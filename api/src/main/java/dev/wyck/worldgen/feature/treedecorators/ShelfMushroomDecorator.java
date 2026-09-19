package dev.wyck.worldgen.feature.treedecorators;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Places shelf mushrooms on standing trees and fallen logs.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Decorator">Tree definition (Decorator)</a>
 * @since 4.0.0
 * @version 4.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("4.0.0")
public interface ShelfMushroomDecorator extends TreeDecorator {

    /**
     * The probability that shelf mushrooms are placed.
     * @return the placement probability
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    float probability();

    /**
     * Converts this object back to a builder.
     * @return a builder containing the same value
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a shelf mushroom decorator.
     * @param probability the placement probability, between 0 and 1
     * @return a new shelf mushroom decorator
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static ShelfMushroomDecorator of(float probability) {
        record Holder() {
            static final ConstructWireProvider<ShelfMushroomDecorator> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.treedecorators.ShelfMushroomDecoratorImpl");
        }
        return Holder.WIRE.construct(probability);
    }

    /**
     * Creates a new builder.
     * @return a new shelf mushroom decorator builder
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link ShelfMushroomDecorator}.
     * @since 4.0.0
     * @version 4.0.0
     * @author Jsinco
     */
    @AsOf("4.0.0")
    final class Builder {
        private float probability;

        public Builder() {}

        public Builder(ShelfMushroomDecorator decorator) {
            this.probability = decorator.probability();
        }

        /**
         * Sets the placement probability.
         * @param probability the probability, between 0 and 1
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder probability(float probability) {
            this.probability = probability;
            return this;
        }

        /**
         * Builds the shelf mushroom decorator.
         * @return a new shelf mushroom decorator
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public ShelfMushroomDecorator build() {
            Preconditions.checkArgument(probability >= 0.0F && probability <= 1.0F, "probability must be between 0 and 1");
            return of(probability);
        }
    }
}
