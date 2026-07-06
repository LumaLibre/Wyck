package dev.wyck.wrapper.worldgen.feature.featuresize;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.OptionalInt;

@NullMarked
@AsOf("3.0.0")
public interface TwoLayersFeatureSize extends FeatureSize {

    @ApiStatus.Internal
    ConstructWireProvider<TwoLayersFeatureSize> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.feature.featuresize.TwoLayersFeatureSizeImpl");

    @AsOf("3.0.0")
    int limit();

    @AsOf("3.0.0")
    int lowerSize();

    @AsOf("3.0.0")
    int upperSize();

    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    @AsOf("3.0.0")
    static TwoLayersFeatureSize of(@Nullable Integer minClippedHeight, int limit, int lowerSize, int upperSize) {
        OptionalInt optionalInt = minClippedHeight == null ? OptionalInt.empty() : OptionalInt.of(minClippedHeight);
        return WIRE.construct(optionalInt, limit, lowerSize, upperSize);
    }

    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    @AsOf("3.0.0")
    final class Builder extends FeatureSizeBuilder<Builder, TwoLayersFeatureSize> {
        // codec defaults
        private int limit = 1;
        private int lowerSize = 0;
        private int upperSize = 1;

        public Builder() {}

        public Builder(TwoLayersFeatureSize other) {
            Integer nullableInt = other.minClippedHeight().isPresent() ? other.minClippedHeight().getAsInt() : null;
            super(nullableInt);
            this.limit = other.limit();
            this.lowerSize = other.lowerSize();
            this.upperSize = other.upperSize();
        }

        @AsOf("3.0.0")
        public Builder limit(int limit) {
            this.limit = limit;
            return this;
        }

        @AsOf("3.0.0")
        public Builder lowerSize(int lowerSize) {
            this.lowerSize = lowerSize;
            return this;
        }

        @AsOf("3.0.0")
        public Builder upperSize(int upperSize) {
            this.upperSize = upperSize;
            return this;
        }

        @Override
        protected TwoLayersFeatureSize create() {
            Preconditions.checkArgument(limit >= 0 && limit <= 81, "limit must be between 0 and 81");
            Preconditions.checkArgument(lowerSize >= 0 && lowerSize <= 16, "lowerSize must be between 0 and 16");
            Preconditions.checkArgument(upperSize >= 0 && upperSize <= 16, "upperSize must be between 0 and 16");
            return of(minClippedHeight, limit, lowerSize, upperSize);
        }
    }
}
