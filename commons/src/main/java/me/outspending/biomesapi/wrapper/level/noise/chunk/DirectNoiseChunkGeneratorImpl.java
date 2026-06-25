package me.outspending.biomesapi.wrapper.level.noise.chunk;

import me.outspending.biomesapi.wrapper.level.BiomeSource;
import me.outspending.biomesapi.wrapper.level.noise.LevelNoiseGeneratorSettings;
import me.outspending.biomesapi.wrapper.level.noise.Noise;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record DirectNoiseChunkGeneratorImpl(BiomeSource biomeSource, LevelNoiseGeneratorSettings settings) implements LevelChunkGenerator {

    @Override
    public Object toMinecraft() {
        net.minecraft.world.level.biome.BiomeSource source = (net.minecraft.world.level.biome.BiomeSource) this.biomeSource.toMinecraft();
        NoiseGeneratorSettings nmsSettings = (NoiseGeneratorSettings) this.settings.toMinecraft();
        Holder<NoiseGeneratorSettings> holder = Holder.direct(nmsSettings);
        return new NoiseBasedChunkGenerator(source, holder);
    }

    @Override
    public Noise noise() {
        return this.settings;
    }
}