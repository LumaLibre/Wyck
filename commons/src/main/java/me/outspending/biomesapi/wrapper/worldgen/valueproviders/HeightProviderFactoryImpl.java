package me.outspending.biomesapi.wrapper.worldgen.valueproviders;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.util.internal.InternalReflectUtil;
import net.minecraft.world.level.levelgen.heightproviders.BiasedToBottomHeight;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.heightproviders.TrapezoidHeight;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.heightproviders.VeryBiasedToBottomHeight;
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
                yield ConstantHeight.of(value);
            }
            case HeightProvider.Uniform u -> {
                net.minecraft.world.level.levelgen.VerticalAnchor min = nmsAnchor(u.minInclusive());
                net.minecraft.world.level.levelgen.VerticalAnchor max = nmsAnchor(u.maxInclusive());
                yield UniformHeight.of(min, max);
            }
            case HeightProvider.Trapezoid t -> {
                net.minecraft.world.level.levelgen.VerticalAnchor min = nmsAnchor(t.minInclusive());
                net.minecraft.world.level.levelgen.VerticalAnchor max = nmsAnchor(t.maxInclusive());
                yield TrapezoidHeight.of(min, max, t.plateau());
            }
            case HeightProvider.BiasedToBottom b -> {
                net.minecraft.world.level.levelgen.VerticalAnchor min = nmsAnchor(b.minInclusive());
                net.minecraft.world.level.levelgen.VerticalAnchor max = nmsAnchor(b.maxInclusive());
                yield BiasedToBottomHeight.of(min, max, b.inner());
            }
            case HeightProvider.VeryBiasedToBottom v -> {
                net.minecraft.world.level.levelgen.VerticalAnchor min = nmsAnchor(v.minInclusive());
                net.minecraft.world.level.levelgen.VerticalAnchor max = nmsAnchor(v.maxInclusive());
                yield VeryBiasedToBottomHeight.of(min, max, v.inner());
            }
        };
    }

    @Override
    public HeightProvider fromMinecraft(Object nms) {
        net.minecraft.world.level.levelgen.heightproviders.HeightProvider nmsProvider =
                (net.minecraft.world.level.levelgen.heightproviders.HeightProvider) nms;

        return switch (nmsProvider) {
            case ConstantHeight c -> {
                VerticalAnchor value = VerticalAnchor.fromMinecraft(c.getValue());
                yield HeightProvider.constant(value);
            }
            case UniformHeight u -> {
                VerticalAnchor min = VerticalAnchor.fromMinecraft(InternalReflectUtil.getFieldValue(u, "minInclusive"));
                VerticalAnchor max = VerticalAnchor.fromMinecraft(InternalReflectUtil.getFieldValue(u, "maxInclusive"));
                yield HeightProvider.uniform(min, max);
            }
            case TrapezoidHeight t -> {
                VerticalAnchor min = VerticalAnchor.fromMinecraft(InternalReflectUtil.getFieldValue(t, "minInclusive"));
                VerticalAnchor max = VerticalAnchor.fromMinecraft(InternalReflectUtil.getFieldValue(t, "maxInclusive"));
                yield HeightProvider.trapezoid(min, max, InternalReflectUtil.getFieldValue(t, "plateau"));
            }
            case BiasedToBottomHeight b -> {
                VerticalAnchor min = VerticalAnchor.fromMinecraft(InternalReflectUtil.getFieldValue(b, "minInclusive"));
                VerticalAnchor max = VerticalAnchor.fromMinecraft(InternalReflectUtil.getFieldValue(b, "maxInclusive"));
                yield HeightProvider.biasedToBottom(min, max, InternalReflectUtil.getFieldValue(b, "inner"));
            }
            case VeryBiasedToBottomHeight v -> {
                VerticalAnchor min = VerticalAnchor.fromMinecraft(InternalReflectUtil.getFieldValue(v, "minInclusive"));
                VerticalAnchor max = VerticalAnchor.fromMinecraft(InternalReflectUtil.getFieldValue(v, "maxInclusive"));
                yield HeightProvider.veryBiasedToBottom(min, max, InternalReflectUtil.getFieldValue(v, "inner"));
            }
            default -> throw new IllegalStateException("Unexpected value: " + nmsProvider.getClass().getSimpleName());
        };
    }

    private net.minecraft.world.level.levelgen.VerticalAnchor nmsAnchor(VerticalAnchor anchor) {
        return (net.minecraft.world.level.levelgen.VerticalAnchor) anchor.toMinecraft();
    }
}