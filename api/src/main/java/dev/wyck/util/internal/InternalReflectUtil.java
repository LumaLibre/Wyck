package dev.wyck.util.internal;

import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.Field;

/**
 * Internal class for quick reflective operations.
 * This class isn't considered public API and may
 * change at any time.
 */
@ApiStatus.Internal
@SuppressWarnings("unchecked")
public final class InternalReflectUtil {

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
}