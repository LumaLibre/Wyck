package me.outspending.biomesapi.factory;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.annotations.AsOf;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

import java.util.function.Supplier;

@AsOf("2.3.0")
@ApiStatus.Internal
public sealed class Lazy<T> implements Supplier<T> permits Lazy.Memorized {
    private @Nullable Supplier<T> supplier;
    private @Nullable T value;

    public static <T> Lazy<T> of(Supplier<T> supplier) {
        return new Lazy<>(supplier);
    }

    public static <T> Memorized<T> memorized(Supplier<T> supplier) {
        return new Memorized<>(supplier);
    }

    private Lazy(@Nullable Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public T get() {
        if (supplier != null) {
            value = supplier.get();
            supplier = null;  // release the closure so it can be GC'd
        }
        return value;
    }

    public static final class Memorized<T> extends Lazy<T> {

        private Memorized(@Nullable Supplier<T> supplier) {
            super(supplier);
        }

        @Override
        public T get() {
            if (super.value == null) {
                Preconditions.checkState(super.supplier != null, "Supplier cannot be null when value is null");
                super.value = super.supplier.get();
                // Do not release the supplier, as we want to allow re-computation if needed
            }
            return super.value;
        }

        public T reload() {
            super.value = null;
            return get();
        }
    }
}