package dev.wyck.worldgen.feature.trunkplacers;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Places a 2×2 trunk of the specified height.
 * Additionally, places columns of branches in a 4×4 area around the stem in the topmost 5 blocks of the trunk.
 * Foliage is attached to the top of the trunk as well as the top of each branch.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Trunk_placer">Tree definition (Trunk placer)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface DarkOakTrunkPlacer extends TrunkPlacer {

    @ApiStatus.Internal
    ConstructWireProvider<DarkOakTrunkPlacer> WIRE = ConstructWireProvider.construct("dev.wyck.worldgen.feature.trunkplacers.DarkOakTrunkPlacerImpl");

    /**
     * Converts this object back to a builder.
     * @return the builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new dark oak trunk placer.
     * @param baseHeight the height of the trunk
     * @param heightRandA the random height modifier
     * @param heightRandB the random height modifier
     * @return a new dark oak trunk placer
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static DarkOakTrunkPlacer of(int baseHeight, int heightRandA, int heightRandB) {
        return WIRE.construct(baseHeight, heightRandA, heightRandB);
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
     * Builder for {@link DarkOakTrunkPlacer}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends TrunkPlacerBuilder<Builder, DarkOakTrunkPlacer> {

        public Builder() {}

        public Builder(DarkOakTrunkPlacer trunkPlacer) {
            super(trunkPlacer);
        }

        @Override
        protected DarkOakTrunkPlacer create() {
            return of(baseHeight, heightRandA, heightRandB);
        }
    }
}
