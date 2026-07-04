package dev.wyck.model.biome.impl;

import dev.wyck.keys.ResourceKey;
import dev.wyck.model.biome.CustomBiome;
import dev.wyck.renderer.packet.data.BlockReplacement;
import dev.wyck.wrapper.biome.BiomeSpecialEffects;
import dev.wyck.wrapper.biome.ClimateSettings;
import dev.wyck.wrapper.entity.BiomeSpawner;
import dev.wyck.wrapper.environment.attribute.EnvironmentAttributeMap;
import dev.wyck.wrapper.worldgen.BiomeGenerationSettings;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;

@NullMarked
@ApiStatus.Internal
public class CustomBiomeImpl extends BiomeImpl implements CustomBiome {

    private List<BlockReplacement> blockReplacements;

    public CustomBiomeImpl(
        ResourceKey resourceKey,
        ClimateSettings climateSettings,
        BiomeSpecialEffects specialEffects,
        EnvironmentAttributeMap attributes,
        @Nullable BiomeSpawner biomeSpawner,
        @Nullable BiomeGenerationSettings generationSettings,
        List<BlockReplacement> blockReplacements
    ) {
        super(
            resourceKey,
            climateSettings,
            specialEffects,
            attributes,
            biomeSpawner,
            generationSettings
        );
        this.blockReplacements = blockReplacements;
    }

    @Override
    public List<BlockReplacement> blockReplacements() {
        return blockReplacements;
    }

    @Override
    public CustomBiome blockReplacements(List<BlockReplacement> blockReplacements) {
        this.blockReplacements = blockReplacements;
        return this;
    }

    @Override
    public boolean isSimilar(CustomBiome otherBiome) {
        if (this == otherBiome) return true;
        if (!this.blockReplacements.equals(otherBiome.blockReplacements())) return false;
        return super.isSimilar(otherBiome);
    }
}