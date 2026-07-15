package dev.wyck.worldgen.valueproviders;

import dev.wyck.worldgen.valueproviders.BiasedToBottomInt;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record BiasedToBottomIntImpl(
    @Override int minInclusive,
    @Override int maxInclusive
) implements BiasedToBottomInt {
    @Override
    public Object toMinecraft() {
        return net.minecraft.util.valueproviders.BiasedToBottomInt.of(minInclusive, maxInclusive);
    }
}
