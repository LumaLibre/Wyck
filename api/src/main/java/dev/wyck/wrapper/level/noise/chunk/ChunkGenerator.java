package dev.wyck.wrapper.level.noise.chunk;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.WireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.model.level.LevelCreator;
import dev.wyck.wrapper.internal.NmsHandle;
import dev.wyck.wrapper.level.BiomeSource;
import dev.wyck.wrapper.level.noise.NoiseGeneratorSettings;
import dev.wyck.wrapper.level.noise.Noise;
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
public interface ChunkGenerator extends NmsHandle {

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("dev.wyck.wrapper.level.noise.chunk.ChunkGeneratorFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        ChunkGenerator noise(BiomeSource biomeSource, ResourceKey noiseSettings);

        ChunkGenerator noise(BiomeSource biomeSource, NoiseGeneratorSettings settings);
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
    static ChunkGenerator noise(BiomeSource biomeSource, ResourceKey noiseSettings) {
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
    static ChunkGenerator noise(BiomeSource biomeSource, NoiseGeneratorSettings settings) {
        return WIRE.get().noise(biomeSource, settings);
    }

    /**
     * Builds a noise generator over the given biome source, referencing the given settings.
     * @param biomeSource the biome source the generator draws from
     * @param abstractNoise the noise generator settings
     * @return a wrapped chunk generator
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static ChunkGenerator noise(BiomeSource biomeSource, Noise abstractNoise) {
        if (abstractNoise instanceof Noise.Reference(ResourceKey key)) {
            return noise(biomeSource, key);
        } else if (abstractNoise instanceof NoiseGeneratorSettings settings) {
            return noise(biomeSource, settings);
        }
        throw new IllegalArgumentException("Unknown noise type: " + abstractNoise.getClass().getName());
    }
}