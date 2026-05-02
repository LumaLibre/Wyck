package me.outspending.biomesapi.wrapper.environment.attribute;

import net.minecraft.world.attribute.EnvironmentAttributeMap;
import net.minecraft.world.level.biome.Biome;

public final class NmsEnvironmentAttributes {

    private NmsEnvironmentAttributes() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Build a new NMS EnvironmentAttributeMap from the wrapped map.
     */
    public static EnvironmentAttributeMap toNms(WrappedEnvironmentAttributeMap map) {
        EnvironmentAttributeMap.Builder builder = EnvironmentAttributeMap.builder();
        applyTo(builder, map);
        return builder.build();
    }

    /**
     * Apply every wrapped attribute to an existing NMS map builder.
     */
    public static void applyTo(EnvironmentAttributeMap.Builder builder, WrappedEnvironmentAttributeMap map) {
        for (WrappedEnvironmentAttribute<?, ?> w : map.attributes()) {
            apply(builder, w);
        }
    }

    /**
     * Apply every wrapped attribute as a modifier on an existing NMS map.
     */
    public static void applyTo(EnvironmentAttributeMap target, WrappedEnvironmentAttributeMap map) {
        for (WrappedEnvironmentAttribute<?, ?> w : map.attributes()) {
            applyAsModifier(target, w);
        }
    }

    /**
     * Apply every wrapped attribute on a Biome.BiomeBuilder.
     */
    public static void applyTo(Biome.BiomeBuilder builder, WrappedEnvironmentAttributeMap map) {
        for (WrappedEnvironmentAttribute<?, ?> w : map.attributes()) {
            apply(builder, w);
        }
    }

    private static <T, K> void apply(EnvironmentAttributeMap.Builder builder, WrappedEnvironmentAttribute<T, K> w) {
        EnvironmentAttributeHandleImpl<T> handle = (EnvironmentAttributeHandleImpl<T>) w.getAttribute();
        builder.set(handle.nms(), w.getConvertedValue());
    }

    private static <T, K> void applyAsModifier(EnvironmentAttributeMap target, WrappedEnvironmentAttribute<T, K> w) {
        EnvironmentAttributeHandleImpl<T> handle = (EnvironmentAttributeHandleImpl<T>) w.getAttribute();
        target.applyModifier(handle.nms(), w.getConvertedValue());
    }

    private static <T, K> void apply(Biome.BiomeBuilder builder, WrappedEnvironmentAttribute<T, K> w) {
        EnvironmentAttributeHandleImpl<T> handle = (EnvironmentAttributeHandleImpl<T>) w.getAttribute();
        builder.setAttribute(handle.nms(), w.getConvertedValue());
    }
}