package dev.wyck.wrapper.worldgen.chunk;

import dev.wyck.wrapper.level.BiomeSource;
import dev.wyck.wrapper.worldgen.noise.Noise;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class NoiseBasedChunkGeneratorImpl extends ChunkGeneratorImpl implements NoiseBasedChunkGenerator {

    private final Noise noise;

    public NoiseBasedChunkGeneratorImpl(BiomeSource biomeSource, Noise noise) {
        super(biomeSource);
        this.noise = noise;
    }

    @Override
    public Noise noise() {
        return this.noise;
    }

    @Override
    public Object toMinecraft() {
        net.minecraft.world.level.biome.BiomeSource source = this.biomeSource.asHandle();
        net.minecraft.world.level.levelgen.NoiseGeneratorSettings settings = this.noise.asHandle();
        return new net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator(
            source,
            net.minecraft.core.Holder.direct(settings)
        );
    }
}