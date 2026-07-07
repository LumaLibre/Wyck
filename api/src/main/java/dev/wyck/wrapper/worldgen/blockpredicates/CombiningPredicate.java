package dev.wyck.wrapper.worldgen.blockpredicates;

import dev.wyck.annotations.AsOf;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A predicate that combines multiple other predicates.
 *
 * @see <a href="https://minecraft.wiki/w/Block_predicate">Block predicate</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface CombiningPredicate extends BlockPredicate {

    /**
     * The child predicates.
     * @return the predicates
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    List<BlockPredicate> predicates();

    // Friendly static accessors

    /**
     * Creates a new all of predicate builder.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static AllOfPredicate.Builder allOf() {
        return AllOfPredicate.builder();
    }

    /**
     * Creates a new any of predicate builder.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static AnyOfPredicate.Builder anyOf() {
        return AnyOfPredicate.builder();
    }

    /**
     * Base builder for combining predicates.
     * @param <T> the concrete predicate type
     * @param <B> the concrete builder type
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    @SuppressWarnings("unchecked")
    abstract class CombiningPredicateBuilder<T extends CombiningPredicate, B extends CombiningPredicateBuilder<T, B>> {
        protected List<BlockPredicate> predicates = new ArrayList<>();

        protected CombiningPredicateBuilder() {}

        protected CombiningPredicateBuilder(CombiningPredicate predicate) {
            this.predicates.addAll(predicate.predicates());
        }

        /**
         * Sets the predicates.
         * @param predicates the predicates
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public B predicates(List<BlockPredicate> predicates) {
            this.predicates = predicates;
            return (B) this;
        }

        /**
         * Sets the predicates.
         * @param predicates the predicates
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public B predicates(BlockPredicate... predicates) {
            this.predicates = new ArrayList<>(List.of(predicates));
            return (B) this;
        }

        /**
         * Adds multiple predicates to the list.
         * @param predicates the predicates to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public B predicate(BlockPredicate... predicates) {
            Collections.addAll(this.predicates, predicates);
            return (B) this;
        }

        /**
         * Adds a predicate to the list.
         * @param predicate the predicate to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public B predicate(BlockPredicate predicate) {
            this.predicates.add(predicate);
            return (B) this;
        }

        /**
         * Builds the predicate.
         * @return the predicate
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public final T build() {
            return create();
        }

        protected abstract T create();
    }
}
