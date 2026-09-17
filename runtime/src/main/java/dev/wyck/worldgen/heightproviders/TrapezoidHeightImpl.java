package dev.wyck.worldgen.heightproviders;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record TrapezoidHeightImpl(
    @Override VerticalAnchor minInclusive,
    @Override VerticalAnchor maxInclusive,
    @Override int plateau
) implements TrapezoidHeight {

    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.heightproviders.TrapezoidHeight.of(minInclusive.asHandle(), maxInclusive.asHandle(), plateau);
    }
}
