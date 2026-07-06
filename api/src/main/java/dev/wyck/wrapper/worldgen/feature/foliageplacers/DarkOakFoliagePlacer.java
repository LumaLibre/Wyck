package dev.wyck.wrapper.worldgen.feature.foliageplacers;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("3.0.0")
public interface DarkOakFoliagePlacer extends FoliagePlacer {

    @ApiStatus.Internal
    ConstructWireProvider<DarkOakFoliagePlacer> WIRE = ConstructWireProvider.construct("dev.wyck.wrapper.worldgen.feature.foliageplacers.DarkOakFoliagePlacerImpl");

    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    @AsOf("3.0.0")
    static DarkOakFoliagePlacer of(IntProvider radius, IntProvider offset) {
        return WIRE.construct(radius, offset);
    }

    static Builder builder() {
        return new Builder();
    }

    final class Builder extends FoliagePlacerBuilder<Builder, DarkOakFoliagePlacer> {

        public Builder() {}

        public Builder(DarkOakFoliagePlacer foliagePlacer) {
            super(foliagePlacer);
        }

        @Override
        protected DarkOakFoliagePlacer create() {
            return of(radius, offset);
        }
    }
}