package me.outspending.biomesapi.wrapper.level.noise.chunk;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.level.LevelCreator;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import me.outspending.biomesapi.wrapper.level.BiomeSource;
import me.outspending.biomesapi.wrapper.level.noise.LevelNoiseGeneratorSettings;
import me.outspending.biomesapi.wrapper.level.noise.Noise;
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

    Codec<LevelChunkGenerator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        BiomeSource.CODEC.fieldOf("biome_source").forGetter(LevelChunkGenerator::biomeSource),
        Noise.CODEC.fieldOf("noise").forGetter(LevelChunkGenerator::noise)
    ).apply(instance, LevelChunkGenerator::noise));

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.level.noise.chunk.LevelChunkGeneratorFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        LevelChunkGenerator noise(BiomeSource biomeSource, ResourceKey noiseSettings);

        LevelChunkGenerator noise(BiomeSource biomeSource, LevelNoiseGeneratorSettings settings);
    }

    /**
     * The biome source this generator draws from.
     * @return the biome source
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    BiomeSource biomeSource();

    /**
     * The noise generator used by this generator.
     * @return the noise generator
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    Noise noise();

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

    /**
     * Builds a noise generator over the given biome source, referencing the given settings.
     * @param biomeSource the biome source the generator draws from
     * @param abstractNoise the noise generator settings
     * @return a wrapped chunk generator
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static LevelChunkGenerator noise(BiomeSource biomeSource, Noise abstractNoise) {
        if (abstractNoise instanceof Noise.Reference(ResourceKey key)) {
            return noise(biomeSource, key);
        } else if (abstractNoise instanceof LevelNoiseGeneratorSettings settings) {
            return noise(biomeSource, settings);
        }
        throw new IllegalArgumentException("Unknown noise type: " + abstractNoise.getClass().getName());
    }
}