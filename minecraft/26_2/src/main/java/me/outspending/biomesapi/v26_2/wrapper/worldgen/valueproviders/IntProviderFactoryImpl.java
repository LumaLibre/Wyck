package me.outspending.biomesapi.v26_2.wrapper.worldgen.valueproviders;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.util.WeightedList;
import me.outspending.biomesapi.util.internal.InternalReflectUtil;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.IntProvider;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.ClampedInt;
import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.TrapezoidInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@ApiStatus.Internal
public final class IntProviderFactoryImpl implements IntProvider.Factory {

    @Override
    public Object toNms(IntProvider provider) {
        return switch (provider) {
            case IntProvider.Constant c -> ConstantInt.of(c.value());
            case IntProvider.Uniform u -> UniformInt.of(u.minInclusive(), u.maxInclusive());
            case IntProvider.BiasedToBottom b -> BiasedToBottomInt.of(b.minInclusive(), b.maxInclusive());
            case IntProvider.ClampedNormal n -> ClampedNormalInt.of(n.mean(), n.deviation(), n.minInclusive(), n.maxInclusive());
            case IntProvider.Trapezoid t -> TrapezoidInt.of(t.minInclusive(), t.maxInclusive(), t.plateau());
            case IntProvider.Clamped clamped -> buildClamped(clamped);
            case IntProvider.WeightedListInt weighted -> buildWeightedList(weighted);
        };
    }

    @Override
    public IntProvider fromMinecraft(Object nms) {
        net.minecraft.util.valueproviders.IntProvider provider = (net.minecraft.util.valueproviders.IntProvider) nms;
        return switch (provider) {
            case ConstantInt(int value) -> IntProvider.constant(value);
            case UniformInt(int minInclusive, int maxInclusive) -> IntProvider.uniform(minInclusive, maxInclusive);
            case BiasedToBottomInt(int minInclusive, int maxInclusive) -> IntProvider.biasedToBottom(minInclusive, maxInclusive);
            case ClampedNormalInt(float mean, float deviation, int minInclusive, int maxInclusive) -> IntProvider.clampedNormal(mean, deviation, minInclusive, maxInclusive);
            case TrapezoidInt(int minInclusive, int maxInclusive, int plateau) -> IntProvider.trapezoid(minInclusive, maxInclusive, plateau);
            case ClampedInt(net.minecraft.util.valueproviders.IntProvider source, int minInclusive, int maxInclusive) -> {
                IntProvider sourceProvider = this.fromMinecraft(source);
                yield IntProvider.clamped(sourceProvider, minInclusive, maxInclusive);
            }
            case WeightedListInt weightedListInt -> {
                net.minecraft.util.random.WeightedList<net.minecraft.util.valueproviders.IntProvider> distribution =
                    InternalReflectUtil.getFieldValue(weightedListInt, "distribution");

                WeightedList.Builder<IntProvider> builder = WeightedList.builder();
                for (Weighted<net.minecraft.util.valueproviders.IntProvider> entry : distribution.unwrap()) {
                    builder.add(this.fromMinecraft(entry.value()), entry.weight());
                }
                yield IntProvider.weightedList(builder.build());
            }
            default -> throw new IllegalStateException("Unexpected value: " + provider.getClass().getName());
        };
    }

    // TODO: move

    private net.minecraft.util.valueproviders.IntProvider buildClamped(IntProvider.Clamped clamped) {
        net.minecraft.util.valueproviders.IntProvider source =
                (net.minecraft.util.valueproviders.IntProvider) clamped.source().toMinecraft();
        return net.minecraft.util.valueproviders.ClampedInt.of(source, clamped.minInclusive(), clamped.maxInclusive());
    }

    private net.minecraft.util.valueproviders.IntProvider buildWeightedList(IntProvider.WeightedListInt weighted) {
        net.minecraft.util.random.WeightedList.Builder<net.minecraft.util.valueproviders.IntProvider> builder =
                net.minecraft.util.random.WeightedList.builder();
        for (WeightedList.Weighted<IntProvider> entry : weighted.distribution().unwrap()) {
            net.minecraft.util.valueproviders.IntProvider value =
                    (net.minecraft.util.valueproviders.IntProvider) entry.value().toMinecraft();
            builder.add(value, entry.weight());
        }

        return new net.minecraft.util.valueproviders.WeightedListInt(builder.build());
    }
}