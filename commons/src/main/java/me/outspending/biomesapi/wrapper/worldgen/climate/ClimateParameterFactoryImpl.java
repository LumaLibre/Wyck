package me.outspending.biomesapi.wrapper.worldgen.climate;

import me.outspending.biomesapi.annotations.WireFactory;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@ApiStatus.Internal
public final class ClimateParameterFactoryImpl implements ClimateParameter.Factory {
    @Override
    public ClimateParameter create(float min, float max) {
        return new ClimateParameterImpl(min, max);
    }
}
