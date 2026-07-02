package dev.wyck.model.biome.impl;

import dev.wyck.model.biome.Biome;
import dev.wyck.keys.ResourceKey;
import dev.wyck.renderer.packet.data.BlockReplacement;
import dev.wyck.wrapper.BiomeSettings;
import dev.wyck.wrapper.entity.BiomeSpawner;
import dev.wyck.wrapper.environment.GrassColorModifier;
import dev.wyck.wrapper.environment.attribute.EnvironmentAttributeMap;
import dev.wyck.wrapper.environment.particle.ParticleCatalog;
import dev.wyck.wrapper.worldgen.BiomeGenerationSettings;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
@ApiStatus.Internal
public class CustomBiomeImpl extends AbstractBiomeImpl implements Biome {

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
    public Biome setBlockReplacements(BlockReplacement[] blockReplacements) {
        this.blockReplacements = blockReplacements;
        return this;
    }

    @Override
    public boolean isSimilar(Biome otherBiome) {
        if (this.blockReplacements.length != otherBiome.getBlockReplacements().length) return false;
        for (int i = 0; i < this.blockReplacements.length; i++) {
            if (!this.blockReplacements[i].equals(otherBiome.getBlockReplacements()[i])) {
                return false;
            }
        }
        return super.isSimilar(otherBiome);
    }
}
