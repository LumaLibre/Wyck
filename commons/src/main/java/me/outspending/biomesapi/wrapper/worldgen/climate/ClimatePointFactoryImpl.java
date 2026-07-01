package me.outspending.biomesapi.wrapper.worldgen.climate;

import me.outspending.biomesapi.annotations.WireFactory;
import net.minecraft.world.level.biome.Climate;
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

    @Override
    public ClimatePoint fromMinecraft(Object nms) {
        Climate.ParameterPoint point = (Climate.ParameterPoint) nms;
        return ClimatePoint.of(
            ClimateParameter.span(point.temperature().min(), point.temperature().max()),
            ClimateParameter.span(point.humidity().min(), point.humidity().max()),
            ClimateParameter.span(point.continentalness().min(), point.continentalness().max()),
            ClimateParameter.span(point.erosion().min(), point.erosion().max()),
            ClimateParameter.span(point.depth().min(), point.depth().max()),
            ClimateParameter.span(point.weirdness().min(), point.weirdness().max()),
            point.offset()
        );
    }
}