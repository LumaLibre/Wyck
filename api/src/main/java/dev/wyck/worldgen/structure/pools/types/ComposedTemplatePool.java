package dev.wyck.worldgen.structure.pools.types;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.structure.pools.PoolElement;
import dev.wyck.worldgen.structure.pools.TemplatePool;
import dev.wyck.wrapper.Registerable;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A template pool authored from, or decoded into, its weighted elements and fallback.
 *
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
public interface ComposedTemplatePool extends TemplatePool, Registerable<ComposedTemplatePool> {

    int MAX_WEIGHT = 150;

    /**
     * The resource key of the template pool.
     * @return the resource key of the template pool, if present
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    Optional<ResourceKey> resourceKey();

    /**
     * The pool used when a jigsaw block's piece from this pool cannot fit, or when the maximum
     * depth is reached.
     * @return the fallback pool
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    TemplatePool fallback();

    /**
     * The weighted elements of this pool, in order.
     * @return the elements of this pool
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    List<Entry> elements();

    /**
     * Converts this object back to a builder.
     * @return a builder with the same values as this object
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new composed template pool.
     * @param resourceKey the resource key of the pool, or null if not present
     * @param fallback the pool used when a piece cannot fit
     * @param elements the weighted elements of the pool
     * @return a new composed template pool
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static ComposedTemplatePool of(@Nullable ResourceKey resourceKey, TemplatePool fallback, List<Entry> elements) {
        record Holder() {
            static final ConstructWireProvider<ComposedTemplatePool> WIRE =
                ConstructWireProvider.create("dev.wyck.worldgen.structure.pools.types.ComposedTemplatePoolImpl");
        }
        return Holder.WIRE.construct(Optional.ofNullable(resourceKey), fallback, List.copyOf(elements));
    }

    /**
     * Creates a new builder.
     * @return a new builder
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * One element of a pool and the weight it is drawn with.
     *
     * @param element the pool element
     * @param weight the weight the element is drawn with, from 1 to {@value #MAX_WEIGHT}
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    record Entry(PoolElement element, int weight) {
        public Entry {
            Preconditions.checkArgument(weight >= 1 && weight <= MAX_WEIGHT, "weight must be between 1 and %s, got %s", MAX_WEIGHT, weight);
        }

        /**
         * Creates a new entry.
         * @param element the pool element
         * @param weight the weight the element is drawn with
         * @return a new entry
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public static Entry of(PoolElement element, int weight) {
            return new Entry(element, weight);
        }

        /**
         * Creates a new entry with a weight of 1.
         * @param element the pool element
         * @return a new entry
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public static Entry of(PoolElement element) {
            return new Entry(element, 1);
        }
    }

    /**
     * Builder for {@link ComposedTemplatePool}.
     * @since 3.4.0
     * @version 3.4.0
     * @author Jsinco
     */
    @AsOf("3.4.0")
    final class Builder {
        private @Nullable ResourceKey resourceKey;
        private TemplatePool fallback = TemplatePool.EMPTY;
        private List<Entry> elements = new ArrayList<>();

        public Builder() {}

        public Builder(ComposedTemplatePool pool) {
            this.resourceKey = pool.resourceKey().orElse(null);
            this.fallback = pool.fallback();
            this.elements = new ArrayList<>(pool.elements());
        }

        /**
         * Sets the resource key of the template pool.
         * @param resourceKey the resource key of the template pool
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder resourceKey(ResourceKey resourceKey) {
            this.resourceKey = resourceKey;
            return this;
        }

        /**
         * Sets the pool used when a piece from this pool cannot fit.
         * @param fallback the fallback pool
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder fallback(TemplatePool fallback) {
            this.fallback = fallback;
            return this;
        }

        /**
         * Sets the weighted elements of this pool.
         * @param elements the elements of this pool
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder elements(List<Entry> elements) {
            this.elements = new ArrayList<>(elements);
            return this;
        }

        // Friendly

        /**
         * Adds an element with a weight of 1.
         * @param element the element to add
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder element(PoolElement element) {
            this.elements.add(Entry.of(element));
            return this;
        }

        /**
         * Adds an element with the given weight.
         * @param element the element to add
         * @param weight the weight the element is drawn with
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder element(PoolElement element, int weight) {
            this.elements.add(Entry.of(element, weight));
            return this;
        }

        /**
         * Builds the composed template pool.
         * @return the composed template pool
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public ComposedTemplatePool build() {
            return of(resourceKey, fallback, elements);
        }
    }
}
