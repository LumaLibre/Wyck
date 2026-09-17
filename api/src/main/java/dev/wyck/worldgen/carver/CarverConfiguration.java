package dev.wyck.worldgen.carver;

import dev.wyck.annotations.AsOf;
import dev.wyck.worldgen.heightproviders.HeightProvider;
import dev.wyck.wrapper.Wrapper;
import org.jspecify.annotations.NullMarked;

/**
 * Common settings shared by Minecraft's direct world carver implementations.
 *
 * @since 4.0.0
 * @version 4.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("4.0.0")
public interface CarverConfiguration extends Wrapper {

    /**
     * The probability that this carver starts in an eligible chunk.
     * @return the carver start probability
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    float probability();

    /**
     * The height provider used to select the carver's starting y-coordinate.
     * @return the starting height provider
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    HeightProvider y();
}
