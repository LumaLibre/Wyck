package me.outspending.biomesapi.wrapper.level;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.level.LevelCreator;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import me.outspending.biomesapi.wrapper.level.noise.LevelNoiseGeneratorSettings;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Wraps a chunk generator for use in a {@link LevelCreator}'s level stem.
 *
 * @version 2.4.0
 * @since 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
@ApiStatus.Experimental
public interface LevelChunkGenerator extends NmsHandle {

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.level.noise.chunk.LevelChunkGeneratorFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        LevelChunkGenerator noise(BiomeSource biomeSource, ResourceKey noiseSettings);
        LevelChunkGenerator noise(BiomeSource biomeSource, LevelNoiseGeneratorSettings settings);
    }

    /**
     * @return the default noise settings for the overworld
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static ResourceKey overworldNoise() {
        return ResourceKey.minecraft("overworld");
    }

    /**
     * @return the default noise settings for the nether
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static ResourceKey netherNoise() {
        return ResourceKey.minecraft("nether");
    }

    /**
     * @return the default noise settings for the end
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static ResourceKey endNoise() {
        return ResourceKey.minecraft("the_end");
    }

    /**
     * Builds a noise generator over the given biome source, referencing the given settings.
     *
     * @param biomeSource the biome source the generator draws from
     * @param noiseSettings the noise generator settings key (e.g. {@code minecraft:overworld})
     * @return a wrapped chunk generator
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static LevelChunkGenerator noise(BiomeSource biomeSource, ResourceKey noiseSettings) {
        return WIRE.get().noise(biomeSource, noiseSettings);
    }

    /**
     * Builds a noise generator over the given biome source, referencing the given settings.
     *
     * @param biomeSource the biome source the generator draws from
     * @param settings the authored noise generator settings
     * @return a wrapped chunk generator
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static LevelChunkGenerator noise(BiomeSource biomeSource, LevelNoiseGeneratorSettings settings) {
        return WIRE.get().noise(biomeSource, settings);
    }
}