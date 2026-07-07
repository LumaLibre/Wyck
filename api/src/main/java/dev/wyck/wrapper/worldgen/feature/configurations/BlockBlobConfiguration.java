package dev.wyck.wrapper.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.util.BukkitBootstrapUtil;
import dev.wyck.wrapper.worldgen.blockpredicates.BlockPredicate;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * A block blob.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface BlockBlobConfiguration extends FeatureConfiguration {

    @ApiStatus.Internal
    ConstructWireProvider<BlockBlobConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.feature.configurations.BlockBlobConfigurationImpl");

    /**
     * The block state that makes up the blob.
     * @return the block state
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockData state();

    /**
     * The block predicate that must be passed for the blob to be placed on a block.
     * @return the placement predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockPredicate canPlaceOn();

    /**
     * Creates a new block blob configuration.
     * @param state the block state that makes up the blob
     * @param canPlaceOn the block predicate that must be passed for the blob to be placed on a block
     * @return a new block blob configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BlockBlobConfiguration of(BlockData state, BlockPredicate canPlaceOn) {
        return WIRE.construct(state, canPlaceOn);
    }

    /**
     * Builder for {@link BlockBlobConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable BlockData state;
        private @Nullable BlockPredicate canPlaceOn;

        /**
         * Sets the block state that makes up the blob.
         * @param state the block state
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder state(BlockData state) {
            this.state = state;
            return this;
        }

        /**
         * Sets the block predicate that must be passed for the blob to be placed on a block.
         * @param canPlaceOn the placement predicate
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder canPlaceOn(BlockPredicate canPlaceOn) {
            this.canPlaceOn = canPlaceOn;
            return this;
        }

        // Friendly builder methods

        /**
         * Sets the block state that makes up the blob from the given material's default block data.
         * @param material the material to derive the block state from
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder state(Material material) {
            this.state = BukkitBootstrapUtil.util().createBlockData(material);
            return this;
        }

        /**
         * Builds the configuration.
         * @return the configuration
         * @since 3.0.0
         */
        public BlockBlobConfiguration build() {
            Preconditions.checkNotNull(state, "state must be set");
            Preconditions.checkNotNull(canPlaceOn, "canPlaceOn must be set");
            return of(state, canPlaceOn);
        }
    }
}
