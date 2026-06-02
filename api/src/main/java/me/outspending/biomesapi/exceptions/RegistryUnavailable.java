package me.outspending.biomesapi.exceptions;

import me.outspending.biomesapi.annotations.AsOf;

import java.util.function.Supplier;

@AsOf("2.3.0")
public class RegistryUnavailable extends RuntimeException {
    @AsOf("2.3.0")
    public RegistryUnavailable(String message) {
        super(message);
    }

    @AsOf("2.3.0")
    public static @SafeVarargs <T> T attempt(Supplier<T> supplier, String message, Class<? extends Throwable>... exceptions) {
        try {
            return supplier.get();
        } catch (Throwable e) {
            for (Class<? extends Throwable> exception : exceptions) {
                if (exception.isInstance(e)) {
                    throw new RegistryUnavailable(message);
                }
            }
            throw e;
        }
    }
}
