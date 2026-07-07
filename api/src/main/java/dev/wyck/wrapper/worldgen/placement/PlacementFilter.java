package dev.wyck.wrapper.worldgen.placement;

import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.worldgen.blockpredicates.BlockPredicate;
import org.jspecify.annotations.NullMarked;

/**
 * An abstract placement filter.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface PlacementFilter extends PlacementModifier {

    /**
     * An instance of the biome filter.
     * @return the biome filter
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BiomeFilter biome() {
        return BiomeFilter.INSTANCE;
    }

    /**
     * A new instance of a block predicate filter.
     * @param predicate the block predicate
     * @return a new block predicate filter
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BlockPredicateFilter blockPredicate(BlockPredicate predicate) {
        return BlockPredicateFilter.of(predicate);
    }

    /**
     * Creates a new rarity filter.
     * @param chance the average chance denominator, a positive integer
     * @return a new rarity filter
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static RarityFilter rarity(int chance) {
        return RarityFilter.of(chance);
    }

    /**
     * Creates a new surface relative threshold filter.
     * @return a new surface relative threshold filter builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SurfaceRelativeThresholdFilter.Builder surfaceRelativeThreshold() {
        return SurfaceRelativeThresholdFilter.builder();
    }

    /**
     * Creates a new surface water depth filter.
     * @param maxWaterDepth the maximum allowed water depth
     * @return a new surface water depth filter
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SurfaceWaterDepthFilter surfaceWaterDepth(int maxWaterDepth) {
        return SurfaceWaterDepthFilter.of(maxWaterDepth);
    }
}
