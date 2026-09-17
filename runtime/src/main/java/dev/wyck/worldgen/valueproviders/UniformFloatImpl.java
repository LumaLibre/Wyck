package dev.wyck.worldgen.valueproviders;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record UniformFloatImpl(
    @Override float minInclusive,
    @Override float maxExclusive
) implements UniformFloat {
    @Override
    public Object toMinecraft() {
        return net.minecraft.util.valueproviders.UniformFloat.of(minInclusive, maxExclusive);
    }
}
