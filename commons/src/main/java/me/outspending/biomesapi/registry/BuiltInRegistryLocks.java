package me.outspending.biomesapi.registry;

import me.outspending.biomesapi.annotations.AsOf;
import net.minecraft.core.MappedRegistry;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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
    // TODO: Duplicated code from UnsafeNMS

    private static final Field FROZEN_FIELD;
    private static final Field ALL_TAGS_FIELD;
    private static final Method UNBOUND_TAGSET;

    static {
        try {
            FROZEN_FIELD = MappedRegistry.class.getDeclaredField("frozen");
            FROZEN_FIELD.setAccessible(true);

            ALL_TAGS_FIELD = MappedRegistry.class.getDeclaredField("allTags");
            ALL_TAGS_FIELD.setAccessible(true);

            Class<?> tagSetClass = Class.forName("net.minecraft.core.MappedRegistry$TagSet");
            UNBOUND_TAGSET = tagSetClass.getDeclaredMethod("unbound");
            UNBOUND_TAGSET.setAccessible(true);
        } catch (NoSuchFieldException | ClassNotFoundException | NoSuchMethodException e) {
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

    private static void unbindTags(MappedRegistry<?> registry) {
        try {
            Object unbound = UNBOUND_TAGSET.invoke(null);
            ALL_TAGS_FIELD.set(registry, unbound);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to reset tags on registry " + registry.key(), e);
        }
    }

    @AsOf("2.3.0")
    public static void whileUnfrozen(MappedRegistry<?> registry, Runnable action) {
        if (!isFrozen(registry)) { // assume bootstrapping, vanilla will freeze later
            action.run();
        } else {
            setFrozen(registry, false);
            try {
                action.run();
            } finally {
                unbindTags(registry);
                registry.freeze();
            }
        }
    }
}