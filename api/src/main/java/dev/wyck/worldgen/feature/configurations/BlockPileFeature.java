package dev.wyck.worldgen.feature.configurations;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
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
public interface BlockPileFeature extends FeatureConfiguration {

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
    static BlockPileFeature of(BlockStateProvider stateProvider) {
        record Holder() {
            static final ConstructWireProvider<BlockPileFeature> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.BlockPileFeatureImpl");
        }
        return Holder.WIRE.construct(stateProvider);
    }
}
