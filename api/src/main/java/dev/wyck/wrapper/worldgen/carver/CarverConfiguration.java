package dev.wyck.wrapper.worldgen.carver;

import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.internal.Wrapper;
import dev.wyck.wrapper.worldgen.valueproviders.FloatProvider;
import dev.wyck.wrapper.worldgen.valueproviders.HeightProvider;
import dev.wyck.wrapper.worldgen.valueproviders.VerticalAnchor;
import org.bukkit.Material;
import org.jspecify.annotations.NullMarked;

import java.util.Collection;

/**
 * Wraps Minecraft's CarverConfiguration class.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public sealed interface CarverConfiguration extends Wrapper permits CanyonCarverConfiguration, CaveCarverConfiguration {

    /**
     * @return the probability of this carver being generated
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    float probability();

    /**
     * @return the y-level of the carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    HeightProvider y();

    /**
     * @return the y-scale of the carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    FloatProvider yScale();

    /**
     * @return the y-level of the lava level of the carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    VerticalAnchor lavaLevel();

    /**
     * @return the debug settings of the carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    CarverDebugSettings debugSettings();

    /**
     * @return the materials that can be replaced by this carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    Collection<Material> replaceable();
}