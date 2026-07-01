package me.outspending.biomesapi.registry.internal;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.util.Lazy;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * Frozen utilities for Minecraft registries.
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
@ApiStatus.Internal
public interface FrozenRegistry extends NmsHandle {

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.registry.internal.FrozenRegistryFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        FrozenRegistry create(ResourceKey key);

        FrozenRegistry create(Collection<ResourceKey> keys);
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
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    <T> @Nullable T retrieve(ResourceKey key);

    /**
     * Runs the given consumer with the registry while it is unfrozen.
     * @param consumer the consumer to run
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default void whileUnfrozen(Consumer<FrozenRegistry> consumer) {
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
        return registry();
    }

    /**
     * Creates a FrozenRegistry from a ResourceKey.
     * @param key the key of the registry
     * @return a FrozenRegistry instance
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static FrozenRegistry of(ResourceKey key) {
        return WIRE.get().create(key);
    }

    /**
     * Creates a FrozenRegistry from a Minecraft registry path.
     * @param path the path of the registry
     * @return a FrozenRegistry instance
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static FrozenRegistry of(String path) {
        return of(ResourceKey.minecraft(path));
    }

    /**
     * Creates a FrozenRegistry from a RegistryReference.
     * @param reference the registry reference
     * @return a FrozenRegistry instance
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static FrozenRegistry of(Referer reference) {
        return WIRE.get().create(reference.getRegistryKeys());
    }

    /**
     * Creates a lazy FrozenRegistry.
     * @param key the key of the registry
     * @return a lazy FrozenRegistry instance
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static Lazy<FrozenRegistry> lazy(ResourceKey key) {
        return Lazy.of(() -> of(key));
    }

    /**
     * Creates a lazy FrozenRegistry.
     * @param path the path of the registry
     * @return a lazy FrozenRegistry instance
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static Lazy<FrozenRegistry> lazy(String path) {
        return Lazy.of(() -> of(path));
    }

    /**
     * Creates a lazy FrozenRegistry.
     * @param reference the registry reference
     * @return a lazy FrozenRegistry instance
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Lazy<FrozenRegistry> lazy(Referer reference) {
        return Lazy.of(() -> of(reference));
    }
}
