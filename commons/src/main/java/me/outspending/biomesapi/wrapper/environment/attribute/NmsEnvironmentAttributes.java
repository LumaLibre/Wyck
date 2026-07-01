package me.outspending.biomesapi.wrapper.environment.attribute;

import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class NmsEnvironmentAttributes {

    private NmsEnvironmentAttributes() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Build a new NMS EnvironmentAttributeMap from the wrapped map.
     */
    public static net.minecraft.world.attribute.EnvironmentAttributeMap toNms(EnvironmentAttributeMap map) {
        net.minecraft.world.attribute.EnvironmentAttributeMap.Builder builder = net.minecraft.world.attribute.EnvironmentAttributeMap.builder();
        applyTo(builder, map);
        return builder.build();
    }

    /**
     * Apply every wrapped attribute to an existing NMS map builder.
     */
    public static void applyTo(net.minecraft.world.attribute.EnvironmentAttributeMap.Builder builder, EnvironmentAttributeMap map) {
        for (EnvironmentAttribute<?, ?> w : map.values()) {
            apply(builder, w);
        }
    }

    /**
     * Apply every wrapped attribute as a modifier on an existing NMS map.
     */
    public static void applyTo(net.minecraft.world.attribute.EnvironmentAttributeMap target, EnvironmentAttributeMap map) {
        for (EnvironmentAttribute<?, ?> w : map.values()) {
            applyAsModifier(target, w);
        }
    }

    /**
     * Apply every wrapped attribute on a Biome.BiomeBuilder.
     */
    public static void applyTo(Biome.BiomeBuilder builder, EnvironmentAttributeMap map) {
        for (EnvironmentAttribute<?, ?> w : map.values()) {
            apply(builder, w);
        }
    }

    private static <T, K> void apply(net.minecraft.world.attribute.EnvironmentAttributeMap.Builder builder, EnvironmentAttribute<T, K> w) {
        EnvironmentAttributeHandleImpl<T> handle = (EnvironmentAttributeHandleImpl<T>) w.getAttribute();
        net.minecraft.world.attribute.EnvironmentAttribute<T> nms = handle.nms();
        T value = sanitize(nms, w.getConvertedValue());
        builder.set(nms, value);
    }

    private static <T, K> void applyAsModifier(net.minecraft.world.attribute.EnvironmentAttributeMap target, EnvironmentAttribute<T, K> w) {
        EnvironmentAttributeHandleImpl<T> handle = (EnvironmentAttributeHandleImpl<T>) w.getAttribute();
        net.minecraft.world.attribute.EnvironmentAttribute<T> nms = handle.nms();
        T value = sanitize(nms, w.getConvertedValue());
        target.applyModifier(nms, value);
    }

    private static <T, K> void apply(Biome.BiomeBuilder builder, EnvironmentAttribute<T, K> w) {
        EnvironmentAttributeHandleImpl<T> handle = (EnvironmentAttributeHandleImpl<T>) w.getAttribute();
        net.minecraft.world.attribute.EnvironmentAttribute<T> nms = handle.nms();
        T value = sanitize(nms, w.getConvertedValue());
        builder.setAttribute(nms, value);
    }

    private static <T> T sanitize(net.minecraft.world.attribute.EnvironmentAttribute<T> nms, T value) {
        return nms.sanitizeValue(value);
    }
}