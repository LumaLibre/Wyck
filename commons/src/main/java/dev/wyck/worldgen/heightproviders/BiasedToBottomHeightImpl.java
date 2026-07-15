package dev.wyck.worldgen.heightproviders;

import dev.wyck.worldgen.heightproviders.BiasedToBottomHeight;
import dev.wyck.worldgen.heightproviders.VerticalAnchor;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record BiasedToBottomHeightImpl(
    @Override VerticalAnchor minInclusive,
    @Override VerticalAnchor maxInclusive,
    @Override int inner
) implements BiasedToBottomHeight {

    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.heightproviders.BiasedToBottomHeight.of(minInclusive.asHandle(), maxInclusive.asHandle(), inner);
    }
}
