package dev.wyck.worldgen.function.noise;

import dev.wyck.annotations.AsOf;
import dev.wyck.worldgen.function.DensityFunction;
import dev.wyck.worldgen.synth.NoiseParameters;
import dev.wyck.wrapper.Registerable;
import org.jspecify.annotations.NullMarked;

/**
 * A density function that uses {@link NoiseParameters} to generate its output.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface NoiseParameterFunction extends DensityFunction, Registerable<NoiseParameterFunction> {

    /**
     * The noise parameters used to generate the output.
     * @return the noise parameters
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    NoiseParameters noiseParameters();
}
