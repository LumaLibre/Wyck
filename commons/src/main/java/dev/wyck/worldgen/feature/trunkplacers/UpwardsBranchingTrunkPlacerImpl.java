package dev.wyck.worldgen.feature.trunkplacers;

import dev.wyck.util.WorldgenConversions;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Set;

@NullMarked
@ApiStatus.Internal
public record UpwardsBranchingTrunkPlacerImpl(
    @Override int baseHeight,
    @Override int heightRandA,
    @Override int heightRandB,
    @Override IntProvider extraBranchSteps,
    @Override float placeBranchPerLogProbability,
    @Override IntProvider extraBranchLength,
    @Override Set<Material> canGrowThrough
) implements UpwardsBranchingTrunkPlacer {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.trunkplacers.UpwardsBranchingTrunkPlacer(
            baseHeight,
            heightRandA,
            heightRandB,
            extraBranchSteps.asHandle(),
            placeBranchPerLogProbability,
            extraBranchLength.asHandle(),
            WorldgenConversions.toBlockHolderSet(canGrowThrough)
        );
    }
}
