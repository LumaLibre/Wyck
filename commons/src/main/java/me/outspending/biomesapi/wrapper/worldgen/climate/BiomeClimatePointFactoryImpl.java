package me.outspending.biomesapi.wrapper.worldgen.climate;

import me.outspending.biomesapi.annotations.WireFactory;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@ApiStatus.Internal
public final class BiomeClimatePointFactoryImpl implements BiomeClimatePoint.Factory {
    @Override
    public BiomeClimatePoint create(BiomeParameter temperature, BiomeParameter humidity, BiomeParameter continentalness, BiomeParameter erosion, BiomeParameter depth, BiomeParameter weirdness, float offset) {
        return new BiomeClimatePointImpl(temperature, humidity, continentalness, erosion, depth, weirdness, offset);
    }
}