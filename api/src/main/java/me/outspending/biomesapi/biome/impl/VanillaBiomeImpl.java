package me.outspending.biomesapi.biome.impl;

import me.outspending.biomesapi.biome.VanillaBiome;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.wrapper.BiomeSettings;
import me.outspending.biomesapi.wrapper.entity.BiomeSpawner;
import me.outspending.biomesapi.wrapper.environment.GrassColorModifier;
import me.outspending.biomesapi.wrapper.environment.attribute.WrappedEnvironmentAttributeMap;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleCatalog;
import me.outspending.biomesapi.wrapper.worldgen.BiomeGenerationSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VanillaBiomeImpl extends AbstractBiomeImpl implements VanillaBiome {
    public VanillaBiomeImpl(
        @NotNull BiomeResourceKey resourceKey,
        @NotNull BiomeSettings settings,
        int waterColor,
        @Nullable Integer fogColor,
        @Nullable Integer waterFogColor,
        @Nullable Integer skyColor,
        @Nullable Integer foliageColor,
        @Nullable Integer grassColor,
        @Nullable Integer dryFoliageColor,
        @NotNull GrassColorModifier grassColorModifier,
        @NotNull ParticleCatalog particleCatalog,
        @NotNull WrappedEnvironmentAttributeMap attributes,
        @Nullable BiomeSpawner biomeSpawner,
        @Nullable BiomeGenerationSettings generationSettings
    ) {
        super(
            resourceKey,
            settings,
            waterColor,
            fogColor,
            waterFogColor,
            skyColor,
            foliageColor,
            grassColor,
            dryFoliageColor,
            grassColorModifier,
            particleCatalog,
            attributes,
            biomeSpawner,
            generationSettings
        );
    }

    @Override
    public VanillaBiomeImpl setSettings(@NotNull BiomeSettings settings) {
        throw new UnsupportedOperationException("Cannot set settings of vanilla biomes.");
    }

    @Override
    public VanillaBiomeImpl setWaterColor(int waterColor) {
        throw new UnsupportedOperationException("Cannot set water color of vanilla biomes.");
    }

    @Override
    public VanillaBiomeImpl setFogColor(@Nullable Integer fogColor) {
        throw new UnsupportedOperationException("Cannot set fog color of vanilla biomes.");
    }

    @Override
    public VanillaBiomeImpl setWaterFogColor(@Nullable Integer waterFogColor) {
        throw new UnsupportedOperationException("Cannot set water fog color of vanilla biomes.");
    }

    @Override
    public VanillaBiomeImpl setSkyColor(@Nullable Integer skyColor) {
        throw new UnsupportedOperationException("Cannot set sky color of vanilla biomes.");
    }

    @Override
    public VanillaBiomeImpl setFoliageColor(@Nullable Integer foliageColor) {
        throw new UnsupportedOperationException("Cannot set foliage color of vanilla biomes.");
    }

    @Override
    public VanillaBiomeImpl setGrassColor(@Nullable Integer grassColor) {
        throw new UnsupportedOperationException("Cannot set grass color of vanilla biomes.");
    }

    @Override
    public VanillaBiomeImpl setDryFoliageColor(@Nullable Integer dryFoliageColor) {
        throw new UnsupportedOperationException("Cannot set dry foliage color of vanilla biomes.");
    }

    @Override
    public VanillaBiomeImpl setGrassColorModifier(@NotNull GrassColorModifier grassColorModifier) {
        throw new UnsupportedOperationException("Cannot set grass color modifier of vanilla biomes.");
    }

    @Override
    public VanillaBiomeImpl setParticleCatalog(@NotNull ParticleCatalog particleCatalog) {
        throw new UnsupportedOperationException("Cannot set particle catalog of vanilla biomes.");
    }

    @Override
    public VanillaBiomeImpl setAttributes(@NotNull WrappedEnvironmentAttributeMap attributes) {
        throw new UnsupportedOperationException("Cannot set attributes of vanilla biomes.");
    }

    @Override
    public VanillaBiomeImpl setBiomeSpawner(@Nullable BiomeSpawner biomeSpawner) {
        super.setBiomeSpawner(biomeSpawner);
        return this;
    }

    @Override
    public VanillaBiomeImpl setGenerationSettings(@Nullable BiomeGenerationSettings generationSettings) {
        super.setGenerationSettings(generationSettings);
        return this;
    }
}
