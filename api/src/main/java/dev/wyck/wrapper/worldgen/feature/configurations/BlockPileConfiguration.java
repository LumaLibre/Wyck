package dev.wyck.wrapper.worldgen.feature.configurations;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.worldgen.stateproviders.BlockStateProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * A feature that generates up to 25 specific blocks.
 *
 * @see <a href="https://minecraft.wiki/w/Pile">Pile</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface BlockPileConfiguration extends FeatureConfiguration {

    @ApiStatus.Internal
    ConstructWireProvider<BlockPileConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.feature.configurations.BlockPileConfigurationImpl");

    /**
     * The block state provider used for the blocks in the pile.
     * @return the state provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockStateProvider stateProvider();

    /**
     * Creates a new block pile configuration.
     * @param stateProvider the block state provider used for the blocks in the pile
     * @return a new block pile configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BlockPileConfiguration of(BlockStateProvider stateProvider) {
        return WIRE.construct(stateProvider);
    }
}
