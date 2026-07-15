package dev.wyck.worldgen.function.transformer;

import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.Registerable;
import dev.wyck.worldgen.function.DensityFunction;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("3.0.0")
public interface PureTransformer extends DensityFunction, Registerable<PureTransformer> {

    /**
     * The input density function.
     * @return the input density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    DensityFunction input();
}
