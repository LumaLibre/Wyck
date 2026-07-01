package me.outspending.biomesapi.factory;

import me.outspending.biomesapi.annotations.AsOf;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Constructor;

/**
 * A provider for lazily loading factory instances based on the active Minecraft version.
 * @param <F> the type of the factory
 * @version 2.3.0
 * @since 2.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.2.0")
@ApiStatus.Internal
public sealed class WireProvider<F> permits NullableWireProvider, ConstructWireProvider {

    private static final String ALLOWED_PACKAGE = "me.outspending.biomesapi";
    private static final String VERSION_EXPECTED = "*";
    private static final String VERSION_AVAILABLE = "*?";

    private final String classNameTemplate;
    protected volatile @Nullable F factory;

    /** Cached constructor for {@link #getNew()}. Resolved once, reused thereafter. */
    private volatile @Nullable Constructor<? extends F> cachedCtor;

    protected WireProvider(String classNameTemplate) {
        this.classNameTemplate = classNameTemplate;
    }

    /**
     * Creates a new WireProvider instance.
     * @param template the class name template to use for loading the factory
     * @return a new WireProvider instance
     * @param <F> the type of the factory
     * @since 2.0.0
     */
    @AsOf("2.0.0")
    public static <F> WireProvider<F> create(String template) {
        return new WireProvider<>(template);
    }

    /**
     * Creates a WireProvider that does not reflectively instantiate anything.
     * @return a WireProvider that is empty
     * @param <F> the type of the factory
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    public static <F> NullableWireProvider<F> empty() {
        return NullableWireProvider.empty();
    }

    /**
     * Creates a WireProvider that uses the given template to construct the factory.
     * @param template the class name template to use for loading the factory
     * @return a WireProvider that constructs the factory using the given template
     * @param <F> the type of the factory
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    public static <F> ConstructWireProvider<F> construct(String template) {
        return ConstructWireProvider.create(template);
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
    @SuppressWarnings("DataFlowIssue")
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
    private F newInstance() {
        Constructor<? extends F> ctor = cachedCtor;
        if (ctor == null) {
            synchronized (this) {
                ctor = cachedCtor;
                if (ctor == null) {
                    try {
                        Class<? extends F> cls = resolveClass();
                        ctor = cls.getDeclaredConstructor();
                        ctor.setAccessible(true);
                        cachedCtor = ctor;
                    } catch (NoSuchMethodException e) {
                        throw new IllegalStateException("Failed to load wire factory constructor for: " + classNameTemplate, e);
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

    /**
     * Resolves the backing factory class from {@link #classNameTemplate}, applying
     * whichever version operator the template uses.
     * @return the resolved factory class
     */
    @AsOf("2.3.0")
    @SuppressWarnings("unchecked")
    protected Class<? extends F> resolveClass() {
        if (classNameTemplate.contains(VERSION_AVAILABLE)) {
            String versioned = classNameTemplate.replace(VERSION_AVAILABLE, Versions.ACTIVE.id());
            try {
                return (Class<? extends F>) Class.forName(versioned);
            } catch (ClassNotFoundException ignored) {
                String fallback = classNameTemplate.replace(VERSION_AVAILABLE + ".", "");
                try {
                    return (Class<? extends F>) Class.forName(fallback);
                } catch (ClassNotFoundException e) {
                    throw new IllegalStateException(
                        "Failed to load wire factory: tried '" + versioned + "' then '" + fallback + "'", e);
                }
            }
        }

        String resolved = classNameTemplate.replace(VERSION_EXPECTED, Versions.ACTIVE.id());
        try {
            return (Class<? extends F>) Class.forName(resolved);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Failed to load wire factory: " + resolved, e);
        }
    }

    public void setProvider(Class<?> caller, F factory) {
        if (!caller.getPackage().getName().startsWith(ALLOWED_PACKAGE)) {
            throw new IllegalStateException("Cannot set provider outside of '" + ALLOWED_PACKAGE + "'");
        }
        this.factory = factory;
    }
}