package dev.wyck.worldgen.feature.trunkplacers;

import dev.wyck.worldgen.feature.trunkplacers.DarkOakTrunkPlacer;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record DarkOakTrunkPlacerImpl(
    @Override int baseHeight,
    @Override int heightRandA,
    @Override int heightRandB
) implements DarkOakTrunkPlacer {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer(
            baseHeight,
            heightRandA,
            heightRandB
        );
    }
}
