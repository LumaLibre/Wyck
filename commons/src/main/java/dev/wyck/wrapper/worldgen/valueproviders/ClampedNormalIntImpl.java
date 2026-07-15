package dev.wyck.wrapper.worldgen.valueproviders;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record ClampedNormalIntImpl(
    @Override int minInclusive,
    @Override int maxInclusive,
    @Override float mean,
    @Override float deviation
) implements ClampedNormalInt {
    @Override
    public Object toMinecraft() {
        return net.minecraft.util.valueproviders.ClampedNormalInt.of(mean, deviation, minInclusive, maxInclusive);
    }
}
