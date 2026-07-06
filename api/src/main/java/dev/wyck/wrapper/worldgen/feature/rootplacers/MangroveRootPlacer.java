package dev.wyck.wrapper.worldgen.feature.rootplacers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

@NullMarked
@AsOf("3.0.0")
public interface MangroveRootPlacer extends RootPlacer {

    @ApiStatus.Internal
    ConstructWireProvider<MangroveRootPlacer> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.feature.rootplacers.MangroveRootPlacerImpl");

    @AsOf("3.0.0")
    MangroveRootPlacement mangroveRootPlacement();

    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    @AsOf("3.0.0")
    static MangroveRootPlacer of(IntProvider trunkOffsetY, BlockStateProvider rootProvider, @Nullable AboveRootPlacement aboveRootPlacement, MangroveRootPlacement mangroveRootPlacement) {
        return WIRE.construct(trunkOffsetY, rootProvider, Optional.ofNullable(aboveRootPlacement), mangroveRootPlacement);
    }

    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    @AsOf("3.0.0")
    final class Builder extends RootPlacerBuilder<Builder, MangroveRootPlacer> {
        private @Nullable MangroveRootPlacement mangroveRootPlacement;

        public Builder() {}

        public Builder(MangroveRootPlacer trunkPlacer) {
            super(trunkPlacer);
            this.mangroveRootPlacement = trunkPlacer.mangroveRootPlacement();
        }

        @AsOf("3.0.0")
        public Builder mangroveRootPlacement(MangroveRootPlacement mangroveRootPlacement) {
            this.mangroveRootPlacement = mangroveRootPlacement;
            return this;
        }

        @Override
        protected MangroveRootPlacer create() {
            Preconditions.checkNotNull(mangroveRootPlacement, "mangroveRootPlacement must be set");
            //noinspection ConstantConditions
            return of(trunkOffsetY, rootProvider, aboveRootPlacement, mangroveRootPlacement);
        }
    }
}
