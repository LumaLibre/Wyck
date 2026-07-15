package dev.wyck.wrapper.worldgen.valueproviders;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record ConstantIntImpl(int value) implements ConstantInt {

    @Override
    public Object toMinecraft() {
        return net.minecraft.util.valueproviders.ConstantInt.of(value);
    }

    @Override
    public int minInclusive() {
        return value;
    }

    @Override
    public int maxInclusive() {
        return value;
    }
}
