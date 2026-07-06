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
public interface ThreeLayersFeatureSize extends FeatureSize {

    @ApiStatus.Internal
    ConstructWireProvider<ThreeLayersFeatureSize> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.feature.featuresize.ThreeLayersFeatureSizeImpl");

    @AsOf("3.0.0")
    int limit();

    @AsOf("3.0.0")
    int upperLimit();

    @AsOf("3.0.0")
    int lowerSize();

    @AsOf("3.0.0")
    int middleSize();

    @AsOf("3.0.0")
    int upperSize();

    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    @AsOf("3.0.0")
    static ThreeLayersFeatureSize of(@Nullable Integer minClippedHeight, int limit, int upperLimit, int lowerSize, int middleSize, int upperSize) {
        OptionalInt optionalInt = minClippedHeight == null ? OptionalInt.empty() : OptionalInt.of(minClippedHeight);
        return WIRE.construct(optionalInt, limit, upperLimit, lowerSize, middleSize, upperSize);
    }

    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    final class Builder extends FeatureSizeBuilder<Builder, ThreeLayersFeatureSize> {
        // codec defaults
        private int limit = 1;
        private int upperLimit = 1;
        private int lowerSize = 0;
        private int middleSize = 1;
        private int upperSize = 1;

        public Builder() {}

        public Builder(ThreeLayersFeatureSize other) {
            Integer nullableInt = other.minClippedHeight().isPresent() ? other.minClippedHeight().getAsInt() : null;
            super(nullableInt);
            this.limit = other.limit();
            this.upperLimit = other.upperLimit();
            this.lowerSize = other.lowerSize();
            this.middleSize = other.middleSize();
            this.upperSize = other.upperSize();
        }

        @AsOf("3.0.0")
        public Builder limit(int limit) {
            this.limit = limit;
            return this;
        }

        @AsOf("3.0.0")
        public Builder upperLimit(int upperLimit) {
            this.upperLimit = upperLimit;
            return this;
        }

        @AsOf("3.0.0")
        public Builder lowerSize(int lowerSize) {
            this.lowerSize = lowerSize;
            return this;
        }

        @AsOf("3.0.0")
        public Builder middleSize(int middleSize) {
            this.middleSize = middleSize;
            return this;
        }

        @AsOf("3.0.0")
        public Builder upperSize(int upperSize) {
            this.upperSize = upperSize;
            return this;
        }

        @Override
        protected ThreeLayersFeatureSize create() {
            Preconditions.checkArgument(limit >= 0 && limit <= 80, "limit must be between 0 and 80");
            Preconditions.checkArgument(upperLimit >= 0 && upperLimit <= 80, "upperLimit must be between 0 and 80");
            Preconditions.checkArgument(lowerSize >= 0 && lowerSize <= 16, "lowerSize must be between 0 and 16");
            Preconditions.checkArgument(middleSize >= 0 && middleSize <= 16, "middleSize must be between 0 and 16");
            Preconditions.checkArgument(upperSize >= 0 && upperSize <= 16, "upperSize must be between 0 and 16");
            return of(minClippedHeight, limit, upperLimit, lowerSize, middleSize, upperSize);
        }
    }
}
