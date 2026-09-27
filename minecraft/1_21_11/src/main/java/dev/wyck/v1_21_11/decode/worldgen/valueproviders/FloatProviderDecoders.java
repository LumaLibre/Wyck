package dev.wyck.v1_21_11.decode.worldgen.valueproviders;

import dev.wyck.decode.Decoders;
import dev.wyck.decode.FastReflection;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.valueproviders.ClampedNormalFloat;
import dev.wyck.worldgen.valueproviders.ConstantFloat;
import dev.wyck.worldgen.valueproviders.FloatProvider;
import dev.wyck.worldgen.valueproviders.TrapezoidFloat;
import dev.wyck.worldgen.valueproviders.UniformFloat;
import dev.wyck.wrapper.decode.DecoderRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class FloatProviderDecoders extends DecoderRegistry<FloatProvider, net.minecraft.util.valueproviders.FloatProvider> {

    public FloatProviderDecoders() {
        register("constant", nms -> ConstantFloat.of(nms.getMinValue()));
        register("uniform", nms -> {
            var uniform = (net.minecraft.util.valueproviders.UniformFloat) nms;
            return UniformFloat.of(uniform.getMinValue(), uniform.getMaxValue());
        });
        register("clamped_normal", nms -> {
            var normal = (net.minecraft.util.valueproviders.ClampedNormalFloat) nms;
            return ClampedNormalFloat.of(normal.getMinValue(), normal.getMaxValue(), FastReflection.read(normal, "mean"), FastReflection.read(normal, "deviation"));
        });
        register("trapezoid", nms -> {
            var trapezoid = (net.minecraft.util.valueproviders.TrapezoidFloat) nms;
            return TrapezoidFloat.of(trapezoid.getMinValue(), trapezoid.getMaxValue(), FastReflection.read(trapezoid, "plateau"));
        });
    }

    @Override
    protected ResourceKey discriminate(net.minecraft.util.valueproviders.FloatProvider provider) {
        return Decoders.registryKey(BuiltInRegistries.FLOAT_PROVIDER_TYPE, provider.getType());
    }
}
