package me.outspending.biomesapi.registry;

import me.outspending.biomesapi.annotations.AsOf;
import net.minecraft.core.MappedRegistry;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.lang.reflect.Field;

/**
 * Utility class to unfreeze and re-freeze built-in registries.
 * These are different from other synchronized registries and are not re-frozen the same way.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
@ApiStatus.Internal
public final class BuiltInRegistryLocks {

    private static final Field FROZEN_FIELD;

    static {
        try {
            FROZEN_FIELD = MappedRegistry.class.getDeclaredField("frozen");
            FROZEN_FIELD.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private BuiltInRegistryLocks() {
    }

    public static boolean isFrozen(MappedRegistry<?> registry) {
        try {
            return FROZEN_FIELD.getBoolean(registry);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to check frozen state on registry " + registry.key(), e);
        }
    }

    @AsOf("2.3.0")
    public static void setFrozen(MappedRegistry<?> registry, boolean frozen) {
        try {
            FROZEN_FIELD.setBoolean(registry, frozen);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to set frozen state on registry " + registry.key(), e);
        }
    }

    @AsOf("2.3.0")
    public static void whileUnfrozen(MappedRegistry<?> registry, Runnable action) {
        if (!isFrozen(registry)) { // assume bootstrapping
            action.run();
        } else {
            setFrozen(registry, false);
            try {
                action.run();
            } finally {
                registry.freeze();
            }
        }
    }
}