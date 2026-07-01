package me.outspending.biomesapi.wrapper.environment.attribute;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import me.outspending.biomesapi.annotations.AsOf;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

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
@NullMarked
@AsOf("1.1.0")
public class EnvironmentAttribute<T, K> {

    public static final Codec<EnvironmentAttribute<?, ?>> CODEC = EnvironmentAttributeHandle.CODEC.dispatch(
        "attribute",
        EnvironmentAttribute::getAttribute,
        EnvironmentAttribute::valueCodecFor
    );

    private final EnvironmentAttributeHandle<T> attribute;
    private final @Nullable Converter<T, K> converter;
    private @MonotonicNonNull K value;

    @AsOf("1.1.0")
    public EnvironmentAttribute(EnvironmentAttributeHandle<T> attribute, @Nullable Converter<T, K> converter, @Nullable K value) {
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
    public void setValue(K value) {
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
    public boolean isSimilar(EnvironmentAttribute<?, ?> other) {
        return attribute.equals(other.attribute);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof EnvironmentAttribute<?, ?> other)) return false;
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
    public static <T, K> EnvironmentAttribute<T, K> of(@Nullable EnvironmentAttributeHandle<T> attribute) {
        Preconditions.checkNotNull(attribute, "attribute cannot be null");
        T defaultValue = EnvironmentAttributeFactory.WIRE.get().defaultValue(attribute);
        return new EnvironmentAttribute<>(attribute, null, (K) defaultValue);
    }

    @AsOf("1.1.0")
    @ApiStatus.Internal
    public static <T, K> EnvironmentAttribute<T, K> of(EnvironmentAttributeHandle<T> attribute, K value) {
        return new EnvironmentAttribute<>(attribute, null, value);
    }

    @AsOf("1.1.0")
    public static <T, K> EnvironmentAttribute<T, K> of(EnvironmentAttributeHandle<T> attribute, Converter<T, K> converter) {
        return new EnvironmentAttribute<>(attribute, converter, null);
    }

    @AsOf("1.1.0")
    public static <T, K> EnvironmentAttributeSupplier<T, K> ofSupplier(EnvironmentAttributeHandle<T> attribute) {
        return new EnvironmentAttributeSupplier<>(of(attribute));
    }

    @AsOf("1.1.0")
    public static <T, K> EnvironmentAttributeSupplier<T, K> ofSupplier(EnvironmentAttributeHandle<T> attribute, K value) {
        return new EnvironmentAttributeSupplier<>(of(attribute, value));
    }

    @AsOf("1.1.0")
    public static <T, K> EnvironmentAttributeSupplier<T, K> ofSupplier(EnvironmentAttributeHandle<T> attribute, Converter<T, K> converter) {
        return new EnvironmentAttributeSupplier<>(of(attribute, converter));
    }

    @AsOf("1.1.0")
    @FunctionalInterface
    public interface Converter<T, K> {
        T convert(K value);
    }

    private static <V> MapCodec<EnvironmentAttribute<?, ?>> valueCodecFor(EnvironmentAttributeHandle<V> handle) {
        return handle.valueCodec().fieldOf("value").xmap(
            value -> EnvironmentAttribute.of(handle, value),
            attr -> castValue(attr, handle)
        );
    }

    @SuppressWarnings("unchecked")
    private static <V> V castValue(EnvironmentAttribute<?, ?> attr, EnvironmentAttributeHandle<V> handle) {
        return (V) attr.getConvertedValue();
    }
}