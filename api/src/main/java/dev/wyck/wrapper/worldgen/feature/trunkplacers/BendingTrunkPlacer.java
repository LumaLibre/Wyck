package dev.wyck.wrapper.worldgen.feature.trunkplacers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import org.jspecify.annotations.NullMarked;

// TODO: Javadoc
@NullMarked
@AsOf("3.0.0")
public interface BendingTrunkPlacer extends TrunkPlacer {

    ConstructWireProvider<BendingTrunkPlacer> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.feature.trunkplacers.BendingTrunkPlacerImpl");

    @AsOf("3.0.0")
    int minHeightForLeaves();

    @AsOf("3.0.0")
    IntProvider bendLength();

    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    @AsOf("3.0.0")
    static BendingTrunkPlacer of(int minHeight, int maxHeight, int bendLength, int minHeightForLeaves, IntProvider bendLengthProvider) {
        return WIRE.construct(minHeight, maxHeight, bendLength, minHeightForLeaves, bendLengthProvider);
    }

    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    @AsOf("3.0.0")
    final class Builder extends TrunkPlacerBuilder<Builder, BendingTrunkPlacer> {
        // impl defaults
        private int minHeightForLeaves = 1;
        private IntProvider bendLength = IntProvider.constant(1);

        public Builder() {}

        public Builder(BendingTrunkPlacer trunkPlacer) {
            super(trunkPlacer);
            this.minHeightForLeaves = trunkPlacer.minHeightForLeaves();
            this.bendLength = trunkPlacer.bendLength();
        }

        @AsOf("3.0.0")
        public Builder minHeightForLeaves(int minHeightForLeaves) {
            this.minHeightForLeaves = minHeightForLeaves;
            return this;
        }

        @AsOf("3.0.0")
        public Builder bendLength(IntProvider bendLength) {
            this.bendLength = bendLength;
            return this;
        }

        @Override
        protected BendingTrunkPlacer create() {
            Preconditions.checkArgument(this.minHeightForLeaves >= 0, "minHeightForLeaves should be positive");
            Preconditions.checkArgument(this.bendLength.minInclusive() < 1, "bendLength must be greater than 0");
            Preconditions.checkArgument(this.bendLength.maxInclusive() <= 64, "bendLength must be less than or equal to 64");
            return of(baseHeight, heightRandA, heightRandB, minHeightForLeaves, bendLength);
        }
    }
}
