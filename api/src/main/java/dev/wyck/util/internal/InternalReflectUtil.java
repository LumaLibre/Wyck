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

    public static <T> T getFieldValue(Object instance, String fieldName) {
        Class<?> current = instance.getClass();
        while (current != null) {
            try {
                Field field = current.getDeclaredField(fieldName);
                field.setAccessible(true);
                return (T) field.get(instance);
            } catch (NoSuchFieldException e) {
                current = current.getSuperclass();
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("no field '" + fieldName + "' on " + instance.getClass());
    }
}
