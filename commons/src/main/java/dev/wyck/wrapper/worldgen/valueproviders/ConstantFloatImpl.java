package dev.wyck.wrapper.worldgen.valueproviders;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record ConstantFloatImpl(float value) implements ConstantFloat {

    @Override
    public Object toMinecraft() {
        return net.minecraft.util.valueproviders.ConstantFloat.of(value);
    }

    @Override
    public float minInclusive() {
        return value;
    }

    @Override
    public float maxInclusive() {
        return value;
    }
}
