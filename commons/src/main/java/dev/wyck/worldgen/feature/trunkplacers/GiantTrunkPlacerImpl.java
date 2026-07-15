package dev.wyck.worldgen.feature.trunkplacers;

import dev.wyck.annotations.AsOf;
import dev.wyck.worldgen.feature.trunkplacers.GiantTrunkPlacer;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("3.0.0")
public record GiantTrunkPlacerImpl(
    @Override int baseHeight,
    @Override int heightRandA,
    @Override int heightRandB
) implements GiantTrunkPlacer {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer(
            baseHeight,
            heightRandA,
            heightRandB
        );
    }
}
