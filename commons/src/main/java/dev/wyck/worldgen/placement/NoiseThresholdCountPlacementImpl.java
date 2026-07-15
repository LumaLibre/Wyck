package dev.wyck.worldgen.placement;

import dev.wyck.worldgen.placement.NoiseThresholdCountPlacement;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record NoiseThresholdCountPlacementImpl(
    @Override double noiseLevel,
    @Override int belowNoise,
    @Override int aboveNoise
) implements NoiseThresholdCountPlacement {
    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement.of(
            noiseLevel,
            belowNoise,
            aboveNoise
        );
    }
}