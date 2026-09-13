package dev.wyck.worldgen.structure.pools;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import org.jspecify.annotations.NullMarked;

/**
 * Blocks of clearance a jigsaw structure keeps from the bottom and top of the dimension.
 *
 * @param bottom blocks kept clear above the dimension floor
 * @param top blocks kept clear below the dimension ceiling
 * @since 3.4.0
 */
@NullMarked
@AsOf("3.4.0")
public record DimensionPadding(int bottom, int top) {

    public static final DimensionPadding ZERO = new DimensionPadding(0, 0);

    public DimensionPadding {
        Preconditions.checkArgument(bottom >= 0, "bottom padding must not be negative");
        Preconditions.checkArgument(top >= 0, "top padding must not be negative");
    }

    /**
     * Creates padding that is the same at the bottom and top.
     * @param padding blocks kept clear at both ends
     * @return the padding
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    public static DimensionPadding of(int padding) {
        return new DimensionPadding(padding, padding);
    }

    /**
     * Creates padding.
     * @param bottom blocks kept clear above the dimension floor
     * @param top blocks kept clear below the dimension ceiling
     * @return the padding
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    public static DimensionPadding of(int bottom, int top) {
        return new DimensionPadding(bottom, top);
    }
}
