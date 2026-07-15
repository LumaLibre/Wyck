package dev.wyck.worldgen.valueproviders;

import dev.wyck.worldgen.valueproviders.ClampedInt;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record ClampedIntImpl(
    @Override IntProvider source,
    @Override int minInclusive,
    @Override int maxInclusive
) implements ClampedInt {
    @Override
    public Object toMinecraft() {
        net.minecraft.util.valueproviders.IntProvider nmsSource = source.asHandle();
        return net.minecraft.util.valueproviders.ClampedInt.of(nmsSource, minInclusive, maxInclusive);
    }
}
