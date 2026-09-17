package dev.wyck.worldgen.biome.custom;

import dev.wyck.annotations.AsOf;
import dev.wyck.worldgen.climate.ClimatePoint;
import org.jspecify.annotations.NullMarked;

/**
 * The sampling context handed to a {@link CustomBiomeSource} while Minecraft determines a biome.
 * Coordinates supplied by Minecraft are quart positions, where one unit represents four blocks.
 *
 * @since 3.3.0
 * @version 3.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.3.0")
public interface BiomeSourceContext {

    /**
     * Gets the quart-coordinate on the x-axis.
     * @return the quart x-coordinate
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    int quartX();

    /**
     * Gets the quart-coordinate on the y-axis.
     * @return the quart y-coordinate
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    int quartY();

    /**
     * Gets the quart-coordinate on the z-axis.
     * @return the quart z-coordinate
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    int quartZ();

    /**
     * Gets the block-coordinate represented by {@link #quartX()}.
     * @return the block x-coordinate
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    default int blockX() {
        return quartX() << 2;
    }

    /**
     * Gets the block-coordinate represented by {@link #quartY()}.
     * @return the block y-coordinate
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    default int blockY() {
        return quartY() << 2;
    }

    /**
     * Gets the block-coordinate represented by {@link #quartZ()}.
     * @return the block z-coordinate
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    default int blockZ() {
        return quartZ() << 2;
    }

    /**
     * Samples Minecraft's climate values at this context's position.
     * @return the sampled climate point
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    ClimatePoint climate();
}
