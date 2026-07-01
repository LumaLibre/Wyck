package me.outspending.biomesapi.wrapper.level.noise;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.wrapper.level.noise.function.DensityFunction;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record NoiseRouterImpl(NoiseRouter.Slots slots) implements NoiseRouter {

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.NoiseRouter(
            resolve(this.slots.barrier()),
            resolve(this.slots.fluidLevelFloodedness()),
            resolve(this.slots.fluidLevelSpread()),
            resolve(this.slots.lava()),
            resolve(this.slots.temperature()),
            resolve(this.slots.vegetation()),
            resolve(this.slots.continents()),
            resolve(this.slots.erosion()),
            resolve(this.slots.depth()),
            resolve(this.slots.ridges()),
            resolve(this.slots.preliminarySurfaceLevel()),
            resolve(this.slots.finalDensity()),
            resolve(this.slots.veinToggle()),
            resolve(this.slots.veinRidged()),
            resolve(this.slots.veinGap())
        );
    }

    private static net.minecraft.world.level.levelgen.DensityFunction resolve(DensityFunction function) {
        Preconditions.checkNotNull(function, "noise router is missing a density function slot function: %s", function.toMinecraft());
        return (net.minecraft.world.level.levelgen.DensityFunction) function.toMinecraft();
    }
}