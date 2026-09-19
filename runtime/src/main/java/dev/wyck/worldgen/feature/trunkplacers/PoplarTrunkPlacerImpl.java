package dev.wyck.worldgen.feature.trunkplacers;

import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record PoplarTrunkPlacerImpl(
    @Override int baseHeight,
    @Override int heightRandA,
    @Override int heightRandB,
    @Override IntProvider trunkHeightAboveBranches,
    @Override IntProvider branchAmount
) implements PoplarTrunkPlacer {

    @Override
    public net.minecraft.world.level.levelgen.feature.trunkplacers.PoplarTrunkPlacer toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.trunkplacers.PoplarTrunkPlacer(
            baseHeight,
            heightRandA,
            heightRandB,
            trunkHeightAboveBranches.asHandle(),
            branchAmount.asHandle()
        );
    }
}
