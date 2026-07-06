package dev.wyck.wrapper.worldgen.feature.rootplacers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.internal.Wrapper;
import dev.wyck.wrapper.worldgen.stateproviders.BlockStateProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
@AsOf("3.0.0")
public interface AboveRootPlacement extends Wrapper {

    @ApiStatus.Internal
    ConstructWireProvider<AboveRootPlacement> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.feature.rootplacers.AboveRootPlacementImpl");

    @AsOf("3.0.0")
    BlockStateProvider aboveRootProvider();

    @AsOf("3.0.0")
    float aboveRootPlacementChance();

    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    @AsOf("3.0.0")
    static AboveRootPlacement of(BlockStateProvider aboveRootProvider, float aboveRootPlacementChance) {
        return WIRE.construct(aboveRootProvider, aboveRootPlacementChance);
    }

    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    @AsOf("3.0.0")
    final class Builder {
        private @Nullable BlockStateProvider aboveRootProvider;
        private float aboveRootPlacementChance;

        public Builder() {}

        public Builder(AboveRootPlacement aboveRootPlacement) {
            this.aboveRootProvider = aboveRootPlacement.aboveRootProvider();
            this.aboveRootPlacementChance = aboveRootPlacement.aboveRootPlacementChance();
        }

        @AsOf("3.0.0")
        public Builder aboveRootProvider(BlockStateProvider aboveRootProvider) {
            this.aboveRootProvider = aboveRootProvider;
            return this;
        }

        @AsOf("3.0.0")
        public Builder aboveRootPlacementChance(float aboveRootPlacementChance) {
            this.aboveRootPlacementChance = aboveRootPlacementChance;
            return this;
        }

        @AsOf("3.0.0")
        public AboveRootPlacement build() {
            Preconditions.checkNotNull(aboveRootProvider, "aboveRootProvider must be set");
            Preconditions.checkArgument(aboveRootPlacementChance >= 0.0F && aboveRootPlacementChance <= 1.0F, "aboveRootPlacementChance must be between 0.0F and 1.0F");
            return AboveRootPlacement.of(aboveRootProvider, aboveRootPlacementChance);
        }
    }
}
