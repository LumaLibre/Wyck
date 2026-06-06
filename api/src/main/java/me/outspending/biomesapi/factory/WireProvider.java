package me.outspending.biomesapi.factory;

import me.outspending.biomesapi.annotations.AsOf;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Constructor;

/**
 * A provider for lazily loading factory instances based on the active Minecraft version.
 * @param <F> the type of the factory
 * @version 2.2.0
 * @since 2.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.2.0")
@ApiStatus.Internal
public sealed class WireProvider<F> permits NullableWireProvider {

    private static final String ALLOWED_PACKAGE = "me.outspending.biomesapi";

    private final String classNameTemplate;
    protected volatile @Nullable F factory;

    /** Cached constructor for {@link #getNew()}. Resolved once, reused thereafter. */
    private volatile @Nullable Constructor<? extends F> cachedCtor;

    protected WireProvider(String classNameTemplate) {
        this.classNameTemplate = classNameTemplate;
    }

    /**
     * Creates a new WireProvider instance.
     * @param classNameTemplate the class name template to use for loading the factory
     * @return a new WireProvider instance
     * @param <F> the type of the factory
     */
    @AsOf("2.0.0")
    public static <F> WireProvider<F> create(String classNameTemplate) {
        return new WireProvider<F>(classNameTemplate);
    }

    /**
     * Checks if the factory has been registered.
     * @return true if the factory has been registered, false otherwise
     */
    @AsOf("2.0.0")
    public boolean isRegistered() {
        return factory != null;
    }

    /**
     * Gets the factory instance.
     * @return the factory instance
     */
    @AsOf("2.0.0")
    public F get() {
        F f = factory;
        if (f != null) return f;

        synchronized (this) {
            if (factory != null) return factory;
            factory = newInstance();
            return factory;
        }
    }

    /**
     * Forces a new factory instance, bypassing the cached singleton. The
     * resolved class and constructor are cached internally, so only the
     * instantiation cost is paid per call.
     * @return a fresh factory instance
     */
    @AsOf("2.2.0")
    public F getNew() {
        return newInstance();
    }

    @AsOf("2.2.0")
    @SuppressWarnings("unchecked")
    private F newInstance() {
        Constructor<? extends F> ctor = cachedCtor;
        if (ctor == null) {
            synchronized (this) {
                ctor = cachedCtor;
                if (ctor == null) {
                    String resolved = classNameTemplate.replace("*", Versions.ACTIVE.id());
                    try {
                        Class<? extends F> cls = (Class<? extends F>) Class.forName(resolved);
                        ctor = cls.getDeclaredConstructor();
                        ctor.setAccessible(true);
                        cachedCtor = ctor;
                    } catch (ReflectiveOperationException e) {
                        throw new IllegalStateException("Failed to load wire factory: " + resolved, e);
                    }
                }
            }
        }
        try {
            return ctor.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Failed to instantiate wire factory", e);
        }
    }

    public void setProvider(Class<?> caller, F factory) {
        if (!caller.getPackage().getName().startsWith(ALLOWED_PACKAGE)) {
            throw new IllegalStateException("Cannot set provider outside of '" + ALLOWED_PACKAGE + "'");
        }
        this.factory = factory;
    }
}