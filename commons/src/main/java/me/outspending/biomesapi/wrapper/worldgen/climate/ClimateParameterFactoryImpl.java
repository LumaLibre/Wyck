package me.outspending.biomesapi.wrapper.worldgen.climate;

import me.outspending.biomesapi.annotations.WireFactory;
import net.minecraft.world.level.biome.Climate;
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

    @Override
    public ClimateParameter fromMinecraft(Object nms) {
        Climate.Parameter parameter = (Climate.Parameter) nms;
        return ClimateParameter.span(parameter.min(), parameter.max());
    }
}
