package dev.wyck.wrapper.worldgen.feature.trunkplacers;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record StraightTrunkPlacerImpl(
    @Override int baseHeight,
    @Override int heightRandA,
    @Override int heightRandB
) implements StraightTrunkPlacer {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer(
            baseHeight,
            heightRandA,
            heightRandB
        );
    }
}
