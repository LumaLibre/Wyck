package dev.wyck.wrapper.worldgen.feature.trunkplacers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("3.0.0")
public interface CherryTrunkPlacer extends TrunkPlacer {

    @ApiStatus.Internal
    ConstructWireProvider<CherryTrunkPlacer> WIRE = ConstructWireProvider.construct("dev.wyck.wrapper.worldgen.feature.trunkplacers.CherryTrunkPlacerImpl");

    @AsOf("3.0.0")
    IntProvider branchCount();

    @AsOf("3.0.0")
    IntProvider branchHorizontalLength();

    @AsOf("3.0.0")
    IntProvider branchStartOffsetFromTop();

    @AsOf("3.0.0")
    IntProvider branchEndOffsetFromTop();

    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    @AsOf("3.0.0")
    static CherryTrunkPlacer of(int baseHeight, int heightRandA, int heightRandB, IntProvider branchCount, IntProvider branchHorizontalLength, IntProvider branchStartOffsetFromTop, IntProvider branchEndOffsetFromTop, IntProvider secondBranchStartOffsetFromTop, IntProvider secondBranchEndOffsetFromTop) {
        return WIRE.construct(baseHeight, heightRandA, heightRandB, branchCount, branchHorizontalLength, branchStartOffsetFromTop, branchEndOffsetFromTop, secondBranchStartOffsetFromTop, secondBranchEndOffsetFromTop);
    }

    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    final class Builder extends TrunkPlacerBuilder<Builder, CherryTrunkPlacer> {
        private IntProvider branchCount = IntProvider.constant(1);
        private IntProvider branchHorizontalLength = IntProvider.constant(2);
        private IntProvider branchStartOffsetFromTop = IntProvider.constant(-16);
        private IntProvider branchEndOffsetFromTop = IntProvider.constant(-16);

        public Builder() {}

        public Builder(CherryTrunkPlacer trunkPlacer) {
            super(trunkPlacer);
            this.branchCount = trunkPlacer.branchCount();
            this.branchHorizontalLength = trunkPlacer.branchHorizontalLength();
            this.branchStartOffsetFromTop = trunkPlacer.branchStartOffsetFromTop();
            this.branchEndOffsetFromTop = trunkPlacer.branchEndOffsetFromTop();
        }

        @AsOf("3.0.0")
        public Builder branchCount(IntProvider branchCount) {
            this.branchCount = branchCount;
            return this;
        }

        @AsOf("3.0.0")
        public Builder branchHorizontalLength(IntProvider branchHorizontalLength) {
            this.branchHorizontalLength = branchHorizontalLength;
            return this;
        }

        @AsOf("3.0.0")
        public Builder branchStartOffsetFromTop(IntProvider branchStartOffsetFromTop) {
            this.branchStartOffsetFromTop = branchStartOffsetFromTop;
            return this;
        }

        @AsOf("3.0.0")
        public Builder branchEndOffsetFromTop(IntProvider branchEndOffsetFromTop) {
            this.branchEndOffsetFromTop = branchEndOffsetFromTop;
            return this;
        }

        @Override
        protected CherryTrunkPlacer create() {
            Preconditions.checkArgument(branchCount.minInclusive() >= 1 && branchCount.maxInclusive() <= 3, "branchCount must be between 1 and 3");
            Preconditions.checkArgument(branchHorizontalLength.minInclusive() >= 2 && branchHorizontalLength.maxInclusive() <= 16, "branchHorizontalLength must be between 2 and 16");
            Preconditions.checkArgument(branchStartOffsetFromTop.minInclusive() >= -16 && branchStartOffsetFromTop.maxInclusive() <= 0, "branchStartOffsetFromTop must be between -16 and 0");
            Preconditions.checkArgument(branchEndOffsetFromTop.minInclusive() >= -16 && branchEndOffsetFromTop.maxInclusive() <= 16, "branchEndOffsetFromTop must be between -16 and 16");
            return of(baseHeight, heightRandA, heightRandB, branchCount, branchHorizontalLength, branchStartOffsetFromTop, branchEndOffsetFromTop, IntProvider.constant(-16), IntProvider.constant(-16));
        }
    }

    // TODO: maybe expose these in the future
//    @AsOf("3.0.0")
//    IntProvider secondBranchStartOffsetFromTop();
//
//    @AsOf("3.0.0")
//    IntProvider secondBranchEndOffsetFromTop();
}
