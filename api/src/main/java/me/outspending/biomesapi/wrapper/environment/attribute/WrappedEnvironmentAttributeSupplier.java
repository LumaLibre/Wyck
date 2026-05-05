package me.outspending.biomesapi.wrapper.environment.attribute;

import me.outspending.biomesapi.annotations.AsOf;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * A supplier for WrappedEnvironmentAttribute instances.
 * @param <T> the type of the underlying EnvironmentAttribute
 * @param <K> the type of the exposed 'api' value
 * @version 2.1.0
 * @since 1.1.0
 * @author Jsinco
 */
@AsOf("2.1.0")
public class WrappedEnvironmentAttributeSupplier<T, K> {

    private final Supplier<WrappedEnvironmentAttribute<T, K>> supplier;

    /**
     * Creates a new WrappedEnvironmentAttributeSupplier with the given supplier.
     * @param supplier the supplier that provides the WrappedEnvironmentAttribute
     */
    @AsOf("2.1.0")
    public WrappedEnvironmentAttributeSupplier(Supplier<WrappedEnvironmentAttribute<T, K>> supplier) {
        this.supplier = supplier;
    }


    /**
     * Creates a new WrappedEnvironmentAttributeSupplier with the given WrappedEnvironmentAttribute.
     * @param wrappedEnvironmentAttribute the WrappedEnvironmentAttribute to supply
     */
    @AsOf("1.1.0")
    public WrappedEnvironmentAttributeSupplier(WrappedEnvironmentAttribute<T, K> wrappedEnvironmentAttribute) {
        this(() -> wrappedEnvironmentAttribute);
    }

    /**
     * Gets the WrappedEnvironmentAttribute from the supplier.
     * @return the WrappedEnvironmentAttribute
     */
    @AsOf("1.1.0")
    public WrappedEnvironmentAttribute<T, K> get() {
        return supplier.get();
    }

    /**
     * Unboxes the value into a WrappedEnvironmentAttribute.
     * @param value the value to unbox
     * @return the WrappedEnvironmentAttribute
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    public WrappedEnvironmentAttribute<T, K> unbox(@NotNull K value) {
        WrappedEnvironmentAttribute<T, K> attribute = supplier.get();
        attribute.setValue(value);
        return attribute;
    }
}
