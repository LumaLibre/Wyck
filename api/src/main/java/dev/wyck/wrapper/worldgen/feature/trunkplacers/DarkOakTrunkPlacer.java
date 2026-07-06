package dev.wyck.wrapper.worldgen.feature.trunkplacers;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("3.0.0")
public interface DarkOakTrunkPlacer extends TrunkPlacer {

    @ApiStatus.Internal
    ConstructWireProvider<DarkOakTrunkPlacer> WIRE = ConstructWireProvider.construct("dev.wyck.wrapper.worldgen.feature.trunkplacers.DarkOakTrunkPlacerImpl");

    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    @AsOf("3.0.0")
    static DarkOakTrunkPlacer of(int baseHeight, int heightRandA, int heightRandB) {
        return WIRE.construct(baseHeight, heightRandA, heightRandB);
    }

    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    @AsOf("3.0.0")
    final class Builder extends TrunkPlacerBuilder<Builder, DarkOakTrunkPlacer> {

        public Builder() {}

        public Builder(DarkOakTrunkPlacer trunkPlacer) {
            super(trunkPlacer);
        }

        @Override
        protected DarkOakTrunkPlacer create() {
            return of(baseHeight, heightRandA, heightRandB);
        }
    }
}
