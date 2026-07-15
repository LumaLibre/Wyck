package dev.wyck.worldgen.valueproviders;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record TrapezoidFloatImpl(
    @Override float minInclusive,
    @Override float maxInclusive,
    @Override float plateau
) implements TrapezoidFloat {
    @Override
    public Object toMinecraft() {
        return net.minecraft.util.valueproviders.TrapezoidFloat.of(minInclusive, maxInclusive, plateau);
    }
}
