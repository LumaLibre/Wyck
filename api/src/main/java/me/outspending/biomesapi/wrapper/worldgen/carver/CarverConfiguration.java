package me.outspending.biomesapi.wrapper.worldgen.carver;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.NmsHandle;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.FloatProvider;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.HeightProvider;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.VerticalAnchor;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Wraps Minecraft's CarverConfiguration class.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@AsOf("2.3.0")
public sealed interface CarverConfiguration extends NmsHandle permits CanyonCarverConfiguration, CaveCarverConfiguration {

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
    @NotNull HeightProvider y();

    /**
     * @return the y-scale of the carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    @NotNull FloatProvider yScale();

    /**
     * @return the y-level of the lava level of the carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    @NotNull VerticalAnchor lavaLevel();

    /**
     * @return the debug settings of the carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    @NotNull CarverDebugSettings debugSettings();

    /**
     * @return the materials that can be replaced by this carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    @NotNull Collection<Material> replaceable();
}