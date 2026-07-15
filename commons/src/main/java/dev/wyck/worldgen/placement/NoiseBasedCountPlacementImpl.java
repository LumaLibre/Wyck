package dev.wyck.worldgen.placement;

import dev.wyck.worldgen.placement.NoiseBasedCountPlacement;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record NoiseBasedCountPlacementImpl(
    @Override int noiseToCountRatio,
    @Override double noiseFactor,
    @Override double noiseOffset
) implements NoiseBasedCountPlacement {
    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.placement.NoiseBasedCountPlacement.of(
            noiseToCountRatio,
            noiseFactor,
            noiseOffset
        );
    }
}