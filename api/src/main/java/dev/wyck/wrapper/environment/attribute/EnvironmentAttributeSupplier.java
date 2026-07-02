package dev.wyck.wrapper.environment.attribute;

import dev.wyck.annotations.AsOf;
import org.jspecify.annotations.NullMarked;

import java.util.function.Supplier;

/**
 * A supplier for WrappedEnvironmentAttribute instances.
 * @param <T> the type of the underlying EnvironmentAttribute
 * @param <K> the type of the exposed 'api' value
 * @version 2.1.0
 * @since 1.1.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.1.0")
public class EnvironmentAttributeSupplier<T, K> {

    private final Supplier<EnvironmentAttribute<T, K>> supplier;

    /**
     * Creates a new WrappedEnvironmentAttributeSupplier with the given supplier.
     * @param supplier the supplier that provides the WrappedEnvironmentAttribute
     */
    @AsOf("2.1.0")
    public EnvironmentAttributeSupplier(Supplier<EnvironmentAttribute<T, K>> supplier) {
        this.supplier = supplier;
    }


    /**
     * Creates a new WrappedEnvironmentAttributeSupplier with the given WrappedEnvironmentAttribute.
     * @param wrappedEnvironmentAttribute the WrappedEnvironmentAttribute to supply
     */
    @AsOf("1.1.0")
    public EnvironmentAttributeSupplier(EnvironmentAttribute<T, K> wrappedEnvironmentAttribute) {
        this(() -> wrappedEnvironmentAttribute);
    }

    /**
     * Gets the WrappedEnvironmentAttribute from the supplier.
     * @return the WrappedEnvironmentAttribute
     */
    @AsOf("1.1.0")
    public EnvironmentAttribute<T, K> get() {
        return supplier.get();
    }

    /**
     * Unboxes the value into a WrappedEnvironmentAttribute.
     * @param value the value to unbox
     * @return the WrappedEnvironmentAttribute
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    public EnvironmentAttribute<T, K> unbox(K value) {
        EnvironmentAttribute<T, K> attribute = supplier.get();
        attribute.setValue(value);
        return attribute;
    }
}
