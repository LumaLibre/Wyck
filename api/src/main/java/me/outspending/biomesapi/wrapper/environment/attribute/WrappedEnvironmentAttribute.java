package me.outspending.biomesapi.wrapper.environment.attribute;

import me.outspending.biomesapi.annotations.AsOf;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A wrapper for an environment attribute that allows for value conversion and storage.
 * The actual application to an NMS environment attribute map happens in the NMS module.
 *
 * @param <T> the type of the underlying environment attribute
 * @param <K> the type of the exposed 'api' value
 * @version 1.1.0
 * @since 1.1.0
 * @author Jsinco
 */
@AsOf("1.1.0")
public class WrappedEnvironmentAttribute<T, K> {

    private final EnvironmentAttributeHandle<T> attribute;
    private final Converter<T, K> converter;
    private @MonotonicNonNull K value;

    @AsOf("1.1.0")
    public WrappedEnvironmentAttribute(@NotNull EnvironmentAttributeHandle<T> attribute, @Nullable Converter<T, K> converter, @Nullable K value) {
        this.attribute = attribute;
        this.converter = converter;
        this.value = value;
    }

    @AsOf("1.1.0")
    public EnvironmentAttributeHandle<T> getAttribute() {
        return attribute;
    }

    @AsOf("1.1.0")
    public K getValue() {
        return value;
    }

    @AsOf("1.1.0")
    public void setValue(@NotNull K value) {
        this.value = value;
    }

    /**
     * Converts and gets the underlying value of type T.
     */
    @AsOf("1.1.0")
    @SuppressWarnings("unchecked")
    public T getConvertedValue() throws ClassCastException, IllegalStateException {
        if (converter == null && value != null) {
            return (T) value;
        } else if (converter == null) {
            throw new IllegalStateException("No converter defined for WrappedEnvironmentAttribute");
        }
        return converter.convert(value);
    }

    /**
     * Checks if this attribute is similar to another attribute. By comparing the attribute handles.
     * @param other the other WrappedEnvironmentAttribute to compare to
     * @return true if the attributes are similar, false otherwise
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    public boolean isSimilar(WrappedEnvironmentAttribute<?, ?> other) {
        return attribute.equals(other.attribute);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof WrappedEnvironmentAttribute<?, ?> other)) return false;
        return attribute.equals(other.attribute) &&
                ((value == null && other.value == null) || (value != null && value.equals(other.value)));
    }

    @Override
    public int hashCode() {
        int result = attribute.hashCode();
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return attribute + " = " + value;
    }

    @AsOf("1.1.0")
    @ApiStatus.Internal
    @SuppressWarnings("unchecked")
    public static <T, K> WrappedEnvironmentAttribute<T, K> of(@Nullable EnvironmentAttributeHandle<T> attribute) {
        if (attribute == null) {
            return null;
        }
        T defaultValue = EnvironmentAttributeFactory.WIRE.get().defaultValue(attribute);
        return new WrappedEnvironmentAttribute<>(attribute, null, (K) defaultValue);
    }

    @AsOf("1.1.0")
    @ApiStatus.Internal
    public static <T, K> WrappedEnvironmentAttribute<T, K> of(EnvironmentAttributeHandle<T> attribute, @NotNull K value) {
        return new WrappedEnvironmentAttribute<>(attribute, null, value);
    }

    @AsOf("1.1.0")
    public static <T, K> WrappedEnvironmentAttribute<T, K> of(EnvironmentAttributeHandle<T> attribute, Converter<T, K> converter) {
        return new WrappedEnvironmentAttribute<>(attribute, converter, null);
    }

    @AsOf("1.1.0")
    public static <T, K> WrappedEnvironmentAttributeSupplier<T, K> ofSupplier(EnvironmentAttributeHandle<T> attribute) {
        return new WrappedEnvironmentAttributeSupplier<>(of(attribute));
    }

    @AsOf("1.1.0")
    public static <T, K> WrappedEnvironmentAttributeSupplier<T, K> ofSupplier(EnvironmentAttributeHandle<T> attribute, @NotNull K value) {
        return new WrappedEnvironmentAttributeSupplier<>(of(attribute, value));
    }

    @AsOf("1.1.0")
    public static <T, K> WrappedEnvironmentAttributeSupplier<T, K> ofSupplier(EnvironmentAttributeHandle<T> attribute, Converter<T, K> converter) {
        return new WrappedEnvironmentAttributeSupplier<>(of(attribute, converter));
    }

    @AsOf("1.1.0")
    @FunctionalInterface
    public interface Converter<T, K> {
        @NotNull T convert(@NotNull K value);
    }
}