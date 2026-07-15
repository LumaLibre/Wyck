package dev.wyck.worldgen.heightproviders;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record UniformHeightImpl(
    @Override VerticalAnchor minInclusive,
    @Override VerticalAnchor maxInclusive
) implements UniformHeight {

    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.heightproviders.UniformHeight.of(minInclusive.asHandle(), maxInclusive.asHandle());
    }
}
