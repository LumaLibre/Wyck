package dev.wyck.wrapper.level.noise.chunk;

import dev.wyck.wrapper.level.BiomeSource;
import dev.wyck.wrapper.level.noise.NoiseGeneratorSettings;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record DirectNoiseChunkGeneratorImpl(BiomeSource biomeSource, NoiseGeneratorSettings noise) implements ChunkGenerator {

    @Override
    public Object toMinecraft() {
        net.minecraft.world.level.biome.BiomeSource source = this.biomeSource.asHandle();
        net.minecraft.world.level.levelgen.NoiseGeneratorSettings nmsSettings = this.noise.asHandle();
        Holder<net.minecraft.world.level.levelgen.NoiseGeneratorSettings> holder = Holder.direct(nmsSettings);
        return new NoiseBasedChunkGenerator(source, holder);
    }
}