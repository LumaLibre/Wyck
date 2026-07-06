package dev.wyck.wrapper.worldgen.feature.foliageplacers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("3.0.0")
public interface MegaPineFoliagePlacer extends FoliagePlacer {

    @ApiStatus.Internal
    ConstructWireProvider<MegaPineFoliagePlacer> WIRE = ConstructWireProvider.construct("dev.wyck.wrapper.worldgen.feature.foliageplacers.MegaPineFoliagePlacerImpl");

    @AsOf("3.0.0")
    IntProvider crownHeight();

    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    @AsOf("3.0.0")
    static MegaPineFoliagePlacer of(IntProvider radius, IntProvider offset, IntProvider crownHeight) {
        return WIRE.construct(radius, offset, crownHeight);
    }

    static Builder builder() {
        return new Builder();
    }

    final class Builder extends FoliagePlacerBuilder<Builder, MegaPineFoliagePlacer> {

        private IntProvider crownHeight = IntProvider.constant(0);

        public Builder() {}

        public Builder(MegaPineFoliagePlacer foliagePlacer) {
            super(foliagePlacer);
            this.crownHeight = foliagePlacer.crownHeight();
        }

        @AsOf("3.0.0")
        public Builder crownHeight(IntProvider crownHeight) {
            this.crownHeight = crownHeight;
            return this;
        }

        @Override
        protected MegaPineFoliagePlacer create() {
            Preconditions.checkArgument(crownHeight.minInclusive() >= 0 && crownHeight.maxInclusive() <= 24, "crownHeight must be between 0 and 24");
            return of(radius, offset, crownHeight);
        }
    }
}