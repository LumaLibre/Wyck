package dev.wyck.util.internal;

import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Internal class for quick reflective operations.
 * This class isn't considered public API and may
 * change at any time.
 */
@ApiStatus.Internal
@SuppressWarnings("unchecked")
public final class InternalReflectUtil {

    private static final ClassValue<Field[]> INSTANCE_FIELDS = new ClassValue<>() {
        @Override
        protected Field[] computeValue(Class<?> type) {
            List<Field> fields = new ArrayList<>();
            for (Class<?> current = type; current != null && current != Object.class; current = current.getSuperclass()) {
                for (Field field : current.getDeclaredFields()) {
                    if (Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }
                    field.setAccessible(true);
                    fields.add(field);
                }
            }
            return fields.toArray(Field[]::new);
        }
    };

    private InternalReflectUtil() {}

    public static Field field(Class<?> owner, String fieldName) {
        Class<?> current = owner;
        while (current != null) {
            try {
                Field field = current.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
                current = current.getSuperclass();
            }
        }
        throw new RuntimeException("no field '" + fieldName + "' on " + owner);
    }

    public static <T> T get(Field field, Object instance) {
        try {
            return (T) field.get(instance);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("failed to read field '" + field.getName() + "'", e);
        }
    }

    public static void set(Field field, Object instance, Object value) {
        try {
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("failed to set field '" + field.getName() + "'", e);
        }
    }

    public static <T> T getFieldValue(Object instance, String fieldName) {
        return get(field(instance.getClass(), fieldName), instance);
    }

    public static void setFieldValue(Object instance, String fieldName, Object value) {
        set(field(instance.getClass(), fieldName), instance, value);
    }

    /**
     * Creates a new instance of {@code instance}'s class without running a constructor and copies
     * every instance field across, inherited ones included. Referenced objects are shared rather
     * than copied, so only the outer object is new.
     *
     * <p>For varying an object per use when it has no constructor that takes its own fields -- such
     * as a packet the server is sending to several players at once, where rewriting the original
     * would leak one player's view to all of them.
     *
     * @param instance the object to copy
     * @return a new object whose fields hold the same values
     */
    public static <T> T shallowCopy(T instance) {
        Class<?> type = instance.getClass();
        try {
            T copy = (T) UnsafeHolder.UNSAFE.allocateInstance(type);
            for (Field field : INSTANCE_FIELDS.get(type)) {
                field.set(copy, field.get(instance));
            }
            return copy;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("failed to copy " + type.getName(), e);
        }
    }

    @SuppressWarnings("removal")
    private static final class UnsafeHolder {

        private static final sun.misc.Unsafe UNSAFE = load();

        private static sun.misc.Unsafe load() {
            try {
                Field field = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
                field.setAccessible(true);
                return (sun.misc.Unsafe) field.get(null);
            } catch (ReflectiveOperationException e) {
                throw new ExceptionInInitializerError(e);
            }
        }
    }
}
