package me.outspending.biomesapi.util;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.annotations.AsOf;
import org.jspecify.annotations.Nullable;

import java.util.function.Supplier;

/**
 * A lazy value that is only computed once.
 *
 * @param <T> the type of the value
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@AsOf("2.3.0")
public final class Lazy<T> implements Supplier<T> {
    private @Nullable Supplier<T> supplier;
    private @Nullable T value;

    private Lazy(@Nullable Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public T get() {
        if (supplier != null) {
            value = supplier.get();
            supplier = null;
        }
        return value;
    }

    public static <T> Lazy<T> of(Supplier<T> supplier) {
        return new Lazy<>(supplier);
    }
}