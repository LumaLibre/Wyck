package dev.wyck.wrapper.worldgen.valueproviders;

import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.internal.Wrapper;
import org.jspecify.annotations.NullMarked;

/**
 * A {@link Wrapper} over a numeric value-provider family whose members expose a
 * range of yieldable values. Both {@link IntProvider} and {@link FloatProvider}
 * extend this, so providers are mutually {@link Comparable} across the int and
 * float families: ordering is by {@link #min()} first, then {@link #max()}, each
 * viewed as a {@code double}.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface ValueProvider extends Wrapper, Comparable<ValueProvider> {

    /**
     * The smallest value this provider can yield, as a double.
     * @return the smallest value this provider can yield
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    double min();

    /**
     * The largest value this provider can yield, as a double.
     * @return the largest value this provider can yield
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    double max();

    @Override
    default int compareTo(ValueProvider other) {
        int cmp = Double.compare(this.min(), other.min());
        if (cmp != 0) {
            return cmp;
        }
        return Double.compare(this.max(), other.max());
    }
}
