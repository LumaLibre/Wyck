package me.outspending.biomesapi.wrapper.worldgen.feature.config;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import org.jspecify.annotations.NullMarked;

/**
 * Wraps Minecraft's FeatureConfiguration. Each concrete configuration converts
 * to its NMS counterpart via {@link #toMinecraft()}.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public interface FeatureConfiguration extends NmsHandle {
}