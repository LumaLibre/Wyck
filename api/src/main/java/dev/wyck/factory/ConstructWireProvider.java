package dev.wyck.factory;

import dev.wyck.annotations.AsOf;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A {@link WireProvider} that can instantiate the version-resolved class directly
 * through a matching constructor, without an intermediate factory.
 * @param <F> the type of the wired class
 * @version 2.4.0
 * @since 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.1")
@ApiStatus.Internal
public final class ConstructWireProvider<F> extends WireProvider<F> {

    private final Map<Integer, Constructor<? extends F>> cachedCtors = new ConcurrentHashMap<>();

    private ConstructWireProvider(String classNameTemplate) {
        super(classNameTemplate);
    }

    /**
     * Creates a new ConstructingWireProvider instance.
     * @param classNameTemplate the class name template to use for loading the class
     * @return a new ConstructingWireProvider instance
     * @param <F> the type of the wired class
     */
    @AsOf("2.4.1")
    public static <F> ConstructWireProvider<F> create(String classNameTemplate) {
        return new ConstructWireProvider<>(classNameTemplate);
    }

    /**
     * Instantiates the version-resolved class directly. The constructor whose parameters
     * match the supplied arguments is resolved once per argument count and cached thereafter.
     * Primitive parameters accept their boxed counterparts, and {@code null} arguments match
     * any non-primitive parameter.
     * @param args the constructor arguments
     * @return a fresh instance of the resolved class
     */
    @AsOf("2.4.1")
    public F construct(@Nullable Object... args) {
        Constructor<? extends F> ctor = cachedCtors.computeIfAbsent(args.length, n -> findCtor(args));
        try {
            return ctor.newInstance(args);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Failed to instantiate wire impl", e);
        }
    }

    @AsOf("2.4.1")
    @SuppressWarnings("unchecked")
    private Constructor<? extends F> findCtor(@Nullable Object[] args) {
        Class<? extends F> cls = resolveClass();
        Constructor<?> match = null;
        for (Constructor<?> ctor : cls.getDeclaredConstructors()) {
            if (!matches(ctor.getParameterTypes(), args)) continue;
            if (match != null) {
                throw new IllegalStateException("Ambiguous constructor for " + cls.getName() + " with " + args.length + " args");
            }
            match = ctor;
        }
        if (match == null) {
            throw new IllegalStateException("No matching constructor for " + cls.getName() + " with " + args.length + " args");
        }
        match.setAccessible(true);
        return (Constructor<? extends F>) match;
    }

    private static boolean matches(Class<?>[] params, @Nullable Object[] args) {
        if (params.length != args.length) return false;
        for (int i = 0; i < params.length; i++) {
            Object arg = args[i];
            Class<?> param = params[i];
            if (arg == null) {
                if (param.isPrimitive()) return false;
            } else if (!wrap(param).isInstance(arg)) {
                return false;
            }
        }
        return true;
    }

    private static Class<?> wrap(Class<?> type) {
        if (!type.isPrimitive()) return type;
        if (type == int.class) return Integer.class;
        if (type == long.class) return Long.class;
        if (type == double.class) return Double.class;
        if (type == float.class) return Float.class;
        if (type == boolean.class) return Boolean.class;
        if (type == byte.class) return Byte.class;
        if (type == short.class) return Short.class;
        if (type == char.class) return Character.class;
        return Void.class;
    }
}