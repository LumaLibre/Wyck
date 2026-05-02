package me.outspending.biomesapi.factory;

import me.outspending.biomesapi.annotations.AsOf;
import org.jetbrains.annotations.ApiStatus;

/**
 * A provider for lazily loading factory instances based on the active Minecraft version.
 * @param <F> the type of the factory
 * @version 2.0.0
 * @since 2.0.0
 * @author Jsinco
 */
@AsOf("2.0.0")
@ApiStatus.Internal
public final class WireProvider<F> {

    private final String classNameTemplate;
    private volatile F factory;

    private WireProvider(String classNameTemplate) {
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
        return new WireProvider<>(classNameTemplate);
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
    @SuppressWarnings("unchecked")
    public F get() {
        F f = factory;
        if (f != null) return f;

        synchronized (this) {
            if (factory != null) return factory;
            String resolved = classNameTemplate.replace("*", Versions.ACTIVE.id());
            try {
                Class<?> cls = Class.forName(resolved);
                factory = (F) cls.getDeclaredConstructor().newInstance();
                return factory;
            } catch (ReflectiveOperationException e) {
                throw new IllegalStateException("Failed to load wire factory: " + resolved, e);
            }
        }
    }
}