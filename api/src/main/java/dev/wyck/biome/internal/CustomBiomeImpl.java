package dev.wyck.biome.internal;

import dev.wyck.keys.ResourceKey;
import dev.wyck.biome.CustomBiome;
import dev.wyck.renderer.packet.data.BlockReplacement;
import dev.wyck.biome.BiomeSpecialEffects;
import dev.wyck.biome.ClimateSettings;
import dev.wyck.biome.entity.BiomeSpawner;
import dev.wyck.environment.attribute.EnvironmentAttributeMap;
import dev.wyck.biome.BiomeGenerationSettings;
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

    @Override
    public String toString() {
        return "CustomBiomeImpl{" +
                "blockReplacements=" + blockReplacements +
                "} " + super.toString();
    }
}