package dev.wyck.wrapper.worldgen.feature.trunkplacers;

import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record CherryTrunkPlacerImpl(
    @Override int baseHeight,
    @Override int heightRandA,
    @Override int heightRandB,
    @Override IntProvider branchCount,
    @Override IntProvider branchHorizontalLength,
    @Override IntProvider.Uniform branchStartOffsetFromTop,
    @Override IntProvider branchEndOffsetFromTop
) implements CherryTrunkPlacer {

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.trunkplacers.CherryTrunkPlacer(
            baseHeight,
            heightRandA,
            heightRandB,
            branchCount.asHandle(),
            branchHorizontalLength.asHandle(),
            branchStartOffsetFromTop.asHandle(),
            branchEndOffsetFromTop.asHandle()
        );
    }
}
