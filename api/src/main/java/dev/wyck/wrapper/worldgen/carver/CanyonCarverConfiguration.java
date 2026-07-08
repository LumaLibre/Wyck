package dev.wyck.wrapper.worldgen.carver;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.internal.Wrapper;
import dev.wyck.wrapper.worldgen.valueproviders.FloatProvider;
import dev.wyck.wrapper.worldgen.valueproviders.HeightProvider;
import dev.wyck.wrapper.worldgen.valueproviders.VerticalAnchor;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Set;

@NullMarked
@AsOf("2.3.0")
public interface CanyonCarverConfiguration extends CarverConfiguration {

    @ApiStatus.Internal
    ConstructWireProvider<CanyonCarverConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.carver.CanyonCarverConfigurationImpl");

    @AsOf("2.3.0")
    FloatProvider verticalRotation();

    @AsOf("2.3.0")
    CanyonShapeConfiguration shape();

    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    static CanyonCarverConfiguration of(
        float probability,
        HeightProvider y,
        FloatProvider yScale,
        VerticalAnchor lavaLevel,
        CarverDebugSettings debugSettings,
        Set<Material> replaceable,
        FloatProvider verticalRotation,
        CanyonShapeConfiguration shape
    ) {
        return WIRE.construct(probability, y, yScale, lavaLevel, debugSettings, replaceable, verticalRotation, shape);
    }

    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    @AsOf("3.0.0")
    final class Builder extends CarverConfigurationBuilder<CanyonCarverConfiguration, Builder> {
        private @Nullable FloatProvider verticalRotation;
        private @Nullable CanyonShapeConfiguration shape;

        public Builder() {}

        public Builder(CanyonCarverConfiguration configuration) {
            super(configuration);
            this.verticalRotation = configuration.verticalRotation();
            this.shape = configuration.shape();
        }

        @AsOf("3.0.0")
        public Builder verticalRotation(FloatProvider verticalRotation) {
            this.verticalRotation = verticalRotation;
            return this;
        }

        @AsOf("3.0.0")
        public Builder shape(CanyonShapeConfiguration shape) {
            this.shape = shape;
            return this;
        }

        @Override
        protected CanyonCarverConfiguration create() {
            Preconditions.checkNotNull(verticalRotation, "verticalRotation must be set");
            Preconditions.checkNotNull(shape, "shape must be set");
            return CanyonCarverConfiguration.of(probability, y, yScale, lavaLevel, debugSettings, replaceable, verticalRotation, shape);
        }
    }

    @AsOf("3.0.0")
    interface CanyonShapeConfiguration extends Wrapper {

        @ApiStatus.Internal
        ConstructWireProvider<CanyonShapeConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.carver.configurations.CanyonCarverConfigurationImpl$CanyonShapeConfigurationImpl");

        @AsOf("3.0.0")
        FloatProvider distanceFor();

        @AsOf("3.0.0")
        FloatProvider thickness();

        @AsOf("3.0.0")
        int widthSmoothness();

        @AsOf("3.0.0")
        FloatProvider horizontalRadiusFactor();

        @AsOf("3.0.0")
        float verticalRadiusDefaultFactor();

        @AsOf("3.0.0")
        float verticalRadiusCenterFactor();

        @AsOf("3.0.0")
        default Builder toBuilder() {
            return new Builder(this);
        }

        @AsOf("3.0.0")
        static CanyonShapeConfiguration of(FloatProvider distanceFactor, FloatProvider thickness, int widthSmoothness, FloatProvider horizontalRadiusFactor, float verticalRadiusDefaultFactor, float verticalRadiusCenterFactor) {
            return WIRE.construct(distanceFactor, thickness, widthSmoothness, horizontalRadiusFactor, verticalRadiusDefaultFactor, verticalRadiusCenterFactor);
        }

        @AsOf("3.0.0")
        static Builder builder() {
            return new Builder();
        }

        final class Builder {
            private @Nullable FloatProvider distanceFactor;
            private @Nullable FloatProvider thickness;
            private int widthSmoothness;
            private @Nullable FloatProvider horizontalRadiusFactor;
            private float verticalRadiusDefaultFactor;
            private float verticalRadiusCenterFactor;

            public Builder() {}

            public Builder(CanyonShapeConfiguration configuration) {
                this.distanceFactor = configuration.distanceFor();
                this.thickness = configuration.thickness();
                this.widthSmoothness = configuration.widthSmoothness();
                this.horizontalRadiusFactor = configuration.horizontalRadiusFactor();
                this.verticalRadiusDefaultFactor = configuration.verticalRadiusDefaultFactor();
                this.verticalRadiusCenterFactor = configuration.verticalRadiusCenterFactor();
            }

            public Builder distanceFactor(FloatProvider distanceFactor) {
                this.distanceFactor = distanceFactor;
                return this;
            }

            public Builder thickness(FloatProvider thickness) {
                this.thickness = thickness;
                return this;
            }

            public Builder widthSmoothness(int widthSmoothness) {
                this.widthSmoothness = widthSmoothness;
                return this;
            }

            public Builder horizontalRadiusFactor(FloatProvider horizontalRadiusFactor) {
                this.horizontalRadiusFactor = horizontalRadiusFactor;
                return this;
            }

            public Builder verticalRadiusDefaultFactor(float verticalRadiusDefaultFactor) {
                this.verticalRadiusDefaultFactor = verticalRadiusDefaultFactor;
                return this;
            }

            public Builder verticalRadiusCenterFactor(float verticalRadiusCenterFactor) {
                this.verticalRadiusCenterFactor = verticalRadiusCenterFactor;
                return this;
            }

            public CanyonShapeConfiguration build() {
                Preconditions.checkArgument(widthSmoothness >= 0, "widthSmoothness must be positive");
                //noinspection ConstantConditions
                return CanyonShapeConfiguration.of(distanceFactor, thickness, widthSmoothness, horizontalRadiusFactor, verticalRadiusDefaultFactor, verticalRadiusCenterFactor);
            }
        }
    }
}
