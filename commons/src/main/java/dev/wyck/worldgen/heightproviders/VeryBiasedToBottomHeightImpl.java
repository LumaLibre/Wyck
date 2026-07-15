package dev.wyck.worldgen.heightproviders;

import dev.wyck.worldgen.heightproviders.VerticalAnchor;
import dev.wyck.worldgen.heightproviders.VeryBiasedToBottomHeight;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record VeryBiasedToBottomHeightImpl(
    @Override VerticalAnchor minInclusive,
    @Override VerticalAnchor maxInclusive,
    @Override int inner
) implements VeryBiasedToBottomHeight {

    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.heightproviders.VeryBiasedToBottomHeight.of(minInclusive.asHandle(), maxInclusive.asHandle(), inner);
    }
}
