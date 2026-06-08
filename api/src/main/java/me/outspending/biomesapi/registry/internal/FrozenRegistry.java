package me.outspending.biomesapi.registry.internal;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.util.Lazy;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

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
}
