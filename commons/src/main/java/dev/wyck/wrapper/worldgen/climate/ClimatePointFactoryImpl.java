package dev.wyck.wrapper.worldgen.climate;

import dev.wyck.annotations.WireFactory;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@ApiStatus.Internal
public final class ClimatePointFactoryImpl implements ClimatePoint.Factory {
    @Override
    public ClimatePoint create(ClimateParameter temperature, ClimateParameter humidity, ClimateParameter continentalness, ClimateParameter erosion, ClimateParameter depth, ClimateParameter weirdness, float offset) {
        return new ClimatePointImpl(temperature, humidity, continentalness, erosion, depth, weirdness, offset);
    }
}