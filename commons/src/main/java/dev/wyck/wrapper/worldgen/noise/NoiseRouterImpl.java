package dev.wyck.wrapper.worldgen.noise;

import com.google.common.base.Preconditions;
import dev.wyck.wrapper.worldgen.function.DensityFunction;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record NoiseRouterImpl(
    @Override DensityFunction barrier,
    @Override DensityFunction fluidLevelFloodedness,
    @Override DensityFunction fluidLevelSpread,
    @Override DensityFunction lava,
    @Override DensityFunction temperature,
    @Override DensityFunction vegetation,
    @Override DensityFunction continents,
    @Override DensityFunction erosion,
    @Override DensityFunction depth,
    @Override DensityFunction ridges,
    @Override DensityFunction preliminarySurfaceLevel,
    @Override DensityFunction finalDensity,
    @Override DensityFunction veinToggle,
    @Override DensityFunction veinRidged,
    @Override DensityFunction veinGap
) implements NoiseRouter {

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.NoiseRouter(
            resolve(this.barrier),
            resolve(this.fluidLevelFloodedness),
            resolve(this.fluidLevelSpread),
            resolve(this.lava),
            resolve(this.temperature),
            resolve(this.vegetation),
            resolve(this.continents),
            resolve(this.erosion),
            resolve(this.depth),
            resolve(this.ridges),
            resolve(this.preliminarySurfaceLevel),
            resolve(this.finalDensity),
            resolve(this.veinToggle),
            resolve(this.veinRidged),
            resolve(this.veinGap)
        );
    }

    private static net.minecraft.world.level.levelgen.DensityFunction resolve(DensityFunction function) {
        Preconditions.checkNotNull(function, "noise router is missing a density function slot");
        return function.asHandle();
    }
}
