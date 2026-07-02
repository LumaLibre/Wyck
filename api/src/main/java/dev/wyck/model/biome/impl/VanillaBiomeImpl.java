package dev.wyck.model.biome.impl;

import dev.wyck.model.biome.VanillaBiome;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.BiomeSettings;
import dev.wyck.wrapper.entity.BiomeSpawner;
import dev.wyck.wrapper.environment.GrassColorModifier;
import dev.wyck.wrapper.environment.attribute.EnvironmentAttributeMap;
import dev.wyck.wrapper.environment.particle.ParticleCatalog;
import dev.wyck.wrapper.worldgen.BiomeGenerationSettings;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class VanillaBiomeImpl extends AbstractBiomeImpl implements VanillaBiome {
    public VanillaBiomeImpl(
        ResourceKey resourceKey,
        BiomeSettings settings,
        int waterColor,
        @Nullable Integer fogColor,
        @Nullable Integer waterFogColor,
        @Nullable Integer skyColor,
        @Nullable Integer foliageColor,
        @Nullable Integer grassColor,
        @Nullable Integer dryFoliageColor,
        GrassColorModifier grassColorModifier,
        ParticleCatalog particleCatalog,
        EnvironmentAttributeMap attributes,
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
    public VanillaBiomeImpl setSettings(BiomeSettings settings) {
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
    public VanillaBiomeImpl setGrassColorModifier(GrassColorModifier grassColorModifier) {
        throw new UnsupportedOperationException("Cannot set grass color modifier of vanilla biomes.");
    }

    @Override
    public VanillaBiomeImpl setParticleCatalog(ParticleCatalog particleCatalog) {
        throw new UnsupportedOperationException("Cannot set particle catalog of vanilla biomes.");
    }

    @Override
    public VanillaBiomeImpl setAttributes(EnvironmentAttributeMap attributes) {
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
