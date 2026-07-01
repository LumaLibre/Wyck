package me.outspending.biomesapi.wrapper.level.noise;

import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.wrapper.level.noise.function.DensityFunction;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@ApiStatus.Internal
public class NoiseRouterFactoryImpl implements NoiseRouter.Factory {

    @Override
    public NoiseRouter create(NoiseRouter.Slots slots) {
        return new NoiseRouterImpl(slots);
    }

    @Override
    public NoiseRouter fromMinecraft(Object nms) {
        net.minecraft.world.level.levelgen.NoiseRouter router = (net.minecraft.world.level.levelgen.NoiseRouter) nms;
        return NoiseRouter.of(
            DensityFunction.fromMinecraft(router.barrierNoise()),
            DensityFunction.fromMinecraft(router.fluidLevelFloodednessNoise()),
            DensityFunction.fromMinecraft(router.fluidLevelSpreadNoise()),
            DensityFunction.fromMinecraft(router.lavaNoise()),
            DensityFunction.fromMinecraft(router.temperature()),
            DensityFunction.fromMinecraft(router.vegetation()),
            DensityFunction.fromMinecraft(router.continents()),
            DensityFunction.fromMinecraft(router.erosion()),
            DensityFunction.fromMinecraft(router.depth()),
            DensityFunction.fromMinecraft(router.ridges()),
            DensityFunction.fromMinecraft(router.preliminarySurfaceLevel()),
            DensityFunction.fromMinecraft(router.finalDensity()),
            DensityFunction.fromMinecraft(router.veinToggle()),
            DensityFunction.fromMinecraft(router.veinRidged()),
            DensityFunction.fromMinecraft(router.veinGap())
        );
    }
}