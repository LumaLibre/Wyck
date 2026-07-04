package dev.wyck.wrapper.worldgen.valueproviders;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.WireFactory;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
public final class HeightProviderFactoryImpl implements HeightProvider.Factory {

    @Override
    public Object toNms(HeightProvider provider) {
        return switch (provider) {
            case HeightProvider.Constant c -> {
                net.minecraft.world.level.levelgen.VerticalAnchor value = nmsAnchor(c.value());
                yield net.minecraft.world.level.levelgen.heightproviders.ConstantHeight.of(value);
            }
            case HeightProvider.Uniform u -> {
                net.minecraft.world.level.levelgen.VerticalAnchor min = nmsAnchor(u.minInclusive());
                net.minecraft.world.level.levelgen.VerticalAnchor max = nmsAnchor(u.maxInclusive());
                yield net.minecraft.world.level.levelgen.heightproviders.UniformHeight.of(min, max);
            }
            case HeightProvider.Trapezoid t -> {
                net.minecraft.world.level.levelgen.VerticalAnchor min = nmsAnchor(t.minInclusive());
                net.minecraft.world.level.levelgen.VerticalAnchor max = nmsAnchor(t.maxInclusive());
                yield net.minecraft.world.level.levelgen.heightproviders.TrapezoidHeight.of(min, max, t.plateau());
            }
            case HeightProvider.BiasedToBottom b -> {
                net.minecraft.world.level.levelgen.VerticalAnchor min = nmsAnchor(b.minInclusive());
                net.minecraft.world.level.levelgen.VerticalAnchor max = nmsAnchor(b.maxInclusive());
                yield net.minecraft.world.level.levelgen.heightproviders.BiasedToBottomHeight.of(min, max, b.inner());
            }
            case HeightProvider.VeryBiasedToBottom v -> {
                net.minecraft.world.level.levelgen.VerticalAnchor min = nmsAnchor(v.minInclusive());
                net.minecraft.world.level.levelgen.VerticalAnchor max = nmsAnchor(v.maxInclusive());
                yield net.minecraft.world.level.levelgen.heightproviders.VeryBiasedToBottomHeight.of(min, max, v.inner());
            }
        };
    }

    private net.minecraft.world.level.levelgen.VerticalAnchor nmsAnchor(VerticalAnchor anchor) {
        return (net.minecraft.world.level.levelgen.VerticalAnchor) anchor.toMinecraft();
    }
}