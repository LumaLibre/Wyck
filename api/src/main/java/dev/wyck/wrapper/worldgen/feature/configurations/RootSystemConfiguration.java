package dev.wyck.wrapper.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.worldgen.BlockPredicate;
import dev.wyck.wrapper.worldgen.placement.PlacedFeature;
import dev.wyck.wrapper.worldgen.stateproviders.BlockStateProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * A feature that spawns below naturally generated azalea trees.
 *
 * @see <a href="https://minecraft.wiki/w/Root_System">Root System</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface RootSystemConfiguration extends FeatureConfiguration {

    @ApiStatus.Internal
    ConstructWireProvider<RootSystemConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.feature.configurations.RootSystemConfigurationImpl");

    /**
     * The placed feature to place on top of the root system.
     * @return the placed feature
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    PlacedFeature treeFeature();

    /**
     * The amount of vertical space for the placed feature between 1 and 64.
     * @return the vertical space
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int requiredVerticalSpaceForTree();

    /**
     * The radius of which these roots can spread between 1 and 64.
     * @return the root radius
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int rootRadius();

    /**
     * A tag of materials which can be replaced by the root system.
     * @return the replaceable tag
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    ResourceKey rootReplaceable();

    /**
     * The block to use for the root column.
     * @return the root state provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockStateProvider rootStateProvider();

    /**
     * The number of attempts to place the root column between 1 and 256.
     * @return the root placement attempts
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int rootPlacementAttempts();

    /**
     * The maximum height of the root column between 1 and 4096.
     * @return the root column max height
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int rootColumnMaxHeight();

    /**
     * The radius of the hanging roots between 1 and 64.
     * @return the hanging root radius
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int hangingRootRadius();

    /**
     * The vertical span of the hanging roots between 1 and 16.
     * @return the hanging roots vertical span
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int hangingRootsVerticalSpan();

    /**
     * The block to use for the hanging roots.
     * @return the hanging root state provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockStateProvider hangingRootStateProvider();

    /**
     * The number of attempts to place the hanging roots between 1 and 256.
     * @return the hanging root placement attempts
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int hangingRootPlacementAttempts();

    /**
     * The amount of vertical water allowed for the placed feature between 1 and 64.
     * @return the vertical water allowed
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int allowedVerticalWaterForTree();

    /**
     * The block predicate used to check if the tree position is valid.
     * @return the block predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockPredicate allowedTreePosition();

    /**
     * Converts this object back to a builder.
     * @return a new builder with the same values as this object
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new root system configuration.
     * @param treeFeature the placed feature to place on top of the root system
     * @param requiredVerticalSpaceForTree the amount of vertical space for the placed feature
     * @param rootRadius the radius of which these roots can spread
     * @param rootReplaceable a tag of materials which can be replaced by the root system
     * @param rootStateProvider the block to use for the root column
     * @param rootPlacementAttempts the number of attempts to place the root column
     * @param rootColumnMaxHeight the maximum height of the root column
     * @param hangingRootRadius the radius of the hanging roots
     * @param hangingRootsVerticalSpan the vertical span of the hanging roots
     * @param hangingRootStateProvider the block to use for the hanging roots
     * @param hangingRootPlacementAttempts the number of attempts to place the hanging roots
     * @param allowedVerticalWaterForTree the amount of vertical water allowed for the placed feature
     * @param allowedTreePosition the block predicate used to check if the tree position is valid
     * @return a new root system configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static RootSystemConfiguration of(
        PlacedFeature treeFeature,
        int requiredVerticalSpaceForTree,
        int rootRadius,
        ResourceKey rootReplaceable,
        BlockStateProvider rootStateProvider,
        int rootPlacementAttempts,
        int rootColumnMaxHeight,
        int hangingRootRadius,
        int hangingRootsVerticalSpan,
        BlockStateProvider hangingRootStateProvider,
        int hangingRootPlacementAttempts,
        int allowedVerticalWaterForTree,
        BlockPredicate allowedTreePosition
    ) {
        return WIRE.construct(treeFeature, requiredVerticalSpaceForTree, rootRadius, rootReplaceable, rootStateProvider, rootPlacementAttempts, rootColumnMaxHeight, hangingRootRadius, hangingRootsVerticalSpan, hangingRootStateProvider, hangingRootPlacementAttempts, allowedVerticalWaterForTree, allowedTreePosition);
    }

    /**
     * Creates a new builder.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link RootSystemConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable PlacedFeature treeFeature;
        private int requiredVerticalSpaceForTree = 1;
        private int rootRadius = 1;
        private @Nullable ResourceKey rootReplaceable;
        private @Nullable BlockStateProvider rootStateProvider;
        private int rootPlacementAttempts = 1;
        private int rootColumnMaxHeight = 1;
        private int hangingRootRadius = 1;
        private int hangingRootsVerticalSpan = 1;
        private @Nullable BlockStateProvider hangingRootStateProvider;
        private int hangingRootPlacementAttempts = 1;
        private int allowedVerticalWaterForTree = 1;
        private @Nullable BlockPredicate allowedTreePosition;

        public Builder() {}

        public Builder(RootSystemConfiguration configuration) {
            this.treeFeature = configuration.treeFeature();
            this.requiredVerticalSpaceForTree = configuration.requiredVerticalSpaceForTree();
            this.rootRadius = configuration.rootRadius();
            this.rootReplaceable = configuration.rootReplaceable();
            this.rootStateProvider = configuration.rootStateProvider();
            this.rootPlacementAttempts = configuration.rootPlacementAttempts();
            this.rootColumnMaxHeight = configuration.rootColumnMaxHeight();
            this.hangingRootRadius = configuration.hangingRootRadius();
            this.hangingRootsVerticalSpan = configuration.hangingRootsVerticalSpan();
            this.hangingRootStateProvider = configuration.hangingRootStateProvider();
            this.hangingRootPlacementAttempts = configuration.hangingRootPlacementAttempts();
            this.allowedVerticalWaterForTree = configuration.allowedVerticalWaterForTree();
            this.allowedTreePosition = configuration.allowedTreePosition();
        }

        /**
         * Sets the placed feature to place on top of the root system.
         * @param treeFeature the placed feature to place on top of the root system
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder treeFeature(PlacedFeature treeFeature) {
            this.treeFeature = treeFeature;
            return this;
        }

        /**
         * Sets the amount of vertical space for the placed feature.
         * @param requiredVerticalSpaceForTree the amount of vertical space for the placed feature
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder requiredVerticalSpaceForTree(int requiredVerticalSpaceForTree) {
            this.requiredVerticalSpaceForTree = requiredVerticalSpaceForTree;
            return this;
        }

        /**
         * Sets the radius of which these roots can spread.
         * @param rootRadius the radius of which these roots can spread
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder rootRadius(int rootRadius) {
            this.rootRadius = rootRadius;
            return this;
        }

        /**
         * Sets the tag of materials which can be replaced by the root system.
         * @param rootReplaceable the tag of materials which can be replaced by the root system
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder rootReplaceable(ResourceKey rootReplaceable) {
            this.rootReplaceable = rootReplaceable;
            return this;
        }

        /**
         * Sets the block to use for the root column.
         * @param rootStateProvider the block to use for the root column
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder rootStateProvider(BlockStateProvider rootStateProvider) {
            this.rootStateProvider = rootStateProvider;
            return this;
        }

        /**
         * Sets the number of attempts to place the root column.
         * @param rootPlacementAttempts the number of attempts to place the root column
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder rootPlacementAttempts(int rootPlacementAttempts) {
            this.rootPlacementAttempts = rootPlacementAttempts;
            return this;
        }

        /**
         * Sets the maximum height of the root column.
         * @param rootColumnMaxHeight the maximum height of the root column
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder rootColumnMaxHeight(int rootColumnMaxHeight) {
            this.rootColumnMaxHeight = rootColumnMaxHeight;
            return this;
        }

        /**
         * Sets the radius of the hanging roots.
         * @param hangingRootRadius the radius of the hanging roots
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder hangingRootRadius(int hangingRootRadius) {
            this.hangingRootRadius = hangingRootRadius;
            return this;
        }

        /**
         * Sets the vertical span of the hanging roots.
         * @param hangingRootsVerticalSpan the vertical span of the hanging roots
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder hangingRootsVerticalSpan(int hangingRootsVerticalSpan) {
            this.hangingRootsVerticalSpan = hangingRootsVerticalSpan;
            return this;
        }

        /**
         * Sets the block to use for the hanging roots.
         * @param hangingRootStateProvider the block to use for the hanging roots
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder hangingRootStateProvider(BlockStateProvider hangingRootStateProvider) {
            this.hangingRootStateProvider = hangingRootStateProvider;
            return this;
        }

        /**
         * Sets the number of attempts to place the hanging roots.
         * @param hangingRootPlacementAttempts the number of attempts to place the hanging roots
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder hangingRootPlacementAttempts(int hangingRootPlacementAttempts) {
            this.hangingRootPlacementAttempts = hangingRootPlacementAttempts;
            return this;
        }

        /**
         * Sets the amount of vertical water allowed for the placed feature.
         * @param allowedVerticalWaterForTree the amount of vertical water allowed for the placed feature
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder allowedVerticalWaterForTree(int allowedVerticalWaterForTree) {
            this.allowedVerticalWaterForTree = allowedVerticalWaterForTree;
            return this;
        }

        /**
         * Sets the block predicate used to check if the tree position is valid.
         * @param allowedTreePosition the block predicate used to check if the tree position is valid
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder allowedTreePosition(BlockPredicate allowedTreePosition) {
            this.allowedTreePosition = allowedTreePosition;
            return this;
        }

        /**
         * Builds the root system configuration.
         * @return the root system configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public RootSystemConfiguration build() {
            Preconditions.checkNotNull(treeFeature, "treeFeature must be set");
            Preconditions.checkArgument(requiredVerticalSpaceForTree >= 1 && requiredVerticalSpaceForTree <= 64, "requiredVerticalSpaceForTree must be between 1 and 64");
            Preconditions.checkArgument(rootRadius >= 1 && rootRadius <= 64, "rootRadius must be between 1 and 64");
            Preconditions.checkNotNull(rootReplaceable, "rootReplaceable must be set");
            Preconditions.checkNotNull(rootStateProvider, "rootStateProvider must be set");
            Preconditions.checkArgument(rootPlacementAttempts >= 1 && rootPlacementAttempts <= 256, "rootPlacementAttempts must be between 1 and 256");
            Preconditions.checkArgument(rootColumnMaxHeight >= 1 && rootColumnMaxHeight <= 4096, "rootColumnMaxHeight must be between 1 and 4096");
            Preconditions.checkArgument(hangingRootRadius >= 1 && hangingRootRadius <= 64, "hangingRootRadius must be between 1 and 64");
            Preconditions.checkArgument(hangingRootsVerticalSpan >= 1 && hangingRootsVerticalSpan <= 16, "hangingRootsVerticalSpan must be between 1 and 16");
            Preconditions.checkNotNull(hangingRootStateProvider, "hangingRootStateProvider must be set");
            Preconditions.checkArgument(hangingRootPlacementAttempts >= 1 && hangingRootPlacementAttempts <= 256, "hangingRootPlacementAttempts must be between 1 and 256");
            Preconditions.checkArgument(allowedVerticalWaterForTree >= 1 && allowedVerticalWaterForTree <= 64, "allowedVerticalWaterForTree must be between 1 and 64");
            Preconditions.checkNotNull(allowedTreePosition, "allowedTreePosition must be set");
            return of(
                treeFeature,
                requiredVerticalSpaceForTree,
                rootRadius,
                rootReplaceable,
                rootStateProvider,
                rootPlacementAttempts,
                rootColumnMaxHeight,
                hangingRootRadius,
                hangingRootsVerticalSpan,
                hangingRootStateProvider,
                hangingRootPlacementAttempts,
                allowedVerticalWaterForTree,
                allowedTreePosition
            );
        }
    }
}