package me.outspending.biomesapi.wrapper.worldgen.climate;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * A single climate axis span, wrapping {@code Climate.Parameter}.
 *
 * @since 2.3.0
 * @version 2.3.0
 */
@NullMarked
@AsOf("2.3.0")
public interface ClimateParameter extends NmsHandle {

    float MAX_BOUNDARY = 2.0f;
    float MIN_BOUNDARY = -2.0f;

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.worldgen.climate.ClimateParameterFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        ClimateParameter create(float min, float max);
    }

    /**
     * The minimum and maximum values of this parameter between -2.0 and 2.0.
     * @return the minimum and maximum values of this parameter
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    float min();

    /**
     * The minimum and maximum values of this parameter between -2.0 and 2.0.
     * @return the minimum and maximum values of this parameter
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    float max();

    /**
     * A zero width span pinned to one value.
     *
     * @param value the value to pin to
     * @return a ClimateParameter pinned to the given value
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ClimateParameter point(float value) {
        return WIRE.get().create(value, value);
    }

    /**
     * A span between two values.
     *
     * @param min the minimum value
     * @param max the maximum value
     * @return a ClimateParameter between the given values
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ClimateParameter span(float min, float max) {
        return WIRE.get().create(min, max);
    }

    /**
     * A zero width span pinned to zero.
     * @return a ClimateParameter pinned to zero
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    static ClimateParameter zero() {
        return point(0.0f);
    }

    /**
     * A span between -2.0 and 2.0.
     * @return a ClimateParameter between -2.0 and 2.0
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    static ClimateParameter all() {
        return span(MIN_BOUNDARY, MAX_BOUNDARY);
    }
}