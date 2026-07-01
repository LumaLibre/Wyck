package me.outspending.biomesapi.util.internal;

import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.Field;

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

    public static <T> T invokeMethod(Object instance, String methodName, Object... args) {
        try {
            return (T) instance.getClass().getMethod(methodName, args.getClass()).invoke(instance, args);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
