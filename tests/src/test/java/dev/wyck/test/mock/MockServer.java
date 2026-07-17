package dev.wyck.test.mock;

import com.mojang.serialization.Lifecycle;
import dev.wyck.test.bootstrap.MinecraftBootstrap;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.RegistryLayer;
import net.minecraft.server.dedicated.DedicatedServer;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.craftbukkit.CraftServer;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@NullMarked
public final class MockServer {

    private static final Field BUKKIT_SERVER;

    static { // InternalReflectUtil
        try {
            BUKKIT_SERVER = Bukkit.class.getDeclaredField("server");
            BUKKIT_SERVER.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private MockServer() {
    }

    public static RegistryAccess.Frozen install() {
        MinecraftBootstrap.boot();

        Set<ResourceKey<? extends Registry<?>>> worldgenKeys = RegistryDataLoader.WORLDGEN_REGISTRIES.stream()
                .map(RegistryDataLoader.RegistryData::key)
                .collect(Collectors.toSet());

        HolderLookup.Provider vanilla = VanillaRegistries.createLookup();
        List<Registry<?>> worldgen = new ArrayList<>();
        vanilla.listRegistries()
                .filter(lookup -> worldgenKeys.contains(lookup.key()))
                .forEach(lookup -> worldgen.add(freezeIntoRegistry(lookup)));

        RegistryAccess.Frozen access = RegistryLayer.createRegistryAccess()
                .replaceFrom(RegistryLayer.WORLDGEN, new RegistryAccess.ImmutableRegistryAccess(worldgen).freeze())
                .compositeAccess();

        DedicatedServer nms = mock(DedicatedServer.class);
        when(nms.registryAccess()).thenReturn(access);

        CraftServer bukkit = mock(CraftServer.class);
        when(bukkit.getServer()).thenReturn(nms);

        set(bukkit);
        return access;
    }

    public static void uninstall() {
        set(null);
    }

    private static <T> Registry<T> freezeIntoRegistry(HolderLookup.RegistryLookup<T> lookup) {
        @SuppressWarnings("unchecked")
        MappedRegistry<T> mapped = new MappedRegistry<>(
                (ResourceKey<? extends @NonNull Registry<T>>) lookup.key(), Lifecycle.stable());
        lookup.listElements().forEach(ref -> mapped.register(ref.key(), ref.value(), RegistrationInfo.BUILT_IN));
        mapped.freeze();
        return mapped;
    }

    private static void set(@Nullable Server server) {
        try {
            BUKKIT_SERVER.set(null, server);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("could not install server onto Bukkit", e);
        }
    }
}
