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
public final class Lazy<T> implements Supplier<@Nullable T> {
    private @Nullable Supplier<T> supplier;
    private @Nullable T value;

    private Lazy(@Nullable Supplier<@Nullable T> supplier) {
        this.supplier = supplier;
    }

    @Override
    @AsOf("2.3.0")
    public T get() {
        if (this.supplier != null) {
            this.value = supplier.get();
            this.supplier = null;
        }
        return value;
    }

    @AsOf("2.3.0")
    public static <T> Lazy<T> of(Supplier<@Nullable T> supplier) {
        Preconditions.checkNotNull(supplier, "supplier");
        return new Lazy<>(supplier);
    }
}