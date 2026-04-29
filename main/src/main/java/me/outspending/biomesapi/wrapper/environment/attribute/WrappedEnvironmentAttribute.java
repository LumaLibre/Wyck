package me.outspending.biomesapi.wrapper.environment.attribute;

import me.outspending.biomesapi.api.annotations.AsOf;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.attribute.EnvironmentAttributeMap;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A wrapper for EnvironmentAttribute that allows for value conversion and application to an EnvironmentAttributeMap.
 * @param <T> the type of the underlying EnvironmentAttribute
 * @param <K> the type of the exposed 'api' value
 * @version 1.1.0
 * @since 1.1.0
 * @author Jsinco
 */
@AsOf("1.1.0")
@ApiStatus.Experimental
public class WrappedEnvironmentAttribute<T, K> {

    private final EnvironmentAttribute<@NotNull T> attribute;
    private final Converter<T, K> converter;
    private @MonotonicNonNull K value;

    /**
     * Creates a new WrappedEnvironmentAttribute with the given attribute, converter, and value.
     * @param attribute The NMS EnvironmentAttribute to wrap
     * @param converter The converter to convert K to T
     * @param value The value of type K
     */
    @AsOf("1.1.0")
    public WrappedEnvironmentAttribute(@NotNull EnvironmentAttribute<@NotNull T> attribute, @Nullable Converter<T, K> converter, @Nullable K value) {
        this.attribute = attribute;
        this.converter = converter;
        this.value = value;
    }

    /**
     * Gets the wrapped EnvironmentAttribute.
     * @return the wrapped EnvironmentAttribute
     */
    @AsOf("1.1.0")
    public EnvironmentAttribute<@NotNull T> getAttribute() {
        return attribute;
    }

    /**
     * Gets the exposed value.
     * @return the exposed value
     */
    @AsOf("1.1.0")
    public K getValue() {
        return value;
    }

    /**
     * Sets the exposed value.
     * @param value the exposed value to set
     */
    @AsOf("1.1.0")
    public void setValue(@NotNull K value) {
        this.value = value;
    }

    /**
     * Converts and gets the underlying value of type T.
     * @return the converted value of type T
     * @throws ClassCastException if conversion fails
     * @throws IllegalStateException if no converter is defined and the value is null
     */
    @AsOf("1.1.0")
    public T getConvertedValue() throws ClassCastException, IllegalStateException {
        if (converter == null && value != null) {
            return (T) value;
        } else if (converter == null) {
            throw new IllegalStateException("No converter defined for WrappedEnvironmentAttribute");
        }
        return converter.convert(value);
    }

    /**
     * Applies the converted value to the given EnvironmentAttributeMap.
     * @param environment the EnvironmentAttributeMap to apply the value to
     */
    @AsOf("1.1.0")
    public void applyToEnvironment(EnvironmentAttributeMap environment) {
        environment.applyModifier(attribute, getConvertedValue());
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

    /**
     * Creates a WrappedEnvironmentAttribute from the given EnvironmentAttribute.
     * @param attribute the EnvironmentAttribute to wrap
     * @return the created WrappedEnvironmentAttribute
     * @param <T> the type of the underlying EnvironmentAttribute
     * @param <K> the type of the exposed 'api' value
     */
    @AsOf("1.1.0")
    @ApiStatus.Internal
    public static <T, K> WrappedEnvironmentAttribute<T, K> of(EnvironmentAttribute<@NotNull T> attribute) {
        return new WrappedEnvironmentAttribute<>(attribute, null, (K) attribute.defaultValue());
    }

    /**
     * Creates a WrappedEnvironmentAttribute from the given EnvironmentAttribute and value.
     * @param attribute the EnvironmentAttribute to wrap
     * @param value the value of type K
     * @return the created WrappedEnvironmentAttribute
     * @param <T> the type of the underlying EnvironmentAttribute
     * @param <K> the type of the exposed 'api' value
     */
    @AsOf("1.1.0")
    @ApiStatus.Internal
    public static <T, K> WrappedEnvironmentAttribute<T, K> of(EnvironmentAttribute<@NotNull T> attribute, @NotNull K value) {
        return new WrappedEnvironmentAttribute<>(attribute, null, value);
    }

    /**
     * Creates a WrappedEnvironmentAttribute from the given EnvironmentAttribute and converter.
     * @param attribute the EnvironmentAttribute to wrap
     * @param converter the converter to convert K to T
     * @return the created WrappedEnvironmentAttribute
     * @param <T> the type of the underlying EnvironmentAttribute
     * @param <K> the type of the exposed 'api' value
     */
    @AsOf("1.1.0")
    public static <T, K> WrappedEnvironmentAttribute<T, K> of(EnvironmentAttribute<@NotNull T> attribute, Converter<T, K> converter) {
        return new WrappedEnvironmentAttribute<>(attribute, converter, null);
    }

    /**
     * Creates a WrappedEnvironmentAttributeSupplier from the given EnvironmentAttribute.
     * @param attribute the EnvironmentAttribute to wrap
     * @return the created WrappedEnvironmentAttributeSupplier
     * @param <T> the type of the underlying EnvironmentAttribute
     * @param <K> the type of the exposed 'api' value
     */
    @AsOf("1.1.0")
    public static <T, K> WrappedEnvironmentAttributeSupplier<T, K> ofSupplier(EnvironmentAttribute<@NotNull T> attribute) {
        return new WrappedEnvironmentAttributeSupplier<>(of(attribute));
    }

    /**
     * Creates a WrappedEnvironmentAttributeSupplier from the given EnvironmentAttribute and value.
     * @param attribute the EnvironmentAttribute to wrap
     * @param value the value of type K
     * @return the created WrappedEnvironmentAttributeSupplier
     * @param <T> the type of the underlying EnvironmentAttribute
     * @param <K> the type of the exposed 'api' value
     */
    @AsOf("1.1.0")
    public static <T, K> WrappedEnvironmentAttributeSupplier<T, K> ofSupplier(EnvironmentAttribute<@NotNull T> attribute, @NotNull K value) {
        return new WrappedEnvironmentAttributeSupplier<>(of(attribute, value));
    }

    /**
     * Creates a WrappedEnvironmentAttributeSupplier from the given EnvironmentAttribute and converter.
     * @param attribute the EnvironmentAttribute to wrap
     * @param converter the converter to convert K to T
     * @return the created WrappedEnvironmentAttributeSupplier
     * @param <T> the type of the underlying EnvironmentAttribute
     * @param <K> the type of the exposed 'api' value
     */
    @AsOf("1.1.0")
    public static <T, K> WrappedEnvironmentAttributeSupplier<T, K> ofSupplier(EnvironmentAttribute<@NotNull T> attribute, Converter<T, K> converter) {
        return new WrappedEnvironmentAttributeSupplier<>(of(attribute, converter));
    }

    /**
     * Converts a value of type K to type T.
     * @param <T> the type of the underlying EnvironmentAttribute
     * @param <K> the type of the exposed 'api' value
     */
    @AsOf("1.1.0")
    @FunctionalInterface
    public interface Converter<T, K> {
        @NotNull T convert(@NotNull K value);
    }
}
