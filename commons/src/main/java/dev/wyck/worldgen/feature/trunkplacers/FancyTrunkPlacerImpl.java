package dev.wyck.worldgen.feature.trunkplacers;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record FancyTrunkPlacerImpl(
    @Override int baseHeight,
    @Override int heightRandA,
    @Override int heightRandB
) implements FancyTrunkPlacer {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer(
            baseHeight,
            heightRandA,
            heightRandB
        );
    }
}
