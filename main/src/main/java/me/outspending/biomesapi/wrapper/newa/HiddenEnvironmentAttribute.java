package me.outspending.biomesapi.wrapper.newa;

import com.google.common.base.Preconditions;
import net.minecraft.world.attribute.EnvironmentAttribute;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HiddenEnvironmentAttribute<T, K> implements AbstractWrappedEnvironmentAttribute<T> {

    private final EnvironmentAttribute<T> exposedAttribute;
    private final Converter<T, K> converter;
    private K exposedValue;


    public HiddenEnvironmentAttribute(EnvironmentAttribute<@NotNull T> exposedAttribute, @NotNull Converter<T, K> converter, @Nullable K exposedValue) {
        this.exposedAttribute = exposedAttribute;
        this.converter = converter;
        this.exposedValue = exposedValue;
    }


    public EnvironmentAttribute<@NotNull T> getAttribute() {
        return exposedAttribute;
    }

    @Override
    public @NotNull T getValue() {
        return getConvertedValue();
    }

    @Override
    public void setValue(@NotNull Object value) {
        this.exposedValue = (K) value;
    }

    public K getExposedValue() {
        return exposedValue;
    }

    public void setExposedValue(@NotNull K exposedValue) {
        this.exposedValue = exposedValue;
    }


    public T getConvertedValue() {
        Preconditions.checkNotNull(exposedValue, "exposedValue must be set before conversion");
        Preconditions.checkNotNull(converter, "converter cannot be null");
        return converter.convert(exposedValue);
    }


    public static <T, K> HiddenEnvironmentAttribute<T, K> of(EnvironmentAttribute<@NotNull T> attribute, @NotNull Converter<T, K> converter, @NotNull K exposedValue) {
        return new HiddenEnvironmentAttribute<>(attribute, converter, exposedValue);
    }


    /**
     * Converts K to T.
     * @param <T> The type of the EnvironmentAttribute.
     * @param <K> The type of the exposed value.
     */
    public interface Converter<T, K> {
        T convert(K value);
    }
}
