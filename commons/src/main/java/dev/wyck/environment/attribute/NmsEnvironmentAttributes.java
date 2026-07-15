package dev.wyck.environment.attribute;

import dev.wyck.environment.attribute.EnvironmentAttribute;
import dev.wyck.environment.attribute.EnvironmentAttributeMap;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

// TODO: Remove this class

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
        for (EnvironmentAttribute<?> w : map.values()) {
            apply(builder, w);
        }
    }

    /**
     * Apply every wrapped attribute as a modifier on an existing NMS map.
     */
    public static void applyTo(net.minecraft.world.attribute.EnvironmentAttributeMap target, EnvironmentAttributeMap map) {
        for (EnvironmentAttribute<?> w : map.values()) {
            applyAsModifier(target, w);
        }
    }

    /**
     * Apply every wrapped attribute on a Biome.BiomeBuilder.
     */
    public static void applyTo(Biome.BiomeBuilder builder, EnvironmentAttributeMap map) {
        for (EnvironmentAttribute<?> w : map.values()) {
            apply(builder, w);
        }
    }

    private static <V, U> void apply(net.minecraft.world.attribute.EnvironmentAttributeMap.Builder builder, EnvironmentAttribute<V> w) {
        net.minecraft.world.attribute.EnvironmentAttribute<U> nms = w.asHandle();
        U value = sanitize(nms, w.minecraftValue());
        builder.set(nms, value);
    }

    private static <V, U> void applyAsModifier(net.minecraft.world.attribute.EnvironmentAttributeMap target, EnvironmentAttribute<V> w) {
        net.minecraft.world.attribute.EnvironmentAttribute<U> nms = w.asHandle();
        U value = sanitize(nms, w.minecraftValue());
        target.applyModifier(nms, value);
    }

    private static <V, U> void apply(Biome.BiomeBuilder builder, EnvironmentAttribute<V> w) {
        net.minecraft.world.attribute.EnvironmentAttribute<U> nms = w.asHandle();
        U value = sanitize(nms, w.minecraftValue());
        builder.setAttribute(nms, value);
    }

    private static <T> T sanitize(net.minecraft.world.attribute.EnvironmentAttribute<T> nms, T value) {
        return nms.sanitizeValue(value);
    }
}