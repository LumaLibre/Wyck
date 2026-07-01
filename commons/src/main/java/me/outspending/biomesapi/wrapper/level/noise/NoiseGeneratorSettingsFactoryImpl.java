package me.outspending.biomesapi.wrapper.level.noise;

import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.registry.bootstrap.util.BootstrapSafeMinecraftRegistries;
import me.outspending.biomesapi.registry.internal.FrozenRegistry;
import me.outspending.biomesapi.registry.internal.Referer;
import me.outspending.biomesapi.wrapper.level.noise.settings.NoiseSettings;
import me.outspending.biomesapi.wrapper.worldgen.climate.ClimatePoint;
import me.outspending.biomesapi.wrapper.worldgen.surface.SurfaceRule;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@WireFactory
@ApiStatus.Internal
public class NoiseGeneratorSettingsFactoryImpl implements NoiseGeneratorSettings.Factory {

    @Override
    public NoiseGeneratorSettings create(NoiseGeneratorSettings.Data data) {
        return new NoiseGeneratorSettingsImpl(data);
    }

    @Override
    public NoiseGeneratorSettings fromMinecraft(Object nms) {
        net.minecraft.world.level.levelgen.NoiseGeneratorSettings nmsSettings = (net.minecraft.world.level.levelgen.NoiseGeneratorSettings) nms;
        Registry<net.minecraft.world.level.levelgen.NoiseGeneratorSettings> registry = BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.NOISE_SETTINGS);
        Identifier id = registry.getKey(nmsSettings);

        ResourceKey key = id != null ? ResourceKey.of(id.getNamespace(), id.getPath()) : null;

        return NoiseGeneratorSettings.of(
            Optional.ofNullable(key),
            NoiseSettings.fromMinecraft(nmsSettings.noiseSettings()),
            nmsSettings.defaultBlock().getBukkitMaterial(),
            nmsSettings.defaultFluid().getBukkitMaterial(),
            NoiseRouter.fromMinecraft(nmsSettings.noiseRouter()),
            SurfaceRule.fromMinecraft(nmsSettings.surfaceRule()),
            nmsSettings.spawnTarget().stream().map(ClimatePoint::fromMinecraft).toList(),
            nmsSettings.seaLevel(),
            nmsSettings.disableMobGeneration(),
            nmsSettings.aquifersEnabled(),
            nmsSettings.oreVeinsEnabled(),
            nmsSettings.useLegacyRandomSource()
        );

    }
}