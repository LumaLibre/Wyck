package dev.wyck.wrapper.worldgen.feature.config;

import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.internal.Wrapper;
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
public interface FeatureConfiguration extends Wrapper {
}