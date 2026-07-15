package dev.wyck.wrapper.worldgen.heightproviders;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record ConstantHeightImpl(@Override VerticalAnchor value) implements ConstantHeight {

    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.heightproviders.ConstantHeight.of(value.asHandle());
    }

    @Override
    public VerticalAnchor minInclusive() {
        return this.value;
    }

    @Override
    public VerticalAnchor maxInclusive() {
        return this.value;
    }
}
