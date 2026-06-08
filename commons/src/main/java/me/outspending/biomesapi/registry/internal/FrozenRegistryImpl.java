package me.outspending.biomesapi.registry.internal;

import com.google.common.base.Preconditions;
import io.papermc.paper.registry.RegistryKey;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.registry.bootstrap.util.BootstrapSafeMinecraftRegistries;
import me.outspending.biomesapi.util.ThrowingRunnable;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import org.jspecify.annotations.NullMarked;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

@NullMarked
public class FrozenRegistryImpl<U> implements FrozenRegistry {

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

    private final ResourceKey key;
    private final Registry<U> registry;

    public FrozenRegistryImpl(ResourceKey key) {
        this.key = key;

        Registry<U> resolved = getRegistry(key);
        this.registry = Preconditions.checkNotNull(resolved, "Failed to resolve registry for " + key);
    }

    @Override
    public ResourceKey key() {
        return this.key;
    }

    @Override
    public Object registry() {
        return this.registry;
    }

    @Override
    public boolean isFrozen() {
        try {
            return FROZEN_FIELD.getBoolean(registry);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to check frozen state on registry " + registry.key(), e);
        }
    }

    @Override
    public void unFreeze() {
        unsafe(() -> FROZEN_FIELD.setBoolean(registry, false), "Failed to set frozen state on registry " + registry.key());
    }

    @Override
    public void freeze() {
        unsafe(() -> {
            Object unbound = UNBOUND_TAGSET.invoke(null);
            ALL_TAGS_FIELD.set(registry, unbound);
            registry.freeze();
        }, "Failed to set frozen state on registry " + registry.key());
    }

    @Override
    public void whileUnfrozen(Runnable action) {
        if (!isFrozen()) { // assume bootstrapping, vanilla will freeze later
            action.run();
            return;
        }
        unFreeze();
        try {
            action.run();
        } finally {
            freeze();
        }
    }

    private static void unsafe(ThrowingRunnable runnable, String err) {
        try {
            runnable.run();
        } catch (Throwable throwable) {
            throw new RuntimeException(err, throwable);
        }
    }

    private static <U> Registry<U> getRegistry(ResourceKey key) {
        String id = key.asString();
        return BootstrapSafeMinecraftRegistries.mappedRegistry(id);
    }

    private static Map<String, RegistryKey<?>> indexPaperRegistryKeys() {
        Map<String, RegistryKey<?>> index = new HashMap<>();
        for (Field field : RegistryKey.class.getFields()) {
            int modifiers = field.getModifiers();
            if (!Modifier.isStatic(modifiers) || !RegistryKey.class.isAssignableFrom(field.getType())) {
                continue;
            }
            try {
                RegistryKey<?> registryKey = (RegistryKey<?>) field.get(null);
                index.put(registryKey.key().asString(), registryKey);
            } catch (IllegalAccessException exception) {
                throw new IllegalStateException("Unable to read RegistryKey." + field.getName(), exception);
            }
        }
        return Map.copyOf(index);
    }

}