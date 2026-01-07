package me.outspending.biomesapi.wrapper.environment.attribute;

import me.outspending.biomesapi.annotations.AsOf;
import net.minecraft.world.attribute.EnvironmentAttributeMap;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * A transformer that converts a list of WrappedEnvironmentAttributes into an EnvironmentAttributeMap.
 *
 * @version 1.1.0
 * @since 1.1.0
 * @param attributes the list of WrappedEnvironmentAttributes to be transformed to a {@link EnvironmentAttributeMap}
 * @author Jsinco
 * @see WrappedEnvironmentAttributes
 */
@AsOf("1.1.0")
@ApiStatus.Experimental
public record WrappedEnvironmentAttributeMap(List<WrappedEnvironmentAttribute<?, ?>> attributes) {

    public static final WrappedEnvironmentAttributeMap EMPTY = new WrappedEnvironmentAttributeMap();

    /**
     * Constructs an EnvironmentAttributeMapTransformer with the given attributes.
     * @param attributes the attributes to be transformed
     */
    @AsOf("1.1.0")
    public WrappedEnvironmentAttributeMap(WrappedEnvironmentAttribute<?, ?>... attributes) {
        this(List.of(attributes));
    }

    /**
     * Transforms the list of WrappedEnvironmentAttributes into a {@link EnvironmentAttributeMap}.
     * @return the constructed {@link EnvironmentAttributeMap}
     */
    public EnvironmentAttributeMap transform() {
        EnvironmentAttributeMap.Builder builder = EnvironmentAttributeMap.builder();
        for (WrappedEnvironmentAttribute<?, ?> wrappedAttribute : attributes) {
            addToBuilder(builder, wrappedAttribute);
        }
        return builder.build();
    }

    public void applyToMap(EnvironmentAttributeMap map) {
        for (WrappedEnvironmentAttribute<?, ?> wrappedAttribute : attributes) {
            addToMap(map, wrappedAttribute);
        }
    }

    public void applyToMapBuilder(EnvironmentAttributeMap.Builder mapBuilder) {
        for (WrappedEnvironmentAttribute<?, ?> wrappedAttribute : attributes) {
            addToBuilder(mapBuilder, wrappedAttribute);
        }
    }

    public void applyToBiomeBuilder(Biome.BiomeBuilder builder) {
        for (WrappedEnvironmentAttribute<?, ?> wrappedAttribute : attributes) {
            addToBiomeBuilder(builder, wrappedAttribute);
        }
    }

    /**
     * Checks if the attribute map is empty.
     * @return true if the attribute map is empty, false otherwise
     */
    @AsOf("1.1.0")
    public boolean empty() {
        return attributes.isEmpty();
    }

    // Internal method to add a WrappedEnvironmentAttribute to the builder
    @AsOf("1.1.0")
    private <T, K> void addToBuilder(EnvironmentAttributeMap.Builder builder, WrappedEnvironmentAttribute<T, K> wrappedAttribute) {
        builder.set(wrappedAttribute.getAttribute(), wrappedAttribute.getConvertedValue());
    }

    private <T, K> void addToMap(EnvironmentAttributeMap map, WrappedEnvironmentAttribute<T, K> wrappedAttribute) {
        map.applyModifier(wrappedAttribute.getAttribute(), wrappedAttribute.getConvertedValue());
    }

    private <T, K> void addToBiomeBuilder(Biome.BiomeBuilder builder, WrappedEnvironmentAttribute<T, K> wrappedAttribute) {
        builder.setAttribute(wrappedAttribute.getAttribute(), wrappedAttribute.getConvertedValue());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof WrappedEnvironmentAttributeMap(List<WrappedEnvironmentAttribute<?, ?>> attributes1))) return false;
        return attributes.equals(attributes1);
    }

    @Override
    public int hashCode() {
        return attributes.hashCode();
    }

    /**
     * Creates a new Builder for {@link WrappedEnvironmentAttributeMap}.
     * @return a new Builder instance
     * @see WrappedEnvironmentAttributes
     */
    @AsOf("1.1.0")
    public static Builder builder() {
        return new Builder();
    }

    /**
     * A builder for creating {@link WrappedEnvironmentAttributeMap} instances.
     *
     * @version 1.1.0
     * @since 1.1.0
     * @author Jsinco
     * @see WrappedEnvironmentAttributes
     */
    @AsOf("1.1.0")
    public static class Builder {

        private final List<WrappedEnvironmentAttribute<?, ?>> attributes = new ArrayList<>();

        /**
         * Sets an attribute with the given WrappedEnvironmentAttributeSupplier and exposed value.
         * @param supplier the WrappedEnvironmentAttributeSupplier
         * @param exposedValue the value to be set for the attribute
         * @return the Builder instance for method chaining
         * @param <T> the type of the underlying EnvironmentAttribute
         * @param <K> the type of the exposed 'api' value
         * @see WrappedEnvironmentAttributes
         */
        @AsOf("1.1.0")
        public <T, K> Builder setAttribute(WrappedEnvironmentAttributeSupplier<T, K> supplier, K exposedValue) {
            WrappedEnvironmentAttribute<T, K> wrappedEnvironmentAttribute = supplier.get();
            wrappedEnvironmentAttribute.setValue(exposedValue);
            attributes.add(wrappedEnvironmentAttribute);
            return this;
        }

        /**
         * Builds the EnvironmentAttributeMapTransformer with the configured attributes.
         * @return the constructed EnvironmentAttributeMapTransformer
         */
        @AsOf("1.1.0")
        public WrappedEnvironmentAttributeMap build() {
            return new WrappedEnvironmentAttributeMap(attributes.toArray(new WrappedEnvironmentAttribute[0]));
        }
    }
}
