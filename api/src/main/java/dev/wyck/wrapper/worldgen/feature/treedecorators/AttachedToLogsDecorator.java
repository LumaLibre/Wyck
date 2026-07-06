package dev.wyck.wrapper.worldgen.feature.treedecorators;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.worldgen.stateproviders.BlockStateProvider;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NullMarked
@AsOf("3.0.0")
public interface AttachedToLogsDecorator extends TreeDecorator {

    @ApiStatus.Internal
    ConstructWireProvider<AttachedToLogsDecorator> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.feature.treedecorators.AttachedToLogsDecoratorImpl");

    @AsOf("3.0.0")
    float probability();

    @AsOf("3.0.0")
    BlockStateProvider blockProvider();

    @AsOf("3.0.0")
    List<BlockFace> directions();

    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    @AsOf("3.0.0")
    static AttachedToLogsDecorator of(float probability, BlockStateProvider blockProvider, List<BlockFace> directions) {
        return WIRE.construct(probability, blockProvider, directions);
    }

    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    final class Builder {
        // codec defaults
        private float probability = 0.0F;
        private @Nullable BlockStateProvider blockProvider = null;
        private List<BlockFace> directions = new ArrayList<>();

        public Builder() {}

        public Builder(AttachedToLogsDecorator decorator) {
            this.probability = decorator.probability();
            this.blockProvider = decorator.blockProvider();
            this.directions = decorator.directions();
        }

        @AsOf("3.0.0")
        public Builder probability(float probability) {
            this.probability = probability;
            return this;
        }

        @AsOf("3.0.0")
        public Builder blockProvider(BlockStateProvider blockProvider) {
            this.blockProvider = blockProvider;
            return this;
        }

        @AsOf("3.0.0")
        public Builder directions(List<BlockFace> directions) {
            this.directions = directions;
            return this;
        }

        // Friendly builder methods

        @AsOf("3.0.0")
        public Builder direction(BlockFace... direction) {
            Collections.addAll(this.directions, direction);
            return this;
        }

        @AsOf("3.0.0")
        public AttachedToLogsDecorator build() {
            Preconditions.checkArgument(probability >= 0.0F && probability <= 1.0F, "probability must be between 0.0F and 1.0F");
            Preconditions.checkNotNull(blockProvider, "blockProvider must be set");
            Preconditions.checkArgument(!directions.isEmpty(), "directions must not be empty");
            return of(probability, blockProvider, directions);
        }
    }
}