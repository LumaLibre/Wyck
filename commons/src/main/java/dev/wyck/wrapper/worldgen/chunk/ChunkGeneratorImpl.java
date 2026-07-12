package dev.wyck.wrapper.worldgen.chunk;

import dev.wyck.wrapper.worldgen.biome.BiomeSource;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public abstract class ChunkGeneratorImpl implements ChunkGenerator {

    protected final BiomeSource biomeSource;

    public ChunkGeneratorImpl(BiomeSource biomeSource) {
        this.biomeSource = biomeSource;
    }

    @Override
    public BiomeSource biomeSource() {
        return biomeSource;
    }
}
