package me.outspending.biomesapi.wrapper.worldgen.valueproviders;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
public final class FloatProviderFactoryImpl implements FloatProvider.Factory {

    @Override
    public @NotNull Object toNms(@NotNull FloatProvider provider) {
        return switch (provider) {
            case FloatProvider.Constant c ->
                    net.minecraft.util.valueproviders.ConstantFloat.of(c.value());
            case FloatProvider.Uniform u ->
                    net.minecraft.util.valueproviders.UniformFloat.of(u.minInclusive(), u.maxExclusive());
            case FloatProvider.ClampedNormal n ->
                    net.minecraft.util.valueproviders.ClampedNormalFloat.of(n.mean(), n.deviation(), n.min(), n.max());
            case FloatProvider.Trapezoid t ->
                    net.minecraft.util.valueproviders.TrapezoidFloat.of(t.min(), t.max(), t.plateau());
        };
    }
}