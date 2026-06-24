package me.outspending.biomesapi.wrapper.worldgen.carver;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.FloatProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Wraps Minecraft's CanyonCarverConfiguration.CanyonShapeConfiguration. The
 * shape parameters that drive a canyon's length, thickness, and radius profile.
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public record CanyonShapeConfiguration(
    FloatProvider distanceFactor,
    FloatProvider thickness,
    int widthSmoothness,
    FloatProvider horizontalRadiusFactor,
    float verticalRadiusDefaultFactor,
    float verticalRadiusCenterFactor
) implements NmsHandle {

    @ApiStatus.Internal
    static final WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.worldgen.carver.CanyonShapeConfigurationFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        Object toNms(CanyonShapeConfiguration configuration);
    }

    @AsOf("2.3.0")
    public CanyonShapeConfiguration {
        Preconditions.checkNotNull(distanceFactor, "distanceFactor");
        Preconditions.checkNotNull(thickness, "thickness");
        Preconditions.checkNotNull(horizontalRadiusFactor, "horizontalRadiusFactor");
        Preconditions.checkArgument(widthSmoothness > 0, "widthSmoothness must be positive");
    }

    /**
     * Creates a new Builder for CanyonShapeConfiguration.
     * @return a new Builder for CanyonShapeConfiguration
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Converts this CanyonShapeConfiguration to an NMS CanyonShapeConfiguration.
     * @return the NMS CanyonShapeConfiguration
     * @since 2.3.0
     */
    @Override
    @AsOf("2.3.0")
    public Object toMinecraft() {
        return WIRE.get().toNms(this);
    }

    /**
     * A builder for creating a CanyonShapeConfiguration.
     * @since 2.3.0
     * @version 2.3.0
     * @author Jsinco
     */
    @AsOf("2.3.0")
    public static final class Builder {

        private @Nullable FloatProvider distanceFactor;
        private @Nullable FloatProvider thickness;
        private @Nullable Integer widthSmoothness;
        private @Nullable FloatProvider horizontalRadiusFactor;
        private @Nullable Float verticalRadiusDefaultFactor;
        private @Nullable Float verticalRadiusCenterFactor;

        /**
         * Sets the distance factor of the shape.
         * @param distanceFactor the distance factor of the shape
         * @return this builder, for chaining
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder distanceFactor(FloatProvider distanceFactor) {
            this.distanceFactor = distanceFactor;
            return this;
        }

        /**
         * Sets the thickness of the shape.
         * @param thickness the thickness of the shape
         * @return this builder, for chaining
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder thickness(FloatProvider thickness) {
            this.thickness = thickness;
            return this;
        }

        /**
         * Sets the width smoothness of the shape.
         * @param widthSmoothness the width smoothness of the shape
         * @return this builder, for chaining
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder widthSmoothness(int widthSmoothness) {
            this.widthSmoothness = widthSmoothness;
            return this;
        }

        /**
         * Sets the horizontal radius factor of the shape.
         * @param horizontalRadiusFactor the horizontal radius factor of the shape
         * @return this builder, for chaining
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder horizontalRadiusFactor(FloatProvider horizontalRadiusFactor) {
            this.horizontalRadiusFactor = horizontalRadiusFactor;
            return this;
        }

        /**
         * Sets the vertical radius default factor of the shape.
         * @param verticalRadiusDefaultFactor the vertical radius default factor of the shape
         * @return this builder, for chaining
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder verticalRadiusDefaultFactor(float verticalRadiusDefaultFactor) {
            this.verticalRadiusDefaultFactor = verticalRadiusDefaultFactor;
            return this;
        }

        /**
         * Sets the vertical radius center factor of the shape.
         * @param verticalRadiusCenterFactor the vertical radius center factor of the shape
         * @return this builder, for chaining
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder verticalRadiusCenterFactor(float verticalRadiusCenterFactor) {
            this.verticalRadiusCenterFactor = verticalRadiusCenterFactor;
            return this;
        }

        /**
         * Builds the CanyonShapeConfiguration.
         * @return the CanyonShapeConfiguration
         * @throws IllegalStateException if any required fields are not set
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public CanyonShapeConfiguration build() {
            Preconditions.checkState(this.distanceFactor != null, "distanceFactor must be set");
            Preconditions.checkState(this.thickness != null, "thickness must be set");
            Preconditions.checkState(this.widthSmoothness != null, "widthSmoothness must be set");
            Preconditions.checkState(this.horizontalRadiusFactor != null, "horizontalRadiusFactor must be set");
            Preconditions.checkState(this.verticalRadiusDefaultFactor != null, "verticalRadiusDefaultFactor must be set");
            Preconditions.checkState(this.verticalRadiusCenterFactor != null, "verticalRadiusCenterFactor must be set");

            return new CanyonShapeConfiguration(
                    this.distanceFactor,
                    this.thickness,
                    this.widthSmoothness,
                    this.horizontalRadiusFactor,
                    this.verticalRadiusDefaultFactor,
                    this.verticalRadiusCenterFactor
            );
        }
    }
}