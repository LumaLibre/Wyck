package dev.wyck.worldgen.feature.foliageplacers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Places the tall, tapered foliage used by poplar trees.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Foliage_placer">Tree definition (Foliage placer)</a>
 * @since 4.0.0
 * @version 4.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("4.0.0")
public interface PoplarFoliagePlacer extends FoliagePlacer {

    /**
     * The foliage height, between 5 and 16 blocks.
     * @return the foliage height
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    IntProvider height();

    /**
     * The chance that an eligible side position is left empty.
     * @return the side-hole chance
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    float sideHoleChance();

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
     * Creates a poplar foliage placer.
     * @param radius the foliage radius
     * @param offset the vertical foliage offset
     * @param height the foliage height, between 5 and 16 blocks
     * @param sideHoleChance the side-hole chance, between 0 and 1
     * @return a new poplar foliage placer
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static PoplarFoliagePlacer of(IntProvider radius, IntProvider offset, IntProvider height, float sideHoleChance) {
        record Holder() {
            static final ConstructWireProvider<PoplarFoliagePlacer> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.foliageplacers.PoplarFoliagePlacerImpl");
        }
        return Holder.WIRE.construct(radius, offset, height, sideHoleChance);
    }

    /**
     * Creates a new builder.
     * @return a new poplar foliage placer builder
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link PoplarFoliagePlacer}.
     * @since 4.0.0
     * @version 4.0.0
     * @author Jsinco
     */
    @AsOf("4.0.0")
    final class Builder extends FoliagePlacerBuilder<Builder, PoplarFoliagePlacer> {
        private IntProvider height = IntProvider.constant(5);
        private float sideHoleChance;

        public Builder() {}

        public Builder(PoplarFoliagePlacer placer) {
            super(placer);
            this.height = placer.height();
            this.sideHoleChance = placer.sideHoleChance();
        }

        /**
         * Sets the foliage height.
         * @param height the foliage height, between 5 and 16 blocks
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder height(IntProvider height) {
            this.height = height;
            return this;
        }

        /**
         * Sets the chance that an eligible side position is left empty.
         * @param sideHoleChance the chance, between 0 and 1
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder sideHoleChance(float sideHoleChance) {
            this.sideHoleChance = sideHoleChance;
            return this;
        }

        @Override
        protected PoplarFoliagePlacer create() {
            Preconditions.checkArgument(height.minInclusive() >= 5 && height.maxInclusive() <= 16, "height must be between 5 and 16");
            Preconditions.checkArgument(sideHoleChance >= 0.0F && sideHoleChance <= 1.0F, "sideHoleChance must be between 0 and 1");
            return of(radius, offset, height, sideHoleChance);
        }
    }
}
