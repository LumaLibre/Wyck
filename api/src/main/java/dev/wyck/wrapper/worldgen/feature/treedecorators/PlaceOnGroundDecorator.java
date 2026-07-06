package dev.wyck.wrapper.worldgen.feature.treedecorators;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.worldgen.stateproviders.BlockStateProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
@AsOf("3.0.0")
public interface PlaceOnGroundDecorator extends TreeDecorator {

    @ApiStatus.Internal
    ConstructWireProvider<PlaceOnGroundDecorator> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.feature.treedecorators.PlaceOnGroundDecoratorImpl");

    @AsOf("3.0.0")
    int tries();

    @AsOf("3.0.0")
    int radius();

    @AsOf("3.0.0")
    int height();

    @AsOf("3.0.0")
    BlockStateProvider blockStateProvider();

    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    @AsOf("3.0.0")
    static PlaceOnGroundDecorator of(int tries, int radius, int height, BlockStateProvider blockStateProvider) {
        return WIRE.construct(tries, radius, height, blockStateProvider);
    }

    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    final class Builder {
        // codec defaults
        private int tries = 128;
        private int radius = 2;
        private int height = 1;
        private @Nullable BlockStateProvider blockStateProvider = null;

        public Builder() {}

        public Builder(PlaceOnGroundDecorator decorator) {
            this.tries = decorator.tries();
            this.radius = decorator.radius();
            this.height = decorator.height();
            this.blockStateProvider = decorator.blockStateProvider();
        }

        @AsOf("3.0.0")
        public Builder tries(int tries) {
            this.tries = tries;
            return this;
        }

        @AsOf("3.0.0")
        public Builder radius(int radius) {
            this.radius = radius;
            return this;
        }

        @AsOf("3.0.0")
        public Builder height(int height) {
            this.height = height;
            return this;
        }

        @AsOf("3.0.0")
        public Builder blockStateProvider(BlockStateProvider blockStateProvider) {
            this.blockStateProvider = blockStateProvider;
            return this;
        }

        @AsOf("3.0.0")
        public PlaceOnGroundDecorator build() {
            Preconditions.checkArgument(tries > 0, "tries must be positive");
            Preconditions.checkArgument(radius >= 0, "radius must not be negative");
            Preconditions.checkArgument(height >= 0, "height must not be negative");
            Preconditions.checkNotNull(blockStateProvider, "blockStateProvider must be set");
            return of(tries, radius, height, blockStateProvider);
        }
    }
}