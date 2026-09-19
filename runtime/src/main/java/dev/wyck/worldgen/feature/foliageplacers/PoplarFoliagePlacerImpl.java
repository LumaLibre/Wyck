package dev.wyck.worldgen.feature.foliageplacers;

import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class PoplarFoliagePlacerImpl extends FoliagePlacerImpl implements PoplarFoliagePlacer {
    private final IntProvider height;
    private final float sideHoleChance;

    public PoplarFoliagePlacerImpl(IntProvider radius, IntProvider offset, IntProvider height, float sideHoleChance) {
        super(radius, offset);
        this.height = height;
        this.sideHoleChance = sideHoleChance;
    }

    @Override
    public IntProvider height() {
        return height;
    }

    @Override
    public float sideHoleChance() {
        return sideHoleChance;
    }

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.foliageplacers.PoplarFoliagePlacer(
            radius.asHandle(),
            offset.asHandle(),
            height.asHandle(),
            sideHoleChance
        );
    }
}
