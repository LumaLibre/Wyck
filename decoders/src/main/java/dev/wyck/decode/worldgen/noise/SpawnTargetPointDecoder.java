package dev.wyck.decode.worldgen.noise;

import dev.wyck.worldgen.climate.ClimateParameter;
import dev.wyck.worldgen.function.DensityFunction;
import dev.wyck.worldgen.noise.SpawnTargetPoint;
import dev.wyck.wrapper.decode.Decodable;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.LinkedHashMap;
import java.util.Map;

@NullMarked
@ApiStatus.Internal
public final class SpawnTargetPointDecoder implements Decodable<SpawnTargetPoint, net.minecraft.world.level.levelgen.SpawnTargetPoint> {

    @Override
    public SpawnTargetPoint decode(net.minecraft.world.level.levelgen.SpawnTargetPoint point) {
        Map<DensityFunction, ClimateParameter> parameters = new LinkedHashMap<>();
        point.parameters().forEach((function, parameter) -> parameters.put(
            DensityFunction.decode(function),
            ClimateParameter.decode(parameter)
        ));
        return SpawnTargetPoint.of(parameters);
    }
}
