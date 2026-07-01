package me.outspending.biomesapi.wrapper.worldgen.valueproviders;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
public final class FloatProviderFactoryImpl implements FloatProvider.Factory {

    @Override
    public Object toNms(FloatProvider provider) {
        return switch (provider) {
            case FloatProvider.Constant c -> net.minecraft.util.valueproviders.ConstantFloat.of(c.value());
            case FloatProvider.Uniform u -> net.minecraft.util.valueproviders.UniformFloat.of(u.minInclusive(), u.maxExclusive());
            case FloatProvider.ClampedNormal n -> net.minecraft.util.valueproviders.ClampedNormalFloat.of(n.mean(), n.deviation(), n.min(), n.max());
            case FloatProvider.Trapezoid t -> net.minecraft.util.valueproviders.TrapezoidFloat.of(t.min(), t.max(), t.plateau());
        };
    }

    @Override
    public FloatProvider fromMinecraft(Object nms) {
        return switch (nms) {
            case net.minecraft.util.valueproviders.ConstantFloat(float value) -> FloatProvider.constant(value);
            case net.minecraft.util.valueproviders.UniformFloat(float min, float max) -> FloatProvider.uniform(min, max);
            case net.minecraft.util.valueproviders.ClampedNormalFloat(float mean, float deviation, float min, float max) -> FloatProvider.clampedNormal(mean, deviation, min, max);
            case net.minecraft.util.valueproviders.TrapezoidFloat(float min, float max, float plateau) -> FloatProvider.trapezoid(min, max, plateau);
            default -> throw new IllegalArgumentException("Unknown FloatProvider type: " + nms.getClass().getSimpleName());
        };
    }
}