package me.outspending.biomesapi.wrapper.worldgen.feature;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.CraftServer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
public final class ConfiguredFeatureFactoryImpl implements ConfiguredFeature.Factory {

    @Override
    public @NotNull Object toNms(@NotNull ConfiguredFeature feature) {
        return switch (feature) {
            case ConfiguredFeature.Reference reference -> resolveReference(reference);
        };
    }

    private Object resolveReference(@NotNull ConfiguredFeature.Reference reference) {
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        RegistryAccess access = server.registryAccess();


        var registry = access.lookupOrThrow(Registries.CONFIGURED_FEATURE);

        Identifier location = (Identifier) reference.key().resourceLocation();
        ResourceKey<net.minecraft.world.level.levelgen.feature.ConfiguredFeature<?, ?>> resourceKey =
                ResourceKey.create(net.minecraft.core.registries.Registries.CONFIGURED_FEATURE, location);

        return registry.getOrThrow(resourceKey);
    }
}