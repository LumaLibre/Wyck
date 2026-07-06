package dev.wyck.wrapper.worldgen.feature.rootplacers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.internal.Wrapper;
import dev.wyck.wrapper.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

@NullMarked
@AsOf("3.0.0")
public interface RootPlacer extends Wrapper {

    @AsOf("3.0.0")
    IntProvider trunkOffsetY();

    @AsOf("3.0.0")
    BlockStateProvider rootProvider();

    @AsOf("3.0.0")
    Optional<AboveRootPlacement> aboveRootPlacement();


    @AsOf("3.0.0")
    @SuppressWarnings("unchecked")
    abstract class RootPlacerBuilder<T, P> {
        protected IntProvider trunkOffsetY = IntProvider.constant(0);
        protected @Nullable BlockStateProvider rootProvider;
        protected @Nullable AboveRootPlacement aboveRootPlacement;

        public RootPlacerBuilder() {}

        public RootPlacerBuilder(RootPlacer rootPlacer) {
            this.trunkOffsetY = rootPlacer.trunkOffsetY();
            this.rootProvider = rootPlacer.rootProvider();
            this.aboveRootPlacement = rootPlacer.aboveRootPlacement().orElse(null);
        }

        @AsOf("3.0.0")
        public T trunkOffsetY(IntProvider trunkOffsetY) {
            this.trunkOffsetY = trunkOffsetY;
            return (T) this;
        }

        @AsOf("3.0.0")
        public T rootProvider(BlockStateProvider rootProvider) {
            this.rootProvider = rootProvider;
            return (T) this;
        }

        @AsOf("3.0.0")
        public T aboveRootPlacement(AboveRootPlacement aboveRootPlacement) {
            this.aboveRootPlacement = aboveRootPlacement;
            return (T) this;
        }

        @AsOf("3.0.0")
        public final P build() {
            Preconditions.checkNotNull(rootProvider, "rootProvider must be set");
            return create();
        }

        protected abstract P create();
    }
}
