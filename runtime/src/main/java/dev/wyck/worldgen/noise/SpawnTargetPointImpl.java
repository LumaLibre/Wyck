package dev.wyck.worldgen.noise;

import dev.wyck.worldgen.climate.ClimateParameter;
import dev.wyck.worldgen.function.DensityFunction;
import net.minecraft.core.Holder;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.LinkedHashMap;
import java.util.Map;

@NullMarked
@ApiStatus.Internal
public record SpawnTargetPointImpl(
    @Override Map<DensityFunction, ClimateParameter> parameters
) implements SpawnTargetPoint {
    public SpawnTargetPointImpl {
        parameters = Map.copyOf(parameters);
    }

    @Override
    public Object toMinecraft() {
        Map<Holder<net.minecraft.world.level.levelgen.densityfunction.DensityFunction>, net.minecraft.world.level.biome.Climate.Parameter> result = new LinkedHashMap<>();
        parameters.forEach((function, parameter) -> result.put(Holder.direct(function.asHandle()), parameter.asHandle()));
        return new net.minecraft.world.level.levelgen.SpawnTargetPoint(result);
    }
}
