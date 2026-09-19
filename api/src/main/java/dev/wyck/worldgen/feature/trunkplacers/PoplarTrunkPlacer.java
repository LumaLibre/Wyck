package dev.wyck.worldgen.feature.trunkplacers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Places a poplar trunk with horizontal branches near the top.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Trunk_placer">Tree definition (Trunk placer)</a>
 * @since 4.0.0
 * @version 4.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("4.0.0")
public interface PoplarTrunkPlacer extends TrunkPlacer {

    /**
     * The number of trunk blocks above the branch layer.
     * @return the trunk height above branches
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    IntProvider trunkHeightAboveBranches();

    /**
     * The number of horizontal branches, between 1 and 4.
     * @return the branch amount
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    IntProvider branchAmount();

    /**
     * Converts this object back to a builder.
     * @return a builder containing the same values
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a poplar trunk placer.
     * @param baseHeight the base trunk height
     * @param heightRandA the first random height bound
     * @param heightRandB the second random height bound
     * @param trunkHeightAboveBranches the trunk height above branches, between 0 and 8
     * @param branchAmount the number of branches, between 1 and 4
     * @return a new poplar trunk placer
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static PoplarTrunkPlacer of(int baseHeight, int heightRandA, int heightRandB,IntProvider trunkHeightAboveBranches, IntProvider branchAmount) {
        record Holder() {
            static final ConstructWireProvider<PoplarTrunkPlacer> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.trunkplacers.PoplarTrunkPlacerImpl");
        }
        return Holder.WIRE.construct(
            baseHeight, heightRandA, heightRandB, trunkHeightAboveBranches, branchAmount
        );
    }

    /**
     * Creates a new builder.
     * @return a new poplar trunk placer builder
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link PoplarTrunkPlacer}.
     * @since 4.0.0
     * @version 4.0.0
     * @author Jsinco
     */
    @AsOf("4.0.0")
    final class Builder extends TrunkPlacerBuilder<Builder, PoplarTrunkPlacer> {
        private IntProvider trunkHeightAboveBranches = IntProvider.constant(0);
        private IntProvider branchAmount = IntProvider.constant(1);

        public Builder() {}

        public Builder(PoplarTrunkPlacer placer) {
            super(placer);
            this.trunkHeightAboveBranches = placer.trunkHeightAboveBranches();
            this.branchAmount = placer.branchAmount();
        }

        /**
         * Sets the trunk height above branches.
         * @param trunkHeightAboveBranches the height, between 0 and 8
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder trunkHeightAboveBranches(IntProvider trunkHeightAboveBranches) {
            this.trunkHeightAboveBranches = trunkHeightAboveBranches;
            return this;
        }

        /**
         * Sets the number of horizontal branches.
         * @param branchAmount the number of branches, between 1 and 4
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder branchAmount(IntProvider branchAmount) {
            this.branchAmount = branchAmount;
            return this;
        }

        @Override
        protected PoplarTrunkPlacer create() {
            Preconditions.checkArgument(trunkHeightAboveBranches.minInclusive() >= 0 && trunkHeightAboveBranches.maxInclusive() <= 8, "trunkHeightAboveBranches must be between 0 and 8");
            Preconditions.checkArgument(branchAmount.minInclusive() >= 1 && branchAmount.maxInclusive() <= 4, "branchAmount must be between 1 and 4");
            return of(baseHeight, heightRandA, heightRandB, trunkHeightAboveBranches, branchAmount);
        }
    }
}
