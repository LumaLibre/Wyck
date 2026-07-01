package me.outspending.biomesapi.wrapper.worldgen.climate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.wrapper.internal.NmsDecoder;
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

    Codec<ClimateParameter> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.floatRange(MIN_BOUNDARY, MAX_BOUNDARY).fieldOf("min").forGetter(ClimateParameter::min),
        Codec.floatRange(MIN_BOUNDARY, MAX_BOUNDARY).fieldOf("max").forGetter(ClimateParameter::max)
    ).apply(instance, ClimateParameter::span));

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.worldgen.climate.ClimateParameterFactoryImpl");

    @ApiStatus.Internal
    interface Factory extends NmsDecoder<ClimateParameter> {
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
     * @return a BiomeParameter pinned to the given value
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
     * @return a BiomeParameter between the given values
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ClimateParameter span(float min, float max) {
        return WIRE.get().create(min, max);
    }

    @AsOf("2.4.0")
    static ClimateParameter fromMinecraft(Object nms) {
        return WIRE.get().fromMinecraft(nms);
    }
}