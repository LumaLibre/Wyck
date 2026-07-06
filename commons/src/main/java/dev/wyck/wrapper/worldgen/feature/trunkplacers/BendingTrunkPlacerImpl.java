package dev.wyck.wrapper.worldgen.feature.trunkplacers;

import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record BendingTrunkPlacerImpl(
    @Override int baseHeight,
    @Override int heightRandA,
    @Override int heightRandB,
    @Override int minHeightForLeaves,
    @Override IntProvider bendLength
) implements BendingTrunkPlacer {

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.trunkplacers.BendingTrunkPlacer(
            baseHeight,
            heightRandA,
            heightRandB,
            minHeightForLeaves,
            bendLength.asHandle()
        );
    }
}
