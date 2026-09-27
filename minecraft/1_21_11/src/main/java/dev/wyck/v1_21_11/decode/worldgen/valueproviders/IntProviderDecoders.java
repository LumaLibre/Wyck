package dev.wyck.v1_21_11.decode.worldgen.valueproviders;

import dev.wyck.decode.Decoders;
import dev.wyck.keys.ResourceKey;
import dev.wyck.decode.FastReflection;
import dev.wyck.worldgen.valueproviders.BiasedToBottomInt;
import dev.wyck.worldgen.valueproviders.ClampedInt;
import dev.wyck.worldgen.valueproviders.ClampedNormalInt;
import dev.wyck.worldgen.valueproviders.ConstantInt;
import dev.wyck.worldgen.valueproviders.IntProvider;
import dev.wyck.worldgen.valueproviders.UniformInt;
import dev.wyck.worldgen.valueproviders.WeightedListInt;
import dev.wyck.wrapper.decode.DecoderRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.random.WeightedList;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class IntProviderDecoders extends DecoderRegistry<IntProvider, net.minecraft.util.valueproviders.IntProvider> {

    public IntProviderDecoders() {
        register("constant", nms -> ConstantInt.of(nms.getMinValue()));
        register("uniform", nms -> {
            var uniform = (net.minecraft.util.valueproviders.UniformInt) nms;
            return UniformInt.of(uniform.getMinValue(), uniform.getMaxValue());
        });
        register("biased_to_bottom", nms -> {
            var biased = (net.minecraft.util.valueproviders.BiasedToBottomInt) nms;
            return BiasedToBottomInt.of(biased.getMinValue(), biased.getMaxValue());
        });
        register("clamped", nms -> {
            var clamped = (net.minecraft.util.valueproviders.ClampedInt) nms;
            return ClampedInt.of(IntProvider.decode(FastReflection.read(clamped, "source")), FastReflection.read(clamped, "minInclusive"), FastReflection.read(clamped, "maxInclusive"));
        });
        register("clamped_normal", nms -> {
            var normal = (net.minecraft.util.valueproviders.ClampedNormalInt) nms;
            return ClampedNormalInt.of(normal.getMinValue(), normal.getMaxValue(), FastReflection.read(normal, "mean"), FastReflection.read(normal, "deviation"));
        });
        register("weighted_list", nms -> {
            WeightedList<net.minecraft.util.valueproviders.IntProvider> distribution =
                FastReflection.read(nms, "distribution");
            return WeightedListInt.of(Decoders.weighted(distribution, IntProvider::decode));
        });
    }

    @Override
    protected ResourceKey discriminate(net.minecraft.util.valueproviders.IntProvider provider) {
        return Decoders.registryKey(BuiltInRegistries.INT_PROVIDER_TYPE, provider.getType());
    }

}
