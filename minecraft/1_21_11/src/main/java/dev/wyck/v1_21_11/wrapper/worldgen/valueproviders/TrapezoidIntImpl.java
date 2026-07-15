package dev.wyck.v1_21_11.wrapper.worldgen.valueproviders;

import dev.wyck.wrapper.worldgen.valueproviders.TrapezoidInt;
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
        throw new UnsupportedOperationException("Doesn't exist in this version of Minecraft");
    }
}
