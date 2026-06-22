package me.outspending.biomesapi.wrapper.level;

import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.keys.ResourceKey;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@WireFactory
@ApiStatus.Internal
public class BiomeSourceFactoryImpl implements BiomeSource.Factory {

    @Override
    public BiomeSource fixed(ResourceKey biome) {
        return new FixedBiomeSourceImpl(biome);
    }

    @Override
    public BiomeSource multiNoise(List<BiomeSource.MultiNoiseEntry> entries) {
        return new MultiNoiseBiomeSourceImpl(entries);
    }
}