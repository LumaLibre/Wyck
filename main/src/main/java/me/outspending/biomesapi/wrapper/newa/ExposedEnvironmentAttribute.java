package me.outspending.biomesapi.wrapper.newa;

import net.minecraft.world.attribute.EnvironmentAttribute;
import org.jetbrains.annotations.NotNull;

public class ExposedEnvironmentAttribute<T> implements AbstractWrappedEnvironmentAttribute<T> {

    private final EnvironmentAttribute<@NotNull T> delegateAttribute;
    private T value;

    public ExposedEnvironmentAttribute(EnvironmentAttribute<@NotNull T> delegateAttribute, @NotNull T value) {
        this.delegateAttribute = delegateAttribute;
        this.value = value;
    }

    public ExposedEnvironmentAttribute(EnvironmentAttribute<@NotNull T> delegateAttribute) {
        this.delegateAttribute = delegateAttribute;
        this.value = delegateAttribute.defaultValue();
    }

    @Override
    public EnvironmentAttribute<@NotNull T> getAttribute() {
        return delegateAttribute;
    }

    @Override
    public @NotNull T getValue() {
        return value;
    }

    @Override
    public void setValue(@NotNull Object value) {
        this.value = (T) value;
    }

    public static <T> ExposedEnvironmentAttribute<T> of(EnvironmentAttribute<@NotNull T> attribute, @NotNull T value) {
        return new ExposedEnvironmentAttribute<>(attribute, value);
    }

    public static <T> ExposedEnvironmentAttribute<T> of(EnvironmentAttribute<@NotNull T> attribute) {
        return new ExposedEnvironmentAttribute<>(attribute);
    }
}
