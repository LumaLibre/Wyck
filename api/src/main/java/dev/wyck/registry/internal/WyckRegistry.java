package dev.wyck.registry.internal;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.WireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.util.Lazy;
import dev.wyck.wrapper.internal.Wrapper;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * A minimal abstraction of Minecraft's registry with frozen registry utilities.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
@ApiStatus.Internal
public interface WyckRegistry extends Wrapper {

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("dev.wyck.registry.internal.WyckRegistryFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        WyckRegistry create(ResourceKey key);

        WyckRegistry create(Collection<ResourceKey> keys);
    }

    /**
     * The key of the registry.
     * @return the key of the registry
     */
    @AsOf("2.3.0")
    ResourceKey key();

    /**
     * The registry itself.
     * @return the registry itself
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    Object registry();

    /**
     * Whether the registry is frozen.
     * @return whether the registry is frozen
     */
    @AsOf("2.3.0")
    boolean isFrozen();

    /**
     * Unfreezes the registry.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    void unFreeze();

    /**
     * Unbinds all tags and freezes the registry.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    void freeze();

    /**
     * Runs the given action while the registry is unfrozen.
     * @param action the action to run
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    void whileUnfrozen(Runnable action);

    /**
     * Retrieves an object from the registry.
     * @param key the key of the object to retrieve
     * @return the object
     * @param <T> the type of the object
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    <T> @Nullable T retrieve(ResourceKey key);

    /**
     * Runs the given consumer with the registry while it is unfrozen.
     * @param consumer the consumer to run
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default void whileUnfrozen(Consumer<WyckRegistry> consumer) {
        whileUnfrozen(() -> consumer.accept(this));
    }

    /**
     * Returns the registry itself.
     * @return the registry itself
     * @since 2.3.0
     */
    @Override
    @AsOf("2.3.0")
    default Object toMinecraft() {
        return this.registry();
    }

    /**
     * Creates a FrozenRegistry from a ResourceKey.
     * @param key the key of the registry
     * @return a FrozenRegistry instance
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static WyckRegistry of(ResourceKey key) {
        return WIRE.get().create(key);
    }

    /**
     * Creates a FrozenRegistry from a Minecraft registry path.
     * @param path the path of the registry
     * @return a FrozenRegistry instance
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static WyckRegistry of(String path) {
        return of(ResourceKey.minecraft(path));
    }

    /**
     * Creates a FrozenRegistry from a RegistryReference.
     * @param reference the registry reference
     * @return a FrozenRegistry instance
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static WyckRegistry of(RegistryId reference) {
        return WIRE.get().create(reference.keys());
    }

    /**
     * Creates a lazy FrozenRegistry.
     * @param key the key of the registry
     * @return a lazy FrozenRegistry instance
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static Lazy<WyckRegistry> lazy(ResourceKey key) {
        return Lazy.of(() -> of(key));
    }

    /**
     * Creates a lazy FrozenRegistry.
     * @param path the path of the registry
     * @return a lazy FrozenRegistry instance
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static Lazy<WyckRegistry> lazy(String path) {
        return Lazy.of(() -> of(path));
    }

    /**
     * Creates a lazy FrozenRegistry.
     * @param reference the registry reference
     * @return a lazy FrozenRegistry instance
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Lazy<WyckRegistry> lazy(RegistryId reference) {
        return Lazy.of(() -> of(reference));
    }
}
