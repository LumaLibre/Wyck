package dev.wyck.wrapper.worldgen.feature.treedecorators;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("3.0.0")
public interface PaleMossDecorator extends TreeDecorator {

    @ApiStatus.Internal
    ConstructWireProvider<PaleMossDecorator> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.feature.treedecorators.PaleMossDecoratorImpl");

    @AsOf("3.0.0")
    float leavesProbability();

    @AsOf("3.0.0")
    float trunkProbability();

    @AsOf("3.0.0")
    float groundProbability();

    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    @AsOf("3.0.0")
    static PaleMossDecorator of(float leavesProbability, float trunkProbability, float groundProbability) {
        return WIRE.construct(leavesProbability, trunkProbability, groundProbability);
    }

    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    final class Builder {
        // codec defaults
        private float leavesProbability = 0.0F;
        private float trunkProbability = 0.0F;
        private float groundProbability = 0.0F;

        public Builder() {}

        public Builder(PaleMossDecorator decorator) {
            this.leavesProbability = decorator.leavesProbability();
            this.trunkProbability = decorator.trunkProbability();
            this.groundProbability = decorator.groundProbability();
        }

        @AsOf("3.0.0")
        public Builder leavesProbability(float leavesProbability) {
            this.leavesProbability = leavesProbability;
            return this;
        }

        @AsOf("3.0.0")
        public Builder trunkProbability(float trunkProbability) {
            this.trunkProbability = trunkProbability;
            return this;
        }

        @AsOf("3.0.0")
        public Builder groundProbability(float groundProbability) {
            this.groundProbability = groundProbability;
            return this;
        }

        @AsOf("3.0.0")
        public PaleMossDecorator build() {
            Preconditions.checkArgument(leavesProbability >= 0.0F && leavesProbability <= 1.0F, "leavesProbability must be between 0.0F and 1.0F");
            Preconditions.checkArgument(trunkProbability >= 0.0F && trunkProbability <= 1.0F, "trunkProbability must be between 0.0F and 1.0F");
            Preconditions.checkArgument(groundProbability >= 0.0F && groundProbability <= 1.0F, "groundProbability must be between 0.0F and 1.0F");
            return of(leavesProbability, trunkProbability, groundProbability);
        }
    }
}