package dev.wyck.worldgen.feature.trunkplacers;

import dev.wyck.worldgen.feature.trunkplacers.ForkingTrunkPlacer;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record ForkingTrunkPlacerImpl(
    @Override int baseHeight,
    @Override int heightRandA,
    @Override int heightRandB
) implements ForkingTrunkPlacer {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer(
            baseHeight,
            heightRandA,
            heightRandB
        );
    }
}
