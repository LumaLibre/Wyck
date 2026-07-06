package dev.wyck.wrapper.worldgen.feature.configurations.geode;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.internal.Wrapper;
import dev.wyck.wrapper.worldgen.stateproviders.BlockStateProvider;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The blocks used for the layers and inner placements of a geode.
 *
 * @see <a href="https://minecraft.wiki/w/Amethyst_Geode">Amethyst Geode</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface GeodeBlockSettings extends Wrapper {

    @ApiStatus.Internal
    ConstructWireProvider<GeodeBlockSettings> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.feature.config.geode.GeodeBlockSettingsImpl");

    /**
     * The block state provider used for the filling layer. This is air in vanilla geodes.
     * @return the filling provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockStateProvider fillingProvider();

    /**
     * The block state provider used for the inner layer. This is amethyst block in vanilla geodes.
     * @return the inner layer provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockStateProvider innerLayerProvider();

    /**
     * The block state provider used for the inner layer's alternate block. This is budding amethyst in vanilla geodes.
     * @return the alternate inner layer provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockStateProvider alternateInnerLayerProvider();

    /**
     * The block state provider used for the middle layer. This is calcite in vanilla geodes.
     * @return the middle layer provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockStateProvider middleLayerProvider();

    /**
     * The block state provider used for the outer layer. This is smooth basalt in vanilla geodes.
     * @return the outer layer provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockStateProvider outerLayerProvider();

    /**
     * The block states placed within the geode, adjacent to the alternate inner layer block(s) by default.
     * In vanilla geodes these are the small, medium, and large amethyst buds and the amethyst cluster.
     * @return an immutable list of inner placements
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    List<BlockData> innerPlacements();

    /**
     * The block tag listing which blocks the geode cannot replace. Vanilla geodes use
     * {@code #minecraft:features_cannot_replace}.
     * @return the block tag of blocks that cannot be replaced
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    ResourceKey cannotReplace();

    /**
     * The block tag listing invalid blocks near which the geode will not generate.
     * @return the block tag of invalid blocks
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    ResourceKey invalidBlocks();

    /**
     * Converts this object back to a builder.
     * @return a builder with the same values as this object
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new geode block settings.
     * @param fillingProvider the block state provider used for the filling layer
     * @param innerLayerProvider the block state provider used for the inner layer
     * @param alternateInnerLayerProvider the block state provider used for the inner layer's alternate block
     * @param middleLayerProvider the block state provider used for the middle layer
     * @param outerLayerProvider the block state provider used for the outer layer
     * @param innerPlacements the block states placed within the geode
     * @param cannotReplace the block tag listing which blocks the geode cannot replace
     * @param invalidBlocks the block tag listing invalid blocks
     * @return a new geode block settings
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static GeodeBlockSettings of(
        BlockStateProvider fillingProvider,
        BlockStateProvider innerLayerProvider,
        BlockStateProvider alternateInnerLayerProvider,
        BlockStateProvider middleLayerProvider,
        BlockStateProvider outerLayerProvider,
        List<BlockData> innerPlacements,
        ResourceKey cannotReplace,
        ResourceKey invalidBlocks
    ) {
        return WIRE.construct(
            fillingProvider, innerLayerProvider, alternateInnerLayerProvider, middleLayerProvider,
            outerLayerProvider, innerPlacements, cannotReplace, invalidBlocks
        );
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
     * Builder for {@link GeodeBlockSettings}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable BlockStateProvider fillingProvider;
        private @Nullable BlockStateProvider innerLayerProvider;
        private @Nullable BlockStateProvider alternateInnerLayerProvider;
        private @Nullable BlockStateProvider middleLayerProvider;
        private @Nullable BlockStateProvider outerLayerProvider;
        private List<BlockData> innerPlacements = new ArrayList<>();
        private @Nullable ResourceKey cannotReplace;
        private @Nullable ResourceKey invalidBlocks;

        public Builder() {}

        public Builder(GeodeBlockSettings settings) {
            this.fillingProvider = settings.fillingProvider();
            this.innerLayerProvider = settings.innerLayerProvider();
            this.alternateInnerLayerProvider = settings.alternateInnerLayerProvider();
            this.middleLayerProvider = settings.middleLayerProvider();
            this.outerLayerProvider = settings.outerLayerProvider();
            this.innerPlacements.addAll(settings.innerPlacements());
            this.cannotReplace = settings.cannotReplace();
            this.invalidBlocks = settings.invalidBlocks();
        }

        /**
         * Sets the block state provider used for the filling layer.
         * @param fillingProvider the filling provider
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder fillingProvider(BlockStateProvider fillingProvider) {
            this.fillingProvider = fillingProvider;
            return this;
        }

        /**
         * Sets the block state provider used for the inner layer.
         * @param innerLayerProvider the inner layer provider
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder innerLayerProvider(BlockStateProvider innerLayerProvider) {
            this.innerLayerProvider = innerLayerProvider;
            return this;
        }

        /**
         * Sets the block state provider used for the inner layer's alternate block.
         * @param alternateInnerLayerProvider the alternate inner layer provider
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder alternateInnerLayerProvider(BlockStateProvider alternateInnerLayerProvider) {
            this.alternateInnerLayerProvider = alternateInnerLayerProvider;
            return this;
        }

        /**
         * Sets the block state provider used for the middle layer.
         * @param middleLayerProvider the middle layer provider
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder middleLayerProvider(BlockStateProvider middleLayerProvider) {
            this.middleLayerProvider = middleLayerProvider;
            return this;
        }

        /**
         * Sets the block state provider used for the outer layer.
         * @param outerLayerProvider the outer layer provider
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder outerLayerProvider(BlockStateProvider outerLayerProvider) {
            this.outerLayerProvider = outerLayerProvider;
            return this;
        }

        /**
         * Sets the block states placed within the geode, replacing any existing inner placements.
         * @param innerPlacements the inner placements
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder innerPlacements(List<BlockData> innerPlacements) {
            this.innerPlacements = innerPlacements;
            return this;
        }

        /**
         * Adds one or more block states to the inner placements of the geode.
         * @param innerPlacements the inner placements to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder innerPlacement(BlockData... innerPlacements) {
            Collections.addAll(this.innerPlacements, innerPlacements);
            return this;
        }

        /**
         * Sets the block tag listing which blocks the geode cannot replace.
         * @param cannotReplace the block tag of blocks that cannot be replaced
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder cannotReplace(ResourceKey cannotReplace) {
            this.cannotReplace = cannotReplace;
            return this;
        }

        /**
         * Sets the block tag listing invalid blocks near which the geode will not generate.
         * @param invalidBlocks the block tag of invalid blocks
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder invalidBlocks(ResourceKey invalidBlocks) {
            this.invalidBlocks = invalidBlocks;
            return this;
        }

        /**
         * Builds the settings.
         * @return the settings
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public GeodeBlockSettings build() {
            Preconditions.checkNotNull(fillingProvider, "fillingProvider must be set");
            Preconditions.checkNotNull(innerLayerProvider, "innerLayerProvider must be set");
            Preconditions.checkNotNull(alternateInnerLayerProvider, "alternateInnerLayerProvider must be set");
            Preconditions.checkNotNull(middleLayerProvider, "middleLayerProvider must be set");
            Preconditions.checkNotNull(outerLayerProvider, "outerLayerProvider must be set");
            Preconditions.checkArgument(!innerPlacements.isEmpty(), "innerPlacements must not be empty");
            Preconditions.checkNotNull(cannotReplace, "cannotReplace must be set");
            Preconditions.checkNotNull(invalidBlocks, "invalidBlocks must be set");
            return of(
                fillingProvider, innerLayerProvider, alternateInnerLayerProvider, middleLayerProvider,
                outerLayerProvider, innerPlacements, cannotReplace, invalidBlocks
            );
        }
    }
}