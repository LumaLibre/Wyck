package dev.wyck.wrapper.worldgen.function.transformer;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.worldgen.function.DensityFunction;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * Clamps the input between two values.
 *
 * @see <a href="https://minecraft.wiki/w/Density_function#clamp">Density function (clamp)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface ClampedTransformer extends PureTransformer {

    @ApiStatus.Internal
    ConstructWireProvider<ClampedTransformer> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.function.transformer.ClampedTransformerImpl");

    /**
     * The minimum value.
     * @return the minimum value
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    double min();

    /**
     * The maximum value.
     * @return the maximum value
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    double max();

    /**
     * Creates a new clamped density function.
     * @param resourceKey the resource key, or null
     * @param input the input density function
     * @param min the minimum value
     * @param max the maximum value
     * @return the clamped density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ClampedTransformer of(@Nullable ResourceKey resourceKey, DensityFunction input, double min, double max) {
        return WIRE.construct(Optional.ofNullable(resourceKey), input, min, max);
    }

    /**
     * Creates a new clamped density function.
     * @param input the input density function
     * @param min the minimum value
     * @param max the maximum value
     * @return the clamped density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ClampedTransformer of(DensityFunction input, double min, double max) {
        return of(null, input, min, max);
    }
}
