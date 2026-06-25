package me.outspending.biomesapi.wrapper.worldgen.climate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Wraps Minecraft's {@code ClimatePoint}.
 *
 * @since 2.3.0
 * @version 2.3.0
 */
@NullMarked
@AsOf("2.3.0")
public interface ClimatePoint extends NmsHandle {

    Codec<ClimatePoint> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        ClimateParameter.CODEC.fieldOf("temperature").forGetter(ClimatePoint::temperature),
        ClimateParameter.CODEC.fieldOf("humidity").forGetter(ClimatePoint::humidity),
        ClimateParameter.CODEC.fieldOf("continentalness").forGetter(ClimatePoint::continentalness),
        ClimateParameter.CODEC.fieldOf("erosion").forGetter(ClimatePoint::erosion),
        ClimateParameter.CODEC.fieldOf("depth").forGetter(ClimatePoint::depth),
        ClimateParameter.CODEC.fieldOf("weirdness").forGetter(ClimatePoint::weirdness),
        Codec.floatRange(0.0f, 1.0f).fieldOf("offset").forGetter(ClimatePoint::offset)
    ).apply(instance, ClimatePoint::fromCodec));

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.worldgen.climate.ClimatePointFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        ClimatePoint create(ClimateParameter temperature, ClimateParameter humidity, ClimateParameter continentalness, ClimateParameter erosion, ClimateParameter depth, ClimateParameter weirdness, float offset);
    }

    /**
     * The temperature axis span.
     * @return the temperature parameter
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    ClimateParameter temperature();

    /**
     * The humidity axis span.
     * @return the humidity parameter
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    ClimateParameter humidity();

    /**
     * The continentalness axis span.
     * @return the continentalness parameter
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    ClimateParameter continentalness();

    /**
     * The erosion axis span.
     * @return the erosion parameter
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    ClimateParameter erosion();

    /**
     * The depth axis span.
     * @return the depth parameter
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    ClimateParameter depth();

    /**
     * The weirdness axis span.
     * @return the weirdness parameter
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    ClimateParameter weirdness();

    /**
     * The constant fitness offset between 0.0 and 1.0.
     * @return the offset
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    float offset();

    /**
     * Creates a builder for a climate climatePoint. Unset axes span the full range.
     * @return a new builder
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static Builder builder() {
        return new Builder();
    }

    @AsOf("2.4.0")
    static Builder zero() {
        return new Builder(true);
    }

    /**
     * Creates a climate climatePoint pinned to a single value on every axis.
     * @param temperature the temperature value
     * @param humidity the humidity value
     * @param continentalness the continentalness value
     * @param erosion the erosion value
     * @param depth the depth value
     * @param weirdness the weirdness value
     * @param offset the fitness offset
     * @return a new climate climatePoint
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ClimatePoint of(float temperature, float humidity, float continentalness, float erosion, float depth, float weirdness, float offset) {
        return WIRE.get().create(
            ClimateParameter.point(temperature),
            ClimateParameter.point(humidity),
            ClimateParameter.point(continentalness),
            ClimateParameter.point(erosion),
            ClimateParameter.point(depth),
            ClimateParameter.point(weirdness),
            offset
        );
    }

    @ApiStatus.Internal
    private static ClimatePoint fromCodec(ClimateParameter temperature, ClimateParameter humidity, ClimateParameter continentalness, ClimateParameter erosion, ClimateParameter depth, ClimateParameter weirdness, float offset) {
        return WIRE.get().create(temperature, humidity, continentalness, erosion, depth, weirdness, offset);
    }

    /**
     * Builder for a climate climatePoint. Any axis left unset spans the full [-2.0, 2.0] range, so it
     * matches anywhere on that axis.
     *
     * @since 2.3.0
     * @version 2.3.0
     * @author Jsinco
     */
    @NullMarked
    @AsOf("2.3.0")
    final class Builder {

        private ClimateParameter temperature = ClimateParameter.span(ClimateParameter.MIN_BOUNDARY, ClimateParameter.MAX_BOUNDARY);
        private ClimateParameter humidity = ClimateParameter.span(ClimateParameter.MIN_BOUNDARY, ClimateParameter.MAX_BOUNDARY);
        private ClimateParameter continentalness = ClimateParameter.span(ClimateParameter.MIN_BOUNDARY, ClimateParameter.MAX_BOUNDARY);
        private ClimateParameter erosion = ClimateParameter.span(ClimateParameter.MIN_BOUNDARY, ClimateParameter.MAX_BOUNDARY);
        private ClimateParameter depth = ClimateParameter.span(ClimateParameter.MIN_BOUNDARY, ClimateParameter.MAX_BOUNDARY);
        private ClimateParameter weirdness = ClimateParameter.span(ClimateParameter.MIN_BOUNDARY, ClimateParameter.MAX_BOUNDARY);
        private float offset = 0.0f;

        private Builder() {
        }

        // FIXME: this sucks lol
        private Builder(boolean empty) {
            this.temperature = ClimateParameter.point(0.0f);
            this.humidity = ClimateParameter.point(0.0f);
            this.continentalness = ClimateParameter.point(0.0f);
            this.erosion = ClimateParameter.point(0.0f);
            this.depth = ClimateParameter.point(0.0f);
            this.weirdness = ClimateParameter.point(0.0f);
            this.offset = 0.0f;
        }

        @AsOf("2.3.0")
        public Builder temperature(ClimateParameter temperature) {
            this.temperature = temperature;
            return this;
        }

        @AsOf("2.3.0")
        public Builder humidity(ClimateParameter humidity) {
            this.humidity = humidity;
            return this;
        }

        @AsOf("2.3.0")
        public Builder continentalness(ClimateParameter continentalness) {
            this.continentalness = continentalness;
            return this;
        }

        @AsOf("2.3.0")
        public Builder erosion(ClimateParameter erosion) {
            this.erosion = erosion;
            return this;
        }

        @AsOf("2.3.0")
        public Builder depth(ClimateParameter depth) {
            this.depth = depth;
            return this;
        }

        @AsOf("2.3.0")
        public Builder weirdness(ClimateParameter weirdness) {
            this.weirdness = weirdness;
            return this;
        }

        @AsOf("2.3.0")
        public Builder offset(float offset) {
            this.offset = offset;
            return this;
        }

        @AsOf("2.3.0")
        public ClimatePoint build() {
            return WIRE.get().create(this.temperature, this.humidity, this.continentalness, this.erosion, this.depth, this.weirdness, this.offset);
        }
    }
}