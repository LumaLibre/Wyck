package dev.wyck.wrapper.worldgen.feature.trunkplacers;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("3.0.0")
public interface StraightTrunkPlacer extends TrunkPlacer {

    @ApiStatus.Internal
    ConstructWireProvider<StraightTrunkPlacer> WIRE = ConstructWireProvider.construct("dev.wyck.wrapper.worldgen.feature.trunkplacers.StraightTrunkPlacerImpl");

    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    @AsOf("3.0.0")
    static StraightTrunkPlacer of(int baseHeight, int heightRandA, int heightRandB) {
        return WIRE.construct(baseHeight, heightRandA, heightRandB);
    }

    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    final class Builder extends TrunkPlacer.TrunkPlacerBuilder<Builder, StraightTrunkPlacer> {
        public Builder() {}

        public Builder(StraightTrunkPlacer trunkPlacer) {
            super(trunkPlacer);
        }

        @Override
        protected StraightTrunkPlacer create() {
            return of(baseHeight, heightRandA, heightRandB);
        }
    }
}
