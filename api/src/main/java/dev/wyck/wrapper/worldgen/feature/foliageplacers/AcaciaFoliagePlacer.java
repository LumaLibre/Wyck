package dev.wyck.wrapper.worldgen.feature.foliageplacers;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("3.0.0")
public interface AcaciaFoliagePlacer extends FoliagePlacer {

    @ApiStatus.Internal
    ConstructWireProvider<AcaciaFoliagePlacer> WIRE = ConstructWireProvider.construct("dev.wyck.wrapper.worldgen.feature.foliageplacers.AcaciaFoliagePlacerImpl");

    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    @AsOf("3.0.0")
    static AcaciaFoliagePlacer of(IntProvider radius, IntProvider offset) {
        return WIRE.construct(radius, offset);
    }

    static Builder builder() {
        return new Builder();
    }

    final class Builder extends FoliagePlacerBuilder<Builder, AcaciaFoliagePlacer> {

        public Builder() {}

        public Builder(AcaciaFoliagePlacer foliagePlacer) {
            super(foliagePlacer);
        }

        @Override
        protected AcaciaFoliagePlacer create() {
            return of(radius, offset);
        }
    }
}
