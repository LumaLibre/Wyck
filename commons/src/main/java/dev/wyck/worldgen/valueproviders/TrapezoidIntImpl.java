package dev.wyck.worldgen.valueproviders;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record TrapezoidIntImpl(
    @Override int minInclusive,
    @Override int maxInclusive,
    @Override int plateau
) implements TrapezoidInt {
    @Override
    public Object toMinecraft() {
        return net.minecraft.util.valueproviders.TrapezoidInt.of(minInclusive, maxInclusive, plateau);
    }
}
