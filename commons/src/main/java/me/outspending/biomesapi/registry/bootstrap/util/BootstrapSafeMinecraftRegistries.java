package me.outspending.biomesapi.registry.bootstrap.util;

import me.outspending.biomesapi.annotations.AsOf;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.resources.ResourceKey;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.craftbukkit.CraftServer;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * A registry lookup provider that works during bootstrap and runtime.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
@ApiStatus.Internal
public final class BootstrapSafeMinecraftRegistries {


    private static volatile HolderLookup.@Nullable Provider VANILLA;
    private static volatile HolderLookup.@Nullable Provider ACTIVE;

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

    // server registries at runtime, active or default vanilla lookup at bootstrap
    @SuppressWarnings("ConstantValue") // Server annotated NotNull when it actually can be null during bootstrap
    public static <T> HolderGetter<T> getter(ResourceKey<? extends Registry<T>> registry) {
        Server bukkit = Bukkit.getServer();
        if (bukkit != null) {
            RegistryAccess access = ((CraftServer) bukkit).getServer().registryAccess();
            return access.lookupOrThrow(registry);
        }
        return vanilla().lookupOrThrow(registry);
    }
}