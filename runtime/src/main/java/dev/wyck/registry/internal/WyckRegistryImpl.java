package dev.wyck.registry.internal;

import com.google.common.base.Preconditions;
import dev.wyck.keys.ResourceKey;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.util.ThrowingRunnable;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;

@NullMarked
public class WyckRegistryImpl<U> implements WyckRegistry {

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

    public WyckRegistryImpl(ResourceKey key) {
        this.key = key;

        Registry<U> resolved = getRegistry(key);
        this.registry = Preconditions.checkNotNull(resolved, "Failed to resolve registry for " + key);
    }

    public WyckRegistryImpl(Collection<ResourceKey> keys) {
        ResourceKey resolvedKey = null;
        Registry<U> resolvedRegistry = null;

        for (ResourceKey key : keys) {
            Registry<U> candidate = getRegistryOrNull(key);
            if (candidate != null) {
                resolvedKey = key;
                resolvedRegistry = candidate;
                break;
            }
        }

        this.key = Preconditions.checkNotNull(resolvedKey, "Failed to resolve any registry from " + keys);
        this.registry = Preconditions.checkNotNull(resolvedRegistry, "resolvedRegistry was null");
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

    @Override
    @SuppressWarnings("unchecked")
    public <T> @Nullable T retrieve(ResourceKey key) {
        return (T) registry.getValue((Identifier) key.identifier());
    }

    public <T> void register(ResourceKey key, T object) {
        Identifier id = key.identifier();
        Registry<T> registry = (Registry<T>) this.registry;
        this.whileUnfrozen(() -> {
            if (!registry.containsKey(id)) {
                if (object instanceof Holder<?> holder) {
                    Registry.register(registry, id, (T) holder.value());
                } else {
                    Registry.register(registry, id, object);
                }
            }
        });
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

    private static <U> @Nullable Registry<U> getRegistryOrNull(ResourceKey key) {
        String id = key.asString();
        return BootstrapSafeMinecraftRegistries.mappedRegistryOrNull(id);
    }
}