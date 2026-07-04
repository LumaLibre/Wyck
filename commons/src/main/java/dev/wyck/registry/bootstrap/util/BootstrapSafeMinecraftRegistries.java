package dev.wyck.registry.bootstrap.util;

import com.mojang.serialization.Lifecycle;
import dev.wyck.annotations.AsOf;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.craftbukkit.CraftServer;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * A registry lookup provider that works during bootstrap and runtime.
 *
 * <p>At runtime everything resolves against the live server's {@link RegistryAccess}. During
 * bootstrap no {@link Registry} instance exists for dynamic/data-driven registries (biome,
 * dimension_type, timeline, worldgen, ...): {@code BuiltInRegistries.REGISTRY} does not contain
 * them, and the {@link VanillaRegistries#createLookup() vanilla lookup} exposes them only as
 * immutable {@link HolderLookup.RegistryLookup} views. When {@link #mappedRegistry} is asked for
 * such a registry at bootstrap, this class materializes a real frozen {@link MappedRegistry} from
 * the vanilla lookup's elements and caches it, so registry-typed call sites keep working
 * unchanged. Holders resolved from a materialized registry are owned by it; encode through
 * {@link #serialization()} so the codec's owner for that registry matches.
 *
 * @since 2.3.0
 * @version 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
@ApiStatus.Internal
public final class BootstrapSafeMinecraftRegistries {


    private static volatile HolderLookup.@Nullable Provider VANILLA;
    private static volatile HolderLookup.@Nullable Provider ACTIVE;
    private static final Map<ResourceKey<? extends Registry<?>>, Registry<?>> MATERIALIZED = new ConcurrentHashMap<>();

    private BootstrapSafeMinecraftRegistries() {
    }

    public static HolderLookup.Provider vanilla() {
        HolderLookup.Provider override = ACTIVE;
        if (override != null) {
            return override;
        }
        if (VANILLA == null) {
            synchronized (BootstrapSafeMinecraftRegistries.class) {
                if (VANILLA == null) {
                    VANILLA = VanillaRegistries.createLookup();
                }
            }
        }
        return VANILLA;
    }

    public static void setActive(HolderLookup.Provider provider) {
        ACTIVE = provider;
    }

    public static void clearActive() {
        ACTIVE = null;
    }

    /**
     * A provider suitable for minting {@link net.minecraft.resources.RegistryOps} during bootstrap.
     * Prefers materialized registries so holders resolved via {@link #mappedRegistry} serialize
     * against their owning registry; falls through to {@link #vanilla()} for everything else. At
     * runtime this is simply the live server's registry access. Lookups consult the materialization
     * cache at call time, so mint ops after building the objects you intend to encode.
     */
    @AsOf("2.4.0")
    @SuppressWarnings("ConstantValue")
    public static HolderLookup.Provider serialization() {
        Server bukkit = Bukkit.getServer();
        if (bukkit != null) {
            return ((CraftServer) bukkit).getServer().registryAccess();
        }
        return new HolderLookup.Provider() {
            @Override
            public Stream<ResourceKey<? extends Registry<?>>> listRegistryKeys() {
                return vanilla().listRegistryKeys();
            }

            @Override
            @SuppressWarnings("unchecked")
            public <T> Optional<? extends HolderLookup.RegistryLookup<T>> lookup(ResourceKey<? extends Registry<? extends T>> key) {
                Registry<?> materialized = MATERIALIZED.get(key);
                if (materialized != null) {
                    return Optional.of((HolderLookup.RegistryLookup<T>) materialized);
                }
                return vanilla().lookup(key);
            }
        };
    }

    // server registries at runtime, materialized/active/vanilla lookup at bootstrap
    @SuppressWarnings({"ConstantValue", "unchecked"}) // Server annotated NotNull when it actually can be null during bootstrap
    public static <T> HolderGetter<T> getter(ResourceKey<? extends Registry<T>> registry) {
        Server bukkit = Bukkit.getServer();
        if (bukkit != null) {
            RegistryAccess access = ((CraftServer) bukkit).getServer().registryAccess();
            return access.lookupOrThrow(registry);
        }
        Registry<?> materialized = MATERIALIZED.get(registry);
        if (materialized != null) {
            return (HolderGetter<T>) materialized;
        }
        return vanilla().lookupOrThrow(registry);
    }

    public static <T> Registry<T> mappedRegistry(String registryId) {
        Identifier location = Identifier.parse(registryId);
        ResourceKey<? extends Registry<T>> registryKey = ResourceKey.createRegistryKey(location);
        return mappedRegistry(registryKey);
    }

    public static <T> Registry<T> mappedRegistry(ResourceKey<? extends Registry<T>> registry) {
        Registry<T> access = mappedRegistryOrNull(registry);
        if (access == null) {
            throw new IllegalStateException("Missing registry: " + registry);
        }
        return access;
    }

    public static <T> @Nullable Registry<T> mappedRegistryOrNull(String registryId) {
        Identifier location = Identifier.parse(registryId);
        ResourceKey<? extends Registry<T>> registryKey = ResourceKey.createRegistryKey(location);
        return mappedRegistryOrNull(registryKey);
    }

    @SuppressWarnings("ConstantValue")
    public static <T> @Nullable Registry<T> mappedRegistryOrNull(ResourceKey<? extends Registry<T>> registry) {
        Server bukkit = Bukkit.getServer();
        if (bukkit != null) {
            RegistryAccess access = ((CraftServer) bukkit).getServer().registryAccess();
            return access.lookup(registry).orElse(null);
        }

        RegistryAccess builtins = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
        Registry<T> builtin = builtins.lookup(registry).orElse(null);
        if (builtin != null) {
            return builtin;
        }

        return materialize(registry);
    }

    /**
     * Materializes a bootstrap {@link MappedRegistry} for a dynamic registry from the current
     * lookup provider. Cached only when no {@link #setActive(HolderLookup.Provider) active}
     * override is set, so promotion-scoped providers never poison the vanilla cache.
     */
    @SuppressWarnings("unchecked")
    private static <T> @Nullable Registry<T> materialize(ResourceKey<? extends Registry<T>> key) {
        if (ACTIVE != null) {
            return copyToMapped(vanilla(), key);
        }
        Registry<?> cached = MATERIALIZED.get(key);
        if (cached == null) {
            Registry<T> built = copyToMapped(vanilla(), key);
            if (built == null) {
                return null;
            }
            cached = MATERIALIZED.computeIfAbsent(key, k -> built);
        }
        return (Registry<T>) cached;
    }

    private static <T> @Nullable Registry<T> copyToMapped(HolderLookup.Provider source, ResourceKey<? extends Registry<T>> key) {
        HolderLookup.RegistryLookup<T> lookup = source.<T>lookup((ResourceKey<? extends Registry<? extends T>>) (ResourceKey<?>) key).orElse(null);
        if (lookup == null) {
            return null;
        }
        MappedRegistry<T> mapped = new MappedRegistry<>(key, Lifecycle.stable());
        lookup.listElements().forEach(ref -> mapped.register(ref.key(), ref.value(), RegistrationInfo.BUILT_IN));
        mapped.freeze();
        return mapped;
    }
}