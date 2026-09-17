package dev.wyck.worldgen.feature.foliageplacers;

import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class RandomSpreadFoliagePlacerImpl extends FoliagePlacerImpl implements RandomSpreadFoliagePlacer {

    private final IntProvider foliageHeight;
    private final int leafPlacementAttempts;

    public RandomSpreadFoliagePlacerImpl(IntProvider radius, IntProvider offset, IntProvider foliageHeight, int leafPlacementAttempts) {
        super(radius, offset);
        this.foliageHeight = foliageHeight;
        this.leafPlacementAttempts = leafPlacementAttempts;
    }

    @Override
    public IntProvider foliageHeight() {
        return this.foliageHeight;
    }

    @Override
    public int leafPlacementAttempts() {
        return this.leafPlacementAttempts;
    }

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer(
            radius.asHandle(),
            offset.asHandle(),
            foliageHeight.asHandle(),
            leafPlacementAttempts
        );
    }
}