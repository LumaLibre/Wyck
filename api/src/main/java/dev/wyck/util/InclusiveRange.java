package dev.wyck.util;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import org.jspecify.annotations.NullMarked;

import java.util.function.Function;

/**
 * Represents a range of values with both ends inclusive.
 * @param minInclusive the minimum value in the range
 * @param maxInclusive the maximum value in the range
 * @param <T> the type of the range values
 * @since 3.0.0
 */
@NullMarked
@AsOf("3.0.0")
public record InclusiveRange<T extends Comparable<T>>(T minInclusive, T maxInclusive) {

    public InclusiveRange {
        Preconditions.checkArgument(minInclusive.compareTo(maxInclusive) <= 0, "minInclusive must be less than or equal to maxInclusive");
    }

    /**
     * Creates a new range with the same min and max values.
     * @param value the value to use for both min and max
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    public InclusiveRange(T value) {
        this(value, value);
    }

    /**
     * Maps the range to a new type.
     * @param mapper the function to map the range values
     * @return a new range with the mapped values
     * @param <S> the type of the mapped values
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    public <S extends Comparable<S>> InclusiveRange<S> map(Function<? super T, ? extends S> mapper) {
        return new InclusiveRange<>(mapper.apply(this.minInclusive), mapper.apply(this.maxInclusive));
    }

    /**
     * Checks if a value is within the range.
     * @param value the value to check
     * @return true if the value is within the range, false otherwise
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    public boolean isValueInRange(T value) {
        return value.compareTo(this.minInclusive) >= 0 && value.compareTo(this.maxInclusive) <= 0;
    }

    /**
     * Checks if a range is within this range.
     * @param subRange the range to check
     * @return true if the range is within this range, false otherwise
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    public boolean contains(InclusiveRange<T> subRange) {
        return subRange.minInclusive().compareTo(this.minInclusive) >= 0 && subRange.maxInclusive.compareTo(this.maxInclusive) <= 0;
    }

    /**
     * Creates a new InclusiveRange instance.
     * @param minInclusive the minimum value in the range
     * @param maxInclusive the maximum value in the range
     * @return a new InclusiveRange instance
     * @param <T> the type of the range values
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    public static <T extends Comparable<T>> InclusiveRange<T> of(T minInclusive, T maxInclusive) {
        return new InclusiveRange<>(minInclusive, maxInclusive);
    }

    /**
     * Creates a new InclusiveRange instance with the same min and max values.
     * @param value the value to use for both min and max
     * @return a new InclusiveRange instance
     * @param <T> the type of the range values
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    public static <T extends Comparable<T>> InclusiveRange<T> of(T value) {
        return new InclusiveRange<>(value);
    }
}
