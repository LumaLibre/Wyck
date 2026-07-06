package dev.wyck.wrapper.worldgen.feature.foliageplacers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("3.0.0")
public interface SpruceFoliagePlacer extends FoliagePlacer {

    @ApiStatus.Internal
    ConstructWireProvider<SpruceFoliagePlacer> WIRE = ConstructWireProvider.construct("dev.wyck.wrapper.worldgen.feature.foliageplacers.SpruceFoliagePlacerImpl");

    @AsOf("3.0.0")
    IntProvider trunkHeight();

    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    @AsOf("3.0.0")
    static SpruceFoliagePlacer of(IntProvider radius, IntProvider offset, IntProvider trunkHeight) {
        return WIRE.construct(radius, offset, trunkHeight);
    }

    static Builder builder() {
        return new Builder();
    }

    final class Builder extends FoliagePlacerBuilder<Builder, SpruceFoliagePlacer> {

        private IntProvider trunkHeight = IntProvider.constant(0);

        public Builder() {}

        public Builder(SpruceFoliagePlacer foliagePlacer) {
            super(foliagePlacer);
            this.trunkHeight = foliagePlacer.trunkHeight();
        }

        @AsOf("3.0.0")
        public Builder trunkHeight(IntProvider trunkHeight) {
            this.trunkHeight = trunkHeight;
            return this;
        }

        @Override
        protected SpruceFoliagePlacer create() {
            Preconditions.checkArgument(trunkHeight.minInclusive() >= 0 && trunkHeight.maxInclusive() <= 24, "trunkHeight must be between 0 and 24");
            return of(radius, offset, trunkHeight);
        }
    }
}