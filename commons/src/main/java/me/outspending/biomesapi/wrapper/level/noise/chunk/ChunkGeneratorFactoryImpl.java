package me.outspending.biomesapi.wrapper.level.noise.chunk;

import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.wrapper.level.BiomeSource;
import me.outspending.biomesapi.wrapper.level.noise.NoiseGeneratorSettings;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@ApiStatus.Internal
public class ChunkGeneratorFactoryImpl implements ChunkGenerator.Factory {

    @Override
    public ChunkGenerator noise(BiomeSource biomeSource, ResourceKey noiseSettings) {
        return new NoiseChunkGeneratorImpl(biomeSource, noiseSettings);
    }

    @Override
    public ChunkGenerator noise(BiomeSource biomeSource, NoiseGeneratorSettings settings) {
        return new DirectNoiseChunkGeneratorImpl(biomeSource, settings);
    }
}