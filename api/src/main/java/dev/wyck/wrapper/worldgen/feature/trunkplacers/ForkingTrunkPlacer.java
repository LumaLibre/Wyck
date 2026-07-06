package dev.wyck.wrapper.worldgen.feature.trunkplacers;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("3.0.0")
public interface ForkingTrunkPlacer extends TrunkPlacer {

    @ApiStatus.Internal
    ConstructWireProvider<ForkingTrunkPlacer> WIRE = ConstructWireProvider.construct("dev.wyck.wrapper.worldgen.feature.trunkplacers.ForkingTrunkPlacerImpl");

    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    @AsOf("3.0.0")
    static ForkingTrunkPlacer of(int baseHeight, int heightRandA, int heightRandB) {
        return WIRE.construct(baseHeight, heightRandA, heightRandB);
    }

    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    @AsOf("3.0.0")
    final class Builder extends TrunkPlacer.TrunkPlacerBuilder<Builder, ForkingTrunkPlacer> {
        public Builder() {}

        public Builder(ForkingTrunkPlacer trunkPlacer) {
            super(trunkPlacer);
        }

        @Override
        protected ForkingTrunkPlacer create() {
            return of(baseHeight, heightRandA, heightRandB);
        }
    }
}
