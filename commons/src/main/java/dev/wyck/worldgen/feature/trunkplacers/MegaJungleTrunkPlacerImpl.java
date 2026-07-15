package dev.wyck.worldgen.feature.trunkplacers;

import dev.wyck.worldgen.feature.trunkplacers.MegaJungleTrunkPlacer;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record MegaJungleTrunkPlacerImpl(
    @Override int baseHeight,
    @Override int heightRandA,
    @Override int heightRandB
) implements MegaJungleTrunkPlacer {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.trunkplacers.MegaJungleTrunkPlacer(
            baseHeight,
            heightRandA,
            heightRandB
        );
    }
}
