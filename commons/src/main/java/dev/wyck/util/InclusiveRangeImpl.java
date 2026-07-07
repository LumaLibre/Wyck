package dev.wyck.util;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record InclusiveRangeImpl<T extends Comparable<T>>(
    @Override T minInclusive,
    @Override T maxInclusive
) implements InclusiveRange<T> {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.util.InclusiveRange<>(minInclusive, maxInclusive);
    }
}
