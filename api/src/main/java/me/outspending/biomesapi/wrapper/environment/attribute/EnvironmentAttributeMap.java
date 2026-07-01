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
 * @see EnvironmentAttributes
 * @since 1.1.0
 */
@NullMarked
@AsOf("2.1.0")
public record EnvironmentAttributeMap(Map<EnvironmentAttributeHandle<?>, EnvironmentAttribute<?, ?>> attributes) {

    public static final EnvironmentAttributeMap EMPTY = new EnvironmentAttributeMap(Map.of());

    @AsOf("2.1.0")
    public EnvironmentAttributeMap {
        attributes = Map.copyOf(attributes);
    }

    /**
     * Returns the wrapped attributes as a collection. Order matches insertion order.
     * @return the wrapped attributes
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    public Collection<EnvironmentAttribute<?, ?>> values() {
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
    public <T, K> EnvironmentAttributeMap with(EnvironmentAttributeSupplier<T, K> supplier, K value) {
        EnvironmentAttribute<T, K> attr = supplier.unbox(value);
        Map<EnvironmentAttributeHandle<?>, EnvironmentAttribute<?, ?>> newAttrs = new LinkedHashMap<>(attributes);
        newAttrs.put(attr.getAttribute(), attr);
        return new EnvironmentAttributeMap(newAttrs);
    }

    /**
     * Creates a new WrappedEnvironmentAttributeMap with the given color attribute added.
     * @param supplier the color attribute supplier
     * @param hex the hex value (e.g. {@code "#FF10F0"})
     * @return a new WrappedEnvironmentAttributeMap with the given color attribute added
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    public EnvironmentAttributeMap with(IntColorSupplier supplier, String hex) {
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
    public static EnvironmentAttributeMap of(EnvironmentAttribute<?, ?>... attributes) {
        Map<EnvironmentAttributeHandle<?>, EnvironmentAttribute<?, ?>> map = new LinkedHashMap<>();
        for (EnvironmentAttribute<?, ?> attribute : attributes) {
            EnvironmentAttributeHandle<?> key = attribute.getAttribute();
            if (map.containsKey(key)) {
                throw new IllegalArgumentException("Duplicate attribute: " + key);
            }
            map.put(key, attribute);
        }
        return new EnvironmentAttributeMap(map);
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

        private final Map<EnvironmentAttributeHandle<?>, EnvironmentAttribute<?, ?>> attributes = new LinkedHashMap<>();

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
                EnvironmentAttributeSupplier<T, K> supplier,
                K value) {
            EnvironmentAttribute<T, K> wrappedEnvironmentAttribute = supplier.get();
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
        public Builder putAll(EnvironmentAttributeMap source) {
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
        public EnvironmentAttributeMap build() {
            return new EnvironmentAttributeMap(attributes);
        }
    }
}