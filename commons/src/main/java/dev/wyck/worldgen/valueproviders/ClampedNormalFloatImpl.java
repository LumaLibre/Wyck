package dev.wyck.worldgen.valueproviders;

import dev.wyck.worldgen.valueproviders.ClampedNormalFloat;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record ClampedNormalFloatImpl(
    @Override float minInclusive,
    @Override float maxInclusive,
    @Override float mean,
    @Override float deviation
) implements ClampedNormalFloat {
    @Override
    public Object toMinecraft() {
        return net.minecraft.util.valueproviders.ClampedNormalFloat.of(mean, deviation, minInclusive, maxInclusive);
    }
}
