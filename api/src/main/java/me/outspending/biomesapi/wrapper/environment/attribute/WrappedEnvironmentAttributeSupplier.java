package me.outspending.biomesapi.wrapper.environment.attribute;

import me.outspending.biomesapi.annotations.AsOf;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Supplier;

/**
 * A supplier for WrappedEnvironmentAttribute instances.
 * @param supplier the supplier that provides the WrappedEnvironmentAttribute
 * @param <T> the type of the underlying EnvironmentAttribute
 * @param <K> the type of the exposed 'api' value
 * @version 1.1.0
 * @since 1.1.0
 * @author Jsinco
 */
@AsOf("1.1.0")
@ApiStatus.Experimental
public record WrappedEnvironmentAttributeSupplier<T, K>(Supplier<WrappedEnvironmentAttribute<T, K>> supplier) {

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
}
