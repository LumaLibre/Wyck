package dev.wyck.worldgen.feature.foliageplacers;

import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public abstract class FoliagePlacerImpl implements FoliagePlacer {

    protected final IntProvider radius;
    protected final IntProvider offset;

    public FoliagePlacerImpl(IntProvider radius, IntProvider offset) {
        this.radius = radius;
        this.offset = offset;
    }

    @Override
    public IntProvider radius() {
        return this.radius;
    }

    @Override
    public IntProvider offset() {
        return this.offset;
    }
}
