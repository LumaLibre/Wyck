package dev.wyck.v26_2.wrapper.worldgen.valueproviders;

import dev.wyck.annotations.WireFactory;
import dev.wyck.util.WeightedList;
import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.TrapezoidInt;
import net.minecraft.util.valueproviders.UniformInt;
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