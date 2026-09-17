package dev.wyck.worldgen.valueproviders;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record UniformIntImpl(
    @Override int minInclusive,
    @Override int maxInclusive
) implements UniformInt {
    @Override
    public Object toMinecraft() {
        return net.minecraft.util.valueproviders.UniformInt.of(minInclusive, maxInclusive);
    }
}
