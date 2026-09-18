package dev.wyck.worldgen.carver;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.heightproviders.HeightProvider;
import dev.wyck.worldgen.valueproviders.FloatProvider;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * A direct Minecraft canyon world carver.
 *
 * @see <a href="https://minecraft.wiki/w/Carver_definition#canyon">Carver definition (canyon)</a>
 * @since 4.0.0
 * @version 4.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("4.0.0")
public interface CanyonWorldCarver extends CarverConfiguration {

    /**
     * The vertical rotation applied to the canyon.
     * @return the canyon's vertical rotation
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    FloatProvider verticalRotation();

    /**
     * The settings used to shape the canyon.
     * @return the canyon shape
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    Shape shape();

    /**
     * Converts this canyon carver back to a builder.
     * @return a builder containing this carver's values
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a canyon world carver.
     * @param probability the probability that the carver starts in an eligible chunk
     * @param y the provider used to select the starting y-coordinate
     * @param verticalRotation the vertical rotation applied to the canyon
     * @param shape the settings used to shape the canyon
     * @return a new canyon world carver
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static CanyonWorldCarver of(
        float probability,
        HeightProvider y,
        FloatProvider verticalRotation,
        Shape shape
    ) {
        Preconditions.checkArgument(probability >= 0.0F && probability <= 1.0F, "probability must be between 0 and 1");
        record Holder() {
            static final ConstructWireProvider<CanyonWorldCarver> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.carver.CanyonWorldCarverImpl");
        }
        return Holder.WIRE.construct(probability, y, verticalRotation, shape);
    }

    /**
     * Creates a new canyon world carver builder.
     * @return a new builder
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link CanyonWorldCarver}.
     *
     * @since 4.0.0
     * @version 4.0.0
     * @author Jsinco
     */
    @AsOf("4.0.0")
    final class Builder {
        private float probability = 0.02F;
        private @Nullable HeightProvider y;
        private FloatProvider verticalRotation = FloatProvider.constant(0.0F);
        private @Nullable Shape shape;

        public Builder() {}

        /**
         * Creates a builder containing the values of an existing canyon carver.
         * @param carver the canyon carver to copy
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder(CanyonWorldCarver carver) {
            this.probability = carver.probability();
            this.y = carver.y();
            this.verticalRotation = carver.verticalRotation();
            this.shape = carver.shape();
        }

        /**
         * Sets the probability that the carver starts in an eligible chunk.
         * @param probability the carver start probability
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder probability(float probability) {
            this.probability = probability;
            return this;
        }

        /**
         * Sets the provider used to select the starting y-coordinate.
         * @param y the starting height provider
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder y(HeightProvider y) {
            this.y = y;
            return this;
        }

        /**
         * Sets the vertical rotation applied to the canyon.
         * @param verticalRotation the canyon's vertical rotation
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder verticalRotation(FloatProvider verticalRotation) {
            this.verticalRotation = verticalRotation;
            return this;
        }

        /**
         * Sets the settings used to shape the canyon.
         * @param shape the canyon shape
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder shape(Shape shape) {
            this.shape = shape;
            return this;
        }

        /**
         * Builds the canyon world carver.
         * @return the canyon world carver
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public CanyonWorldCarver build() {
            return CanyonWorldCarver.of(
                probability,
                Preconditions.checkNotNull(y, "y must be set"),
                verticalRotation,
                Preconditions.checkNotNull(shape, "shape must be set")
            );
        }
    }

    /**
     * The settings used to shape a {@link CanyonWorldCarver}.
     *
     * @since 4.0.0
     * @version 4.0.0
     * @author Jsinco
     */
    @NullMarked
    @AsOf("4.0.0")
    interface Shape extends Wrapper {

        /**
         * The factor controlling the canyon's distance-based horizontal shape.
         * @return the distance factor
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        FloatProvider distanceFactor();

        /**
         * The base thickness of the canyon.
         * @return the canyon thickness
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        FloatProvider thickness();

        /**
         * The number of steps over which canyon width changes are smoothed.
         * @return the width smoothness
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        int widthSmoothness();

        /**
         * The factor applied to the canyon's horizontal radius.
         * @return the horizontal radius factor
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        FloatProvider horizontalRadiusFactor();

        /**
         * The default factor applied to the canyon's vertical radius.
         * @return the default vertical radius factor
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        float verticalRadiusDefaultFactor();

        /**
         * The factor applied to the canyon's vertical radius near its center.
         * @return the center vertical radius factor
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        float verticalRadiusCenterFactor();

        /**
         * The vertical scale applied while shaping the canyon.
         * @return the canyon's vertical scale
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        FloatProvider yScale();

        /**
         * Converts this canyon shape back to a builder.
         * @return a builder containing this shape's values
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        default Shape.Builder toBuilder() {
            return new Shape.Builder(this);
        }

        /**
         * Creates a canyon shape.
         * @param distanceFactor the distance-based horizontal shape factor
         * @param thickness the base canyon thickness
         * @param widthSmoothness the number of steps over which width changes are smoothed
         * @param horizontalRadiusFactor the horizontal radius factor
         * @param verticalRadiusDefaultFactor the default vertical radius factor
         * @param verticalRadiusCenterFactor the center vertical radius factor
         * @param yScale the vertical scale applied while shaping the canyon
         * @return a new canyon shape
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        static Shape of(
            FloatProvider distanceFactor,
            FloatProvider thickness,
            int widthSmoothness,
            FloatProvider horizontalRadiusFactor,
            float verticalRadiusDefaultFactor,
            float verticalRadiusCenterFactor,
            FloatProvider yScale
        ) {
            Preconditions.checkArgument(widthSmoothness > 0, "widthSmoothness must be positive");
            record Holder() {
                static final ConstructWireProvider<Shape> WIRE = ConstructWireProvider.create(
                    "dev.wyck.worldgen.carver.CanyonWorldCarverImpl$ShapeImpl"
                );
            }
            return Holder.WIRE.construct(
                distanceFactor,
                thickness,
                widthSmoothness,
                horizontalRadiusFactor,
                verticalRadiusDefaultFactor,
                verticalRadiusCenterFactor,
                yScale
            );
        }

        /**
         * Creates a new canyon shape builder.
         * @return a new builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        static Shape.Builder builder() {
            return new Shape.Builder();
        }

        /**
         * Reads a Minecraft canyon shape.
         * @param minecraftShape the Minecraft canyon shape to read
         * @return the decoded canyon shape
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        static Shape decode(Object minecraftShape) {
            record Holder() {
                static final Decoder<Shape> DECODER = Decoder.create(
                    "dev.wyck.decode.worldgen.carver.CanyonShapeDecoder"
                );
            }
            return Holder.DECODER.decode(minecraftShape);
        }

        /**
         * Builder for {@link Shape}.
         *
         * @since 4.0.0
         * @version 4.0.0
         * @author Jsinco
         */
        @AsOf("4.0.0")
        final class Builder {
            private @Nullable FloatProvider distanceFactor;
            private @Nullable FloatProvider thickness;
            private int widthSmoothness = 1;
            private @Nullable FloatProvider horizontalRadiusFactor;
            private float verticalRadiusDefaultFactor = 1.0F;
            private float verticalRadiusCenterFactor;
            private FloatProvider yScale = FloatProvider.constant(1.0F);

            public Builder() {}

            /**
             * Creates a builder containing the values of an existing canyon shape.
             * @param shape the canyon shape to copy
             * @since 4.0.0
             */
            @AsOf("4.0.0")
            public Builder(Shape shape) {
                this.distanceFactor = shape.distanceFactor();
                this.thickness = shape.thickness();
                this.widthSmoothness = shape.widthSmoothness();
                this.horizontalRadiusFactor = shape.horizontalRadiusFactor();
                this.verticalRadiusDefaultFactor = shape.verticalRadiusDefaultFactor();
                this.verticalRadiusCenterFactor = shape.verticalRadiusCenterFactor();
                this.yScale = shape.yScale();
            }

            /**

             * Sets the distance-based horizontal shape factor.
             * @param distanceFactor the distance factor
             * @return this builder
             * @since 4.0.0
             */
            @AsOf("4.0.0")
            public Shape.Builder distanceFactor(FloatProvider distanceFactor) {
                this.distanceFactor = distanceFactor;
                return this;
            }

            /**

             * Sets the base canyon thickness.
             * @param thickness the canyon thickness
             * @return this builder
             * @since 4.0.0
             */
            @AsOf("4.0.0")
            public Shape.Builder thickness(FloatProvider thickness) {
                this.thickness = thickness;
                return this;
            }

            /**

             * Sets the number of steps over which width changes are smoothed.
             * @param widthSmoothness the width smoothness
             * @return this builder
             * @since 4.0.0
             */
            @AsOf("4.0.0")
            public Shape.Builder widthSmoothness(int widthSmoothness) {
                this.widthSmoothness = widthSmoothness;
                return this;
            }

            /**

             * Sets the horizontal radius factor.
             * @param horizontalRadiusFactor the horizontal radius factor
             * @return this builder
             * @since 4.0.0
             */
            @AsOf("4.0.0")
            public Shape.Builder horizontalRadiusFactor(FloatProvider horizontalRadiusFactor) {
                this.horizontalRadiusFactor = horizontalRadiusFactor;
                return this;
            }

            /**

             * Sets the default vertical radius factor.
             * @param verticalRadiusDefaultFactor the default vertical radius factor
             * @return this builder
             * @since 4.0.0
             */
            @AsOf("4.0.0")
            public Shape.Builder verticalRadiusDefaultFactor(float verticalRadiusDefaultFactor) {
                this.verticalRadiusDefaultFactor = verticalRadiusDefaultFactor;
                return this;
            }

            /**

             * Sets the center vertical radius factor.
             * @param verticalRadiusCenterFactor the center vertical radius factor
             * @return this builder
             * @since 4.0.0
             */
            @AsOf("4.0.0")
            public Shape.Builder verticalRadiusCenterFactor(float verticalRadiusCenterFactor) {
                this.verticalRadiusCenterFactor = verticalRadiusCenterFactor;
                return this;
            }

            /**

             * Sets the vertical scale applied while shaping the canyon.
             * @param yScale the vertical scale
             * @return this builder
             * @since 4.0.0
             */
            @AsOf("4.0.0")
            public Shape.Builder yScale(FloatProvider yScale) {
                this.yScale = yScale;
                return this;
            }

            /**
             * Builds the canyon shape.
             * @return the canyon shape
             * @since 4.0.0
             */
            @AsOf("4.0.0")
            public Shape build() {
                return Shape.of(
                    Preconditions.checkNotNull(distanceFactor, "distanceFactor must be set"),
                    Preconditions.checkNotNull(thickness, "thickness must be set"),
                    widthSmoothness,
                    Preconditions.checkNotNull(horizontalRadiusFactor, "horizontalRadiusFactor must be set"),
                    verticalRadiusDefaultFactor,
                    verticalRadiusCenterFactor,
                    yScale
                );
            }
        }
    }
}
