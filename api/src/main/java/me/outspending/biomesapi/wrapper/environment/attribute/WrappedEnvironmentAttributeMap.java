package me.outspending.biomesapi.wrapper.environment.attribute;

import me.outspending.biomesapi.annotations.AsOf;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * A collection of WrappedEnvironmentAttributes. NMS-side code converts this
 * into the underlying environment attribute map.
 *
 * @version 1.1.0
 * @since 1.1.0
 * @param attributes the list of WrappedEnvironmentAttributes
 * @author Jsinco
 * @see WrappedEnvironmentAttributes
 */
@AsOf("1.1.0")
@ApiStatus.Experimental
public record WrappedEnvironmentAttributeMap(List<WrappedEnvironmentAttribute<?, ?>> attributes) {

    public static final WrappedEnvironmentAttributeMap EMPTY = new WrappedEnvironmentAttributeMap();

    @AsOf("1.1.0")
    public WrappedEnvironmentAttributeMap(WrappedEnvironmentAttribute<?, ?>... attributes) {
        this(List.of(attributes));
    }

    @AsOf("1.1.0")
    public boolean empty() {
        return attributes.isEmpty();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof WrappedEnvironmentAttributeMap(List<WrappedEnvironmentAttribute<?, ?>> attributes1)))
            return false;
        return attributes.equals(attributes1);
    }

    @Override
    public int hashCode() {
        return attributes.hashCode();
    }

    @AsOf("1.1.0")
    public static Builder builder() {
        return new Builder();
    }

    @AsOf("1.1.0")
    public static class Builder {

        private final List<WrappedEnvironmentAttribute<?, ?>> attributes = new ArrayList<>();

        @AsOf("1.1.0")
        public <T, K> Builder setAttribute(WrappedEnvironmentAttributeSupplier<T, K> supplier, K exposedValue) {
            WrappedEnvironmentAttribute<T, K> wrappedEnvironmentAttribute = supplier.get();
            wrappedEnvironmentAttribute.setValue(exposedValue);
            attributes.add(wrappedEnvironmentAttribute);
            return this;
        }

        @AsOf("1.1.0")
        public WrappedEnvironmentAttributeMap build() {
            return new WrappedEnvironmentAttributeMap(attributes.toArray(new WrappedEnvironmentAttribute[0]));
        }
    }
}