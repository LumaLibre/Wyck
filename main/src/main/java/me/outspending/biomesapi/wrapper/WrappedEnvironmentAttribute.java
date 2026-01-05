package me.outspending.biomesapi.wrapper;

import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.attribute.EnvironmentAttributeMap;
import org.jetbrains.annotations.NotNull;

public class WrappedEnvironmentAttribute<T, K> {

    private final EnvironmentAttribute<@NotNull T> attribute;
    private K value;
    private Converter<T, K> converter;

    public WrappedEnvironmentAttribute(@NotNull EnvironmentAttribute<@NotNull T> attribute, @NotNull K value, Converter<T, K> converter) {
        this.attribute = attribute;
        this.value = value;
        this.converter = converter;
    }

    public EnvironmentAttribute<@NotNull T> getAttribute() {
        return attribute;
    }

    public K getValue() {
        return value;
    }

    public T getConvertedValue() {
        if (converter == null && value != null) {
            return (T) value;
        } else if (converter == null) {
            throw new IllegalStateException("No converter defined for WrappedEnvironmentAttribute");
        }
        return converter.convert(value);
    }

    public void setValue(@NotNull K value) {
        this.value = value;
    }

    public void applyToEnvironment(EnvironmentAttributeMap environment) {
        environment.applyModifier(attribute, getConvertedValue());
    }

    public static <T, K> WrappedEnvironmentAttribute<T, K> of(EnvironmentAttribute<@NotNull T> attribute) {
        return new WrappedEnvironmentAttribute<>(attribute, (K) attribute.defaultValue(), null);
    }

    public static <T, K> WrappedEnvironmentAttribute<T, K> of(EnvironmentAttribute<@NotNull T> attribute, @NotNull K value) {
        return new WrappedEnvironmentAttribute<>(attribute, value, null);
    }

    public static <T, K> WrappedEnvironmentAttribute<T, K> of(EnvironmentAttribute<@NotNull T> attribute, Converter<T, K> converter) {
        WrappedEnvironmentAttribute<T, K> wrapped = of(attribute);
        wrapped.converter = converter;
        return wrapped;
    }

    /**
     * Converts K to T.
     * @param <T>
     * @param <K>
     */
    public interface Converter<T, K> {
        T convert(K value);
    }
}
