package dev.wyck.worldgen.noise;

import dev.wyck.worldgen.function.DensityFunction;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record NoiseRouterImpl(
    @Override DensityFunction temperature,
    @Override DensityFunction vegetation,
    @Override DensityFunction continents,
    @Override DensityFunction erosion,
    @Override DensityFunction depth,
    @Override DensityFunction ridges,
    @Override DensityFunction chunkSurfaceLevel,
    @Override DensityFunction finalDensity
) implements NoiseRouter {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.NoiseRouter(
            temperature.asHandle(), vegetation.asHandle(), continents.asHandle(), erosion.asHandle(),
            depth.asHandle(), ridges.asHandle(), chunkSurfaceLevel.asHandle(), finalDensity.asHandle()
        );
    }
}
