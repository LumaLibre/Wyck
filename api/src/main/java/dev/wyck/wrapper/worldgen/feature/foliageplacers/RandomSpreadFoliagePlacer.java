package dev.wyck.wrapper.worldgen.feature.foliageplacers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("3.0.0")
public interface RandomSpreadFoliagePlacer extends FoliagePlacer {

    @ApiStatus.Internal
    ConstructWireProvider<RandomSpreadFoliagePlacer> WIRE = ConstructWireProvider.construct("dev.wyck.wrapper.worldgen.feature.foliageplacers.RandomSpreadFoliagePlacerImpl");

    @AsOf("3.0.0")
    IntProvider foliageHeight();

    @AsOf("3.0.0")
    int leafPlacementAttempts();

    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    @AsOf("3.0.0")
    static RandomSpreadFoliagePlacer of(IntProvider radius, IntProvider offset, IntProvider foliageHeight, int leafPlacementAttempts) {
        return WIRE.construct(radius, offset, foliageHeight, leafPlacementAttempts);
    }

    static Builder builder() {
        return new Builder();
    }

    final class Builder extends FoliagePlacerBuilder<Builder, RandomSpreadFoliagePlacer> {

        private IntProvider foliageHeight = IntProvider.constant(1);
        private int leafPlacementAttempts;

        public Builder() {}

        public Builder(RandomSpreadFoliagePlacer foliagePlacer) {
            super(foliagePlacer);
            this.foliageHeight = foliagePlacer.foliageHeight();
            this.leafPlacementAttempts = foliagePlacer.leafPlacementAttempts();
        }

        @AsOf("3.0.0")
        public Builder foliageHeight(IntProvider foliageHeight) {
            this.foliageHeight = foliageHeight;
            return this;
        }

        @AsOf("3.0.0")
        public Builder leafPlacementAttempts(int leafPlacementAttempts) {
            this.leafPlacementAttempts = leafPlacementAttempts;
            return this;
        }

        @Override
        protected RandomSpreadFoliagePlacer create() {
            Preconditions.checkArgument(foliageHeight.minInclusive() >= 1 && foliageHeight.maxInclusive() <= 512, "foliageHeight must be between 1 and 512");
            Preconditions.checkArgument(leafPlacementAttempts >= 0 && leafPlacementAttempts <= 256, "leafPlacementAttempts must be between 0 and 256");
            return of(radius, offset, foliageHeight, leafPlacementAttempts);
        }
    }
}