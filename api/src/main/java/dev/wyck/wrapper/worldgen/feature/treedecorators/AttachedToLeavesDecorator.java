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
public interface AttachedToLeavesDecorator extends TreeDecorator {

    @ApiStatus.Internal
    ConstructWireProvider<AttachedToLeavesDecorator> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.feature.treedecorators.AttachedToLeavesDecoratorImpl");

    @AsOf("3.0.0")
    float probability();

    @AsOf("3.0.0")
    int exclusionRadiusXZ();

    @AsOf("3.0.0")
    int exclusionRadiusY();

    @AsOf("3.0.0")
    BlockStateProvider blockProvider();

    @AsOf("3.0.0")
    int requiredEmptyBlocks();

    @AsOf("3.0.0")
    List<BlockFace> directions();

    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    @AsOf("3.0.0")
    static AttachedToLeavesDecorator of(float probability, int exclusionRadiusXZ, int exclusionRadiusY, BlockStateProvider blockProvider, int requiredEmptyBlocks, List<BlockFace> directions) {
        return WIRE.construct(probability, exclusionRadiusXZ, exclusionRadiusY, blockProvider, requiredEmptyBlocks, directions);
    }

    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    final class Builder {
        // codec defaults
        private float probability = 0.0F;
        private int exclusionRadiusXZ = 0;
        private int exclusionRadiusY = 0;
        private @Nullable BlockStateProvider blockProvider = null;
        private int requiredEmptyBlocks = 1;
        private List<BlockFace> directions = new ArrayList<>();

        public Builder() {}

        public Builder(AttachedToLeavesDecorator decorator) {
            this.probability = decorator.probability();
            this.exclusionRadiusXZ = decorator.exclusionRadiusXZ();
            this.exclusionRadiusY = decorator.exclusionRadiusY();
            this.blockProvider = decorator.blockProvider();
            this.requiredEmptyBlocks = decorator.requiredEmptyBlocks();
            this.directions = decorator.directions();
        }

        @AsOf("3.0.0")
        public Builder probability(float probability) {
            this.probability = probability;
            return this;
        }

        @AsOf("3.0.0")
        public Builder exclusionRadiusXZ(int exclusionRadiusXZ) {
            this.exclusionRadiusXZ = exclusionRadiusXZ;
            return this;
        }

        @AsOf("3.0.0")
        public Builder exclusionRadiusY(int exclusionRadiusY) {
            this.exclusionRadiusY = exclusionRadiusY;
            return this;
        }

        @AsOf("3.0.0")
        public Builder blockProvider(BlockStateProvider blockProvider) {
            this.blockProvider = blockProvider;
            return this;
        }

        @AsOf("3.0.0")
        public Builder requiredEmptyBlocks(int requiredEmptyBlocks) {
            this.requiredEmptyBlocks = requiredEmptyBlocks;
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
        public AttachedToLeavesDecorator build() {
            Preconditions.checkArgument(probability >= 0.0F && probability <= 1.0F, "Probability must be between 0.0F and 1.0F");
            Preconditions.checkArgument(exclusionRadiusXZ >= 0 && exclusionRadiusXZ <= 16, "exclusionRadiusXZ must be between 0 and 16");
            Preconditions.checkArgument(exclusionRadiusY >= 0 && exclusionRadiusY <= 16, "exclusionRadiusY must be between 0 and 16");
            Preconditions.checkNotNull(blockProvider, "blockProvider must be set");
            Preconditions.checkArgument(requiredEmptyBlocks >= 1 && requiredEmptyBlocks <= 16, "requiredEmptyBlocks must be between 1 and 16");
            Preconditions.checkArgument(!directions.isEmpty(), "directions must not be empty");
            return of(probability, exclusionRadiusXZ, exclusionRadiusY, blockProvider, requiredEmptyBlocks, directions);
        }
    }
}
