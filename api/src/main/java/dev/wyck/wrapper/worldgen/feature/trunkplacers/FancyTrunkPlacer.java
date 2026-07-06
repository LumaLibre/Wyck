package dev.wyck.wrapper.worldgen.feature.trunkplacers;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("3.0.0")
public interface FancyTrunkPlacer extends TrunkPlacer {

    @ApiStatus.Internal
    ConstructWireProvider<FancyTrunkPlacer> WIRE = ConstructWireProvider.construct("dev.wyck.wrapper.worldgen.feature.trunkplacers.FancyTrunkPlacerImpl");

    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    @AsOf("3.0.0")
    static FancyTrunkPlacer of(int baseHeight, int heightRandA, int heightRandB) {
        return WIRE.construct(baseHeight, heightRandA, heightRandB);
    }

    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    @AsOf("3.0.0")
    final class Builder extends TrunkPlacerBuilder<Builder, FancyTrunkPlacer> {
        public Builder() {}

        public Builder(FancyTrunkPlacer trunkPlacer) {
            super(trunkPlacer);
        }

        @Override
        protected FancyTrunkPlacer create() {
            return of(baseHeight, heightRandA, heightRandB);
        }
    }
}
