package dev.wyck.v1_21_11.wrapper.worldgen.valueproviders;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.WireFactory;
import dev.wyck.util.WeightedList;
import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.logging.Logger;

@NullMarked
@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
public final class IntProviderFactoryImpl implements IntProvider.Factory {

    private static final Logger LOGGER = Logger.getLogger(IntProviderFactoryImpl.class.getName());

    @Override
    public Object toNms(IntProvider provider) {
        return switch (provider) {
            case IntProvider.Constant c -> ConstantInt.of(c.value());
            case IntProvider.Uniform u -> UniformInt.of(u.minInclusive(), u.maxInclusive());
            case IntProvider.BiasedToBottom b -> BiasedToBottomInt.of(b.minInclusive(), b.maxInclusive());
            case IntProvider.ClampedNormal n -> ClampedNormalInt.of(n.mean(), n.deviation(), n.minInclusive(), n.maxInclusive());
            case IntProvider.Trapezoid t -> {
                LOGGER.warning("TrapezoidInt is not supported on 1.21.11, defaulting to ConstantInt of " + t.minInclusive());
                yield ConstantInt.of(t.minInclusive());
            }
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