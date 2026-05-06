package me.outspending.biomesapi.factory;

import me.outspending.biomesapi.annotations.AsOf;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * A WireProvider that returns null if the factory does not exist.
 * @param <F> the type of the factory
 * @since 2.1.0
 * @version 2.1.0
 * @author Jsinco
 */
public final class NullableWireProvider<F> extends WireProvider<F> {

    private final boolean reflectiveFallback;
    private boolean initialized;

    @AsOf("2.1.0")
    private NullableWireProvider(String classNameTemplate, boolean reflectiveFallback) {
        super(classNameTemplate);
        this.reflectiveFallback = reflectiveFallback;
    }

    /**
     * Creates a new NullableWireProvider that reflectively instantiates the named class
     * if no provider has been explicitly set.
     */
    @AsOf("2.1.0")
    public static <F> NullableWireProvider<F> create(String classNameTemplate) {
        return new NullableWireProvider<>(classNameTemplate, true);
    }

    /**
     * Creates a new NullableWireProvider that does not reflectively instantiate anything.
     * The provider is null until {@link #setProvider} is called explicitly. Useful when
     * the target class has side effects in its constructor that should only run after
     * the host plugin has explicitly wired itself up.
     */
    @AsOf("2.1.0")
    public static <F> NullableWireProvider<F> empty() {
        return new NullableWireProvider<>(null, false);
    }

    /**
     * Gets the factory instance.
     * @return the factory instance, or null if the factory does not exist
     * @since 2.1.0
     */
    @Override
    @AsOf("2.1.0")
    public @Nullable F get() {
        if (initialized) {
            return factory;
        }
        initialized = true;

        if (!reflectiveFallback) {
            return factory;  // remains null until setProvider is called
        }

        try {
            return super.get();
        } catch (IllegalStateException _) {
            return null;
        }
    }

    public Optional<F> optional() {
        return Optional.ofNullable(get());
    }
}