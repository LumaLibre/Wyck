package me.outspending.biomesapi.wrapper.worldgen.carver;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.FloatProvider;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.HeightProvider;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.VerticalAnchor;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Wraps Minecraft's CanyonCarverConfiguration, the configuration consumed by
 * the CANYON carver.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public record CanyonCarverConfiguration(
        float probability,
        HeightProvider y,
        FloatProvider yScale,
        VerticalAnchor lavaLevel,
        CarverDebugSettings debugSettings,
        Collection<Material> replaceable,
        FloatProvider verticalRotation,
        CanyonShapeConfiguration shape
) implements CarverConfiguration {

    @ApiStatus.Internal
    static final WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.worldgen.carver.CanyonCarverConfigurationFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        Object toNms(CanyonCarverConfiguration configuration);
    }

    @AsOf("2.3.0")
    public CanyonCarverConfiguration {
        Preconditions.checkNotNull(y, "y");
        Preconditions.checkNotNull(yScale, "yScale");
        Preconditions.checkNotNull(lavaLevel, "lavaLevel");
        Preconditions.checkNotNull(debugSettings, "debugSettings");
        Preconditions.checkNotNull(verticalRotation, "verticalRotation");
        Preconditions.checkNotNull(shape, "shape");
        replaceable = List.copyOf(replaceable);
    }

    /**
     * Creates a new Builder for CanyonCarverConfiguration.
     * @return a new Builder for CanyonCarverConfiguration
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Converts this CanyonCarverConfiguration to an NMS CanyonCarverConfiguration.
     * @return the NMS CanyonCarverConfiguration
     * @since 2.3.0
     */
    @Override
    @AsOf("2.3.0")
    public Object toMinecraft() {
        return WIRE.get().toNms(this);
    }

    /**
     * A builder for creating a CanyonCarverConfiguration. The debug settings
     * default to {@link CarverDebugSettings#defaultSettings()} if left unset.
     * @since 2.3.0
     * @version 2.3.0
     * @author Jsinco
     */
    @AsOf("2.3.0")
    public static final class Builder {

        private @Nullable Float probability;
        private @Nullable HeightProvider y;
        private @Nullable FloatProvider yScale;
        private @Nullable VerticalAnchor lavaLevel;
        private CarverDebugSettings debugSettings = CarverDebugSettings.defaultSettings();
        private Collection<Material> replaceable = Collections.emptyList();
        private @Nullable FloatProvider verticalRotation;
        private @Nullable CanyonShapeConfiguration shape;


        /**
         * Sets the probability of the carver being generated.
         * @param probability the probability of the carver being generated, between 0 and 1
         * @return this builder, for chaining
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder probability(float probability) {
            this.probability = probability;
            return this;
        }

        /**
         * Sets the y-coordinate of the carver.
         * @param y the y-coordinate of the carver
         * @return this builder, for chaining
         */
        @AsOf("2.3.0")
        public Builder y(HeightProvider y) {
            this.y = y;
            return this;
        }

        /**
         * Sets the y-scale of the carver.
         * @param yScale the y-scale of the carver
         * @return this builder, for chaining
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder yScale(FloatProvider yScale) {
            this.yScale = yScale;
            return this;
        }

        /**
         * Sets the lava level of the carver.
         * @param lavaLevel the lava level of the carver
         * @return this builder, for chaining
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder lavaLevel(VerticalAnchor lavaLevel) {
            this.lavaLevel = lavaLevel;
            return this;
        }

        /**
         * Sets the debug settings of the carver.
         * @param debugSettings the debug settings of the carver
         * @return this builder, for chaining
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder debugSettings(CarverDebugSettings debugSettings) {
            this.debugSettings = debugSettings;
            return this;
        }

        /**
         * Sets the replaceable materials of the carver.
         * @param replaceable the replaceable materials of the carver
         * @return this builder, for chaining
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder replaceable(Collection<Material> replaceable) {
            this.replaceable = replaceable;
            return this;
        }

        /**
         * Sets the vertical rotation of the carver.
         * @param verticalRotation the vertical rotation of the carver
         * @return this builder, for chaining
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder verticalRotation(FloatProvider verticalRotation) {
            this.verticalRotation = verticalRotation;
            return this;
        }

        /**
         * Sets the shape of the carver.
         * @param shape the shape of the carver
         * @return this builder, for chaining
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder shape(CanyonShapeConfiguration shape) {
            this.shape = shape;
            return this;
        }

        /**
         * Builds the CanyonCarverConfiguration.
         * @return the CanyonCarverConfiguration
         * @throws IllegalStateException if any required fields are not set
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public CanyonCarverConfiguration build() {
            Preconditions.checkState(this.probability != null, "probability must be set");
            Preconditions.checkState(this.y != null, "y level must be set");
            Preconditions.checkState(this.yScale != null, "yScale must be set");
            Preconditions.checkState(this.lavaLevel != null, "lavaLevel must be set");
            Preconditions.checkState(this.replaceable != null, "replaceable must be set");
            Preconditions.checkState(this.verticalRotation != null, "verticalRotation must be set");
            Preconditions.checkState(this.shape != null, "shape must be set");

            return new CanyonCarverConfiguration(
                    this.probability,
                    this.y,
                    this.yScale,
                    this.lavaLevel,
                    this.debugSettings,
                    this.replaceable,
                    this.verticalRotation,
                    this.shape
            );
        }
    }
}