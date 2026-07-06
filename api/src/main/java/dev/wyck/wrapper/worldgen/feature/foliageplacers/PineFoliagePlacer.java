package dev.wyck.wrapper.worldgen.feature.foliageplacers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("3.0.0")
public interface PineFoliagePlacer extends FoliagePlacer {

    @ApiStatus.Internal
    ConstructWireProvider<PineFoliagePlacer> WIRE = ConstructWireProvider.construct("dev.wyck.wrapper.worldgen.feature.foliageplacers.PineFoliagePlacerImpl");

    @AsOf("3.0.0")
    IntProvider height();

    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    @AsOf("3.0.0")
    static PineFoliagePlacer of(IntProvider radius, IntProvider offset, IntProvider height) {
        return WIRE.construct(radius, offset, height);
    }

    static Builder builder() {
        return new Builder();
    }

    final class Builder extends FoliagePlacerBuilder<Builder, PineFoliagePlacer> {

        private IntProvider height = IntProvider.constant(0);

        public Builder() {}

        public Builder(PineFoliagePlacer foliagePlacer) {
            super(foliagePlacer);
            this.height = foliagePlacer.height();
        }

        @AsOf("3.0.0")
        public Builder height(IntProvider height) {
            this.height = height;
            return this;
        }

        @Override
        protected PineFoliagePlacer create() {
            Preconditions.checkArgument(height.minInclusive() >= 0 && height.maxInclusive() <= 24, "height must be between 0 and 24");
            return of(radius, offset, height);
        }
    }
}