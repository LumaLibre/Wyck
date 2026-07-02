package dev.wyck.wrapper.level.noise.chunk;

import dev.wyck.annotations.WireFactory;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.level.BiomeSource;
import dev.wyck.wrapper.level.noise.NoiseGeneratorSettings;
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