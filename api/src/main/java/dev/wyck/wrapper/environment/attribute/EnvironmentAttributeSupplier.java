package dev.wyck.wrapper.environment.attribute;

import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NullMarked;

import java.util.function.Supplier;

/**
 * A supplier for WrappedEnvironmentAttribute instances.
 * @param <V> the type of the attribute value
 * @version 2.1.0
 * @since 1.1.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.1.0")
public sealed class EnvironmentAttributeSupplier<V> implements Keyed permits FriendlyColorSupplier {

    private final Supplier<EnvironmentAttribute<V>> supplier;
    private final ResourceKey key;

    /**
     * Creates a new WrappedEnvironmentAttributeSupplier with the given supplier.
     * @param supplier the supplier that provides the WrappedEnvironmentAttribute
     */
    @AsOf("2.1.0")
    private EnvironmentAttributeSupplier(Supplier<EnvironmentAttribute<V>> supplier, ResourceKey key) {
        this.supplier = supplier;
        this.key = key;
    }

    /**
     * Creates a new WrappedEnvironmentAttributeSupplier with the given WrappedEnvironmentAttribute.
     * @param environmentAttribute the WrappedEnvironmentAttribute to supply
     */
    @AsOf("1.1.0")
    public EnvironmentAttributeSupplier(EnvironmentAttribute<V> environmentAttribute) {
        this(() -> environmentAttribute, environmentAttribute.key());
    }

    /**
     * Gets the WrappedEnvironmentAttribute from the supplier.
     * @return the WrappedEnvironmentAttribute
     */
    @AsOf("1.1.0")
    public EnvironmentAttribute<V> get() {
        return supplier.get();
    }

    /**
     * Unboxes the value into a WrappedEnvironmentAttribute.
     * @param value the value to unbox
     * @return the WrappedEnvironmentAttribute
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    public EnvironmentAttribute<V> unbox(V value) {
        EnvironmentAttribute<V> attribute = supplier.get();
        attribute.value(value);
        return attribute;
    }

    /**
     * Gets the key of the attribute.
     * @return the key of the attribute
     * @since 3.0.0
     */
    @Override
    @AsOf("3.0.0")
    public ResourceKey key() {
        return this.key;
    }
}
