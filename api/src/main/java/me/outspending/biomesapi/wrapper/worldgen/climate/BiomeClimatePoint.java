package me.outspending.biomesapi.wrapper.worldgen.climate;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Wraps Minecraft's BiomeClimatePoint.
 *
 * @since 2.3.0
 * @version 2.3.0
 */
@NullMarked
@AsOf("2.3.0")
public interface BiomeClimatePoint extends NmsHandle {

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.worldgen.climate.BiomeClimatePointFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        BiomeClimatePoint create(BiomeParameter temperature, BiomeParameter humidity, BiomeParameter continentalness, BiomeParameter erosion, BiomeParameter depth, BiomeParameter weirdness, float offset);
    }

    /**
     * The temperature axis span.
     * @return the temperature parameter
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    BiomeParameter temperature();

    /**
     * The humidity axis span.
     * @return the humidity parameter
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    BiomeParameter humidity();

    /**
     * The continentalness axis span.
     * @return the continentalness parameter
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    BiomeParameter continentalness();

    /**
     * The erosion axis span.
     * @return the erosion parameter
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    BiomeParameter erosion();

    /**
     * The depth axis span.
     * @return the depth parameter
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    BiomeParameter depth();

    /**
     * The weirdness axis span.
     * @return the weirdness parameter
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    BiomeParameter weirdness();

    /**
     * The constant fitness offset between 0.0 and 1.0.
     * @return the offset
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    float offset();

    /**
     * Creates a builder for a climate point. Unset axes span the full range.
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
     * Creates a climate point pinned to a single value on every axis.
     * @param temperature the temperature value
     * @param humidity the humidity value
     * @param continentalness the continentalness value
     * @param erosion the erosion value
     * @param depth the depth value
     * @param weirdness the weirdness value
     * @param offset the fitness offset
     * @return a new climate point
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static BiomeClimatePoint of(float temperature, float humidity, float continentalness, float erosion, float depth, float weirdness, float offset) {
        return WIRE.get().create(
            BiomeParameter.point(temperature),
            BiomeParameter.point(humidity),
            BiomeParameter.point(continentalness),
            BiomeParameter.point(erosion),
            BiomeParameter.point(depth),
            BiomeParameter.point(weirdness),
            offset
        );
    }

    /**
     * Builder for a climate point. Any axis left unset spans the full [-2.0, 2.0] range, so it
     * matches anywhere on that axis.
     *
     * @since 2.3.0
     * @version 2.3.0
     * @author Jsinco
     */
    @NullMarked
    @AsOf("2.3.0")
    final class Builder {

        private BiomeParameter temperature = BiomeParameter.span(BiomeParameter.MIN_BOUNDARY, BiomeParameter.MAX_BOUNDARY);
        private BiomeParameter humidity = BiomeParameter.span(BiomeParameter.MIN_BOUNDARY, BiomeParameter.MAX_BOUNDARY);
        private BiomeParameter continentalness = BiomeParameter.span(BiomeParameter.MIN_BOUNDARY, BiomeParameter.MAX_BOUNDARY);
        private BiomeParameter erosion = BiomeParameter.span(BiomeParameter.MIN_BOUNDARY, BiomeParameter.MAX_BOUNDARY);
        private BiomeParameter depth = BiomeParameter.span(BiomeParameter.MIN_BOUNDARY, BiomeParameter.MAX_BOUNDARY);
        private BiomeParameter weirdness = BiomeParameter.span(BiomeParameter.MIN_BOUNDARY, BiomeParameter.MAX_BOUNDARY);
        private float offset = 0.0f;

        private Builder() {
        }

        private Builder(boolean empty) {
            this.temperature = BiomeParameter.point(0.0f);
            this.humidity = BiomeParameter.point(0.0f);
            this.continentalness = BiomeParameter.point(0.0f);
            this.erosion = BiomeParameter.point(0.0f);
            this.depth = BiomeParameter.point(0.0f);
            this.weirdness = BiomeParameter.point(0.0f);
            this.offset = 0.0f;
        }

        @AsOf("2.3.0")
        public Builder temperature(BiomeParameter temperature) {
            this.temperature = temperature;
            return this;
        }

        @AsOf("2.3.0")
        public Builder humidity(BiomeParameter humidity) {
            this.humidity = humidity;
            return this;
        }

        @AsOf("2.3.0")
        public Builder continentalness(BiomeParameter continentalness) {
            this.continentalness = continentalness;
            return this;
        }

        @AsOf("2.3.0")
        public Builder erosion(BiomeParameter erosion) {
            this.erosion = erosion;
            return this;
        }

        @AsOf("2.3.0")
        public Builder depth(BiomeParameter depth) {
            this.depth = depth;
            return this;
        }

        @AsOf("2.3.0")
        public Builder weirdness(BiomeParameter weirdness) {
            this.weirdness = weirdness;
            return this;
        }

        @AsOf("2.3.0")
        public Builder offset(float offset) {
            this.offset = offset;
            return this;
        }

        @AsOf("2.3.0")
        public BiomeClimatePoint build() {
            return WIRE.get().create(this.temperature, this.humidity, this.continentalness, this.erosion, this.depth, this.weirdness, this.offset);
        }
    }
}