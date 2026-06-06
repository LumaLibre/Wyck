package me.outspending.biomesapi.wrapper.environment.attribute;

import me.outspending.biomesapi.annotations.AsOf;
import org.jspecify.annotations.NullMarked;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A collection of WrappedEnvironmentAttributes, keyed by their underlying attribute handle.
 * NMS-side code converts this into the underlying environment attribute map.
 *
 * @author Jsinco
 * @version 2.1.0
 * @see WrappedEnvironmentAttributes
 * @since 1.1.0
 */
@NullMarked
@AsOf("2.1.0")
public record WrappedEnvironmentAttributeMap(Map<EnvironmentAttributeHandle<?>, WrappedEnvironmentAttribute<?, ?>> attributes) {

    public static final WrappedEnvironmentAttributeMap EMPTY = new WrappedEnvironmentAttributeMap(Map.of());

    @AsOf("2.1.0")
    public WrappedEnvironmentAttributeMap {
        attributes = Map.copyOf(attributes);
    }

    /**
     * Returns the wrapped attributes as a collection. Order matches insertion order.
     * @return the wrapped attributes
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    public Collection<WrappedEnvironmentAttribute<?, ?>> values() {
        return attributes.values();
    }

    /**
     * @return true if the map is empty, false otherwise.
     * @since 1.1.0
     */
    @AsOf("1.1.0")
    public boolean empty() {
        return attributes.isEmpty();
    }

    /**
     * @return a new builder pre-populated with this map's attributes.
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    public Builder toBuilder() {
        Builder b = new Builder();
        b.attributes.putAll(attributes);
        return b;
    }

    /**
     * Creates a new WrappedEnvironmentAttributeMap with the given attribute added.
     * @param supplier the attribute supplier
     * @param value the value to set
     * @return a new WrappedEnvironmentAttributeMap with the given attribute added
     * @param <T> the type of the attribute
     * @param <K> the type of the exposed value
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    public <T, K> WrappedEnvironmentAttributeMap with(WrappedEnvironmentAttributeSupplier<T, K> supplier, K value) {
        WrappedEnvironmentAttribute<T, K> attr = supplier.unbox(value);
        Map<EnvironmentAttributeHandle<?>, WrappedEnvironmentAttribute<?, ?>> newAttrs = new LinkedHashMap<>(attributes);
        newAttrs.put(attr.getAttribute(), attr);
        return new WrappedEnvironmentAttributeMap(newAttrs);
    }

    /**
     * Creates a new WrappedEnvironmentAttributeMap with the given color attribute added.
     * @param supplier the color attribute supplier
     * @param hex the hex value (e.g. {@code "#FF10F0"})
     * @return a new WrappedEnvironmentAttributeMap with the given color attribute added
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    public WrappedEnvironmentAttributeMap with(IntColorSupplier supplier, String hex) {
        return with(supplier, IntColorSupplier.parseHex(hex));
    }

    /**
     * Creates a new Builder instance.
     * @return a new Builder instance
     * @since 1.1.0
     */
    @AsOf("1.1.0")
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Creates a new WrappedEnvironmentAttributeMap from the given attributes.
     * @param attributes the attributes to include
     * @return a new WrappedEnvironmentAttributeMap
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    public static WrappedEnvironmentAttributeMap of(WrappedEnvironmentAttribute<?, ?>... attributes) {
        Map<EnvironmentAttributeHandle<?>, WrappedEnvironmentAttribute<?, ?>> map = new LinkedHashMap<>();
        for (WrappedEnvironmentAttribute<?, ?> attribute : attributes) {
            EnvironmentAttributeHandle<?> key = attribute.getAttribute();
            if (map.containsKey(key)) {
                throw new IllegalArgumentException("Duplicate attribute: " + key);
            }
            map.put(key, attribute);
        }
        return new WrappedEnvironmentAttributeMap(map);
    }

    /**
     * A builder for creating WrappedEnvironmentAttributeMap instances.
     *
     * @author Jsinco
     * @version 2.1.0
     * @since 1.1.0
     */
    @AsOf("2.1.0")
    public static class Builder {

        private final Map<EnvironmentAttributeHandle<?>, WrappedEnvironmentAttribute<?, ?>> attributes = new LinkedHashMap<>();

        /**
         * Sets an attribute in the builder.
         * If an attribute with the same handle is already present,
         * this throws to flag the duplicate to the caller.
         *
         * @param supplier the attribute supplier
         * @param value    the exposed value
         * @param <T>      the type of the attribute
         * @param <K>      the type of the exposed value
         * @return the builder
         * @since 1.1.0
         */
        @AsOf("1.1.0")
        public <T, K> Builder setAttribute(
                WrappedEnvironmentAttributeSupplier<T, K> supplier,
                K value) {
            WrappedEnvironmentAttribute<T, K> wrappedEnvironmentAttribute = supplier.get();
            wrappedEnvironmentAttribute.setValue(value);

            EnvironmentAttributeHandle<?> key = wrappedEnvironmentAttribute.getAttribute();
            if (attributes.containsKey(key)) {
                throw new IllegalArgumentException("Attribute: " + key + " is already present.");
            }
            attributes.put(key, wrappedEnvironmentAttribute);
            return this;
        }

        /**
         * Sets a color attribute in the builder using a hex string.
         *
         * @param supplier the color attribute supplier
         * @param hex      the hex value (e.g. {@code "#FF10F0"})
         * @return the builder
         * @since 2.1.0
         */
        @AsOf("2.1.0")
        public Builder setAttribute(IntColorSupplier supplier, String hex) {
            return setAttribute(supplier, IntColorSupplier.parseHex(hex));
        }

        /**
         * Removes all attributes from this builder.
         * @return the builder
         * @since 2.1.0
         */
        @AsOf("2.1.0")
        public Builder clear() {
            attributes.clear();
            return this;
        }

        /**
         * Adds all attributes from the given map. Throws if any key collides with an existing entry.
         * @param source the map to add
         * @return the builder
         * @since 2.1.0
         */
        @AsOf("2.1.0")
        public Builder putAll(WrappedEnvironmentAttributeMap source) {
            for (var entry : source.attributes().entrySet()) {
                if (attributes.containsKey(entry.getKey())) {
                    throw new IllegalArgumentException("Attribute: " + entry.getKey() + " is already present.");
                }
                attributes.put(entry.getKey(), entry.getValue());
            }
            return this;
        }

        /**
         * Builds the WrappedEnvironmentAttributeMap.
         *
         * @return the WrappedEnvironmentAttributeMap
         * @since 1.1.0
         */
        @AsOf("1.1.0")
        public WrappedEnvironmentAttributeMap build() {
            return new WrappedEnvironmentAttributeMap(attributes);
        }
    }
}