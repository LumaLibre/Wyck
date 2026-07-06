package dev.wyck.wrapper.worldgen.feature.foliageplacers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("3.0.0")
public interface CherryFoliagePlacer extends FoliagePlacer {

    @ApiStatus.Internal
    ConstructWireProvider<CherryFoliagePlacer> WIRE = ConstructWireProvider.construct("dev.wyck.wrapper.worldgen.feature.foliageplacers.CherryFoliagePlacerImpl");

    @AsOf("3.0.0")
    IntProvider height();

    @AsOf("3.0.0")
    float wideBottomLayerHoleChance();

    @AsOf("3.0.0")
    float cornerHoleChance();

    @AsOf("3.0.0")
    float hangingLeavesChance();

    @AsOf("3.0.0")
    float hangingLeavesExtensionChance();

    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    @AsOf("3.0.0")
    static CherryFoliagePlacer of(
        IntProvider radius,
        IntProvider offset,
        IntProvider height,
        float wideBottomLayerHoleChance,
        float cornerHoleChance,
        float hangingLeavesChance,
        float hangingLeavesExtensionChance
    ) {
        return WIRE.construct(radius, offset, height, wideBottomLayerHoleChance, cornerHoleChance, hangingLeavesChance, hangingLeavesExtensionChance);
    }

    static Builder builder() {
        return new Builder();
    }

    final class Builder extends FoliagePlacerBuilder<Builder, CherryFoliagePlacer> {

        private IntProvider height = IntProvider.constant(4);
        private float wideBottomLayerHoleChance;
        private float cornerHoleChance;
        private float hangingLeavesChance;
        private float hangingLeavesExtensionChance;

        public Builder() {}

        public Builder(CherryFoliagePlacer foliagePlacer) {
            super(foliagePlacer);
            this.height = foliagePlacer.height();
            this.wideBottomLayerHoleChance = foliagePlacer.wideBottomLayerHoleChance();
            this.cornerHoleChance = foliagePlacer.cornerHoleChance();
            this.hangingLeavesChance = foliagePlacer.hangingLeavesChance();
            this.hangingLeavesExtensionChance = foliagePlacer.hangingLeavesExtensionChance();
        }

        @AsOf("3.0.0")
        public Builder height(IntProvider height) {
            this.height = height;
            return this;
        }

        @AsOf("3.0.0")
        public Builder wideBottomLayerHoleChance(float wideBottomLayerHoleChance) {
            this.wideBottomLayerHoleChance = wideBottomLayerHoleChance;
            return this;
        }

        @AsOf("3.0.0")
        public Builder cornerHoleChance(float cornerHoleChance) {
            this.cornerHoleChance = cornerHoleChance;
            return this;
        }

        @AsOf("3.0.0")
        public Builder hangingLeavesChance(float hangingLeavesChance) {
            this.hangingLeavesChance = hangingLeavesChance;
            return this;
        }

        @AsOf("3.0.0")
        public Builder hangingLeavesExtensionChance(float hangingLeavesExtensionChance) {
            this.hangingLeavesExtensionChance = hangingLeavesExtensionChance;
            return this;
        }

        @Override
        protected CherryFoliagePlacer create() {
            Preconditions.checkArgument(height.minInclusive() >= 4 && height.maxInclusive() <= 16, "height must be between 4 and 16");
            Preconditions.checkArgument(wideBottomLayerHoleChance >= 0.0F && wideBottomLayerHoleChance <= 1.0F, "wideBottomLayerHoleChance must be between 0.0 and 1.0");
            Preconditions.checkArgument(cornerHoleChance >= 0.0F && cornerHoleChance <= 1.0F, "cornerHoleChance must be between 0.0 and 1.0");
            Preconditions.checkArgument(hangingLeavesChance >= 0.0F && hangingLeavesChance <= 1.0F, "hangingLeavesChance must be between 0.0 and 1.0");
            Preconditions.checkArgument(hangingLeavesExtensionChance >= 0.0F && hangingLeavesExtensionChance <= 1.0F, "hangingLeavesExtensionChance must be between 0.0 and 1.0");
            return of(radius, offset, height, wideBottomLayerHoleChance, cornerHoleChance, hangingLeavesChance, hangingLeavesExtensionChance);
        }
    }
}