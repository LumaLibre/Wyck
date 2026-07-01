package me.outspending.biomesapi.biome.impl;

import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.renderer.packet.data.BlockReplacement;
import me.outspending.biomesapi.wrapper.BiomeSettings;
import me.outspending.biomesapi.wrapper.entity.BiomeSpawner;
import me.outspending.biomesapi.wrapper.environment.GrassColorModifier;
import me.outspending.biomesapi.wrapper.environment.attribute.EnvironmentAttributeMap;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleCatalog;
import me.outspending.biomesapi.wrapper.worldgen.BiomeGenerationSettings;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
@ApiStatus.Internal
public class CustomBiomeImpl extends AbstractBiomeImpl implements CustomBiome {

    private BlockReplacement[] blockReplacements;

    public CustomBiomeImpl(
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
        BlockReplacement[] blockReplacements,
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
        this.blockReplacements = blockReplacements;
    }

    @Override
    public BlockReplacement[] getBlockReplacements() {
        return blockReplacements;
    }

    @Override
    public CustomBiome setBlockReplacements(BlockReplacement[] blockReplacements) {
        this.blockReplacements = blockReplacements;
        return this;
    }

    @Override
    public boolean isSimilar(CustomBiome otherBiome) {
        if (this.blockReplacements.length != otherBiome.getBlockReplacements().length) return false;
        for (int i = 0; i < this.blockReplacements.length; i++) {
            if (!this.blockReplacements[i].equals(otherBiome.getBlockReplacements()[i])) {
                return false;
            }
        }
        return super.isSimilar(otherBiome);
    }
}
