package dev.wyck.wrapper.worldgen.feature.foliageplacers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("3.0.0")
public interface BlobFoliagePlacer extends FoliagePlacer {

    @ApiStatus.Internal
    ConstructWireProvider<BlobFoliagePlacer> WIRE = ConstructWireProvider.construct("dev.wyck.wrapper.worldgen.feature.foliageplacers.BlobFoliagePlacerImpl");

    @AsOf("3.0.0")
    int height();

    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    @AsOf("3.0.0")
    static BlobFoliagePlacer of(IntProvider radius, IntProvider offset, int height) {
        return WIRE.construct(radius, offset, height);
    }

    static Builder builder() {
        return new Builder();
    }

    final class Builder extends FoliagePlacerBuilder<Builder, BlobFoliagePlacer> {

        private int height;

        public Builder() {}

        public Builder(BlobFoliagePlacer foliagePlacer) {
            super(foliagePlacer);
            this.height = foliagePlacer.height();
        }

        @AsOf("3.0.0")
        public Builder height(int height) {
            this.height = height;
            return this;
        }

        @Override
        protected BlobFoliagePlacer create() {
            Preconditions.checkArgument(height >= 0 && height <= 16, "height must be between 0 and 16");
            return of(radius, offset, height);
        }
    }
}