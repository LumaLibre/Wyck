package dev.wyck.decode.worldgen.noise;

import dev.wyck.worldgen.function.DensityFunction;
import dev.wyck.worldgen.noise.NoiseRouter;
import dev.wyck.wrapper.decode.Decodable;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class NoiseRouterDecoder implements Decodable<NoiseRouter, net.minecraft.world.level.levelgen.NoiseRouter> {
    @Override
    public NoiseRouter decode(net.minecraft.world.level.levelgen.NoiseRouter router) {
        return NoiseRouter.of(
            DensityFunction.decode(router.temperature()),
            DensityFunction.decode(router.vegetation()),
            DensityFunction.decode(router.continents()),
            DensityFunction.decode(router.erosion()),
            DensityFunction.decode(router.depth()),
            DensityFunction.decode(router.ridges()),
            DensityFunction.decode(router.chunkSurfaceLevel()),
            DensityFunction.decode(router.finalDensity())
        );
    }
}
