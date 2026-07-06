package dev.wyck.wrapper.worldgen.feature.treedecorators;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("3.0.0")
public interface BeehiveDecorator extends TreeDecorator {

    @ApiStatus.Internal
    ConstructWireProvider<BeehiveDecorator> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.feature.treedecorators.BeehiveDecoratorImpl");

    @AsOf("3.0.0")
    float probability();

    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    @AsOf("3.0.0")
    static BeehiveDecorator of(float probability) {
        return WIRE.construct(probability);
    }

    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    final class Builder {
        // codec defaults
        private float probability = 0.0F;

        public Builder() {}

        public Builder(BeehiveDecorator decorator) {
            this.probability = decorator.probability();
        }

        @AsOf("3.0.0")
        public Builder probability(float probability) {
            this.probability = probability;
            return this;
        }

        @AsOf("3.0.0")
        public BeehiveDecorator build() {
            Preconditions.checkArgument(probability >= 0.0F && probability <= 1.0F, "probability must be between 0.0F and 1.0F");
            return of(probability);
        }
    }
}