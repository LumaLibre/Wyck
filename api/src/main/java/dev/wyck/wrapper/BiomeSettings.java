package dev.wyck.wrapper;

import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.biome.TemperatureModifier;
import org.jspecify.annotations.NullMarked;

/**
 * This is a record class that represents the settings for a biome in Minecraft.
 * It includes properties such as depth, scale, temperature, downfall, and a temperature modifier.
 * It also includes a nested Builder class for creating instances of BiomeSettings.
 *
 * @version 0.0.8
 * @since 0.0.1
 * @author Outspending
 * @deprecated No longer representative of how climate settings should be defined,
 * use {@link dev.wyck.wrapper.biome.ClimateSettings}.
 */
@Deprecated(forRemoval = true)
@NullMarked
@AsOf("0.0.8")
public record BiomeSettings(
    float depth,
    float scale,
    float temperature,
    float downfall,
    TemperatureModifier modifier,
    boolean hasPrecipitation
) {

    /**
     * This method creates a new Builder object for creating instances of BiomeSettings.
     *
     * @since 0.0.1
     * @return a new Builder object.
     */
    @AsOf("0.0.1")
    public static Builder builder() {
        return new Builder();
    }

    /**
     * This method creates a new BiomeSettings object with default settings.
     * The default settings are a depth of 0.1, a scale of 0.2, a temperature of 0.5, a downfall of 0.5, and a temperature modifier of NONE.
     *
     * @since 0.0.1
     * @return a new BiomeSettings object with default settings.
     */
    @AsOf("0.0.1")
    public static BiomeSettings defaultSettings() {
        return new BiomeSettings(0.1F, 0.2F, 0.5F, 0.5F, TemperatureModifier.NONE, true);
    }

    /**
     * This is a nested Builder class for creating instances of BiomeSettings.
     * It uses the Builder pattern, where you call a chain of methods to set the properties,
     * and then call build() to create the object.
     *
     * @version 0.0.8
     * @since 0.0.1
     */
    @AsOf("0.0.8")
    public static final class Builder {

        private float depth = 0.1F;
        private float scale = 0.2F;
        private float temperature = 0.5F;
        private float downfall = 0.5F;
        private TemperatureModifier modifier = TemperatureModifier.NONE;
        private boolean hasPrecipitation = true;

        /**
         * This method sets the depth property of the BiomeSettings.
         *
         * @param depth The depth of the biome.
         * @since 0.0.1
         * @return The Builder object, for chaining method calls.
         */
        @AsOf("0.0.1")
        public Builder depth(float depth) {
            this.depth = depth;
            return this;
        }

        /**
         * This method sets the scale property of the BiomeSettings.
         *
         * @param scale The scale of the biome.
         * @since 0.0.1
         * @return The Builder object, for chaining method calls.
         */
        @AsOf("0.0.1")
        public Builder scale(float scale) {
            this.scale = scale;
            return this;
        }

        /**
         * This method sets the temperature property of the BiomeSettings.
         *
         * @param temperature The temperature of the biome.
         * @since 0.0.1
         * @return The Builder object, for chaining method calls.
         */
        @AsOf("0.0.1")
        public Builder temperature(float temperature) {
            this.temperature = temperature;
            return this;
        }

        /**
         * This method sets the downfall property of the BiomeSettings.
         *
         * @param downfall The downfall of the biome.
         * @since 0.0.1
         * @return The Builder object, for chaining method calls.
         */
        @AsOf("0.0.1")
        public Builder downfall(float downfall) {
            this.downfall = downfall;
            return this;
        }

        /**
         * This method sets the temperature modifier property of the BiomeSettings.
         *
         * @param modifier The temperature modifier of the biome.
         * @since  0.0.1
         * @return The Builder object, for chaining method calls.
         */
        @AsOf("0.0.8")
        public Builder modifier(TemperatureModifier modifier) {
            this.modifier = modifier;
            return this;
        }

        /**
         * This method sets whether the biome has precipitation.
         *
         * @param hasPrecipitation Whether the biome has precipitation.
         * @since 0.0.8
         * @return The Builder object, for chaining method calls.
         */
        @AsOf("0.0.8")
        public Builder hasPrecipitation(boolean hasPrecipitation) {
            this.hasPrecipitation = hasPrecipitation;
            return this;
        }

        /**
         * This method creates a new BiomeSettings object with the properties set in the Builder.
         *
         * @since 0.0.1
         * @return a new BiomeSettings object.
         */
        @AsOf("0.0.1")
        public BiomeSettings build() {
            return new BiomeSettings(depth, scale, temperature, downfall, modifier, hasPrecipitation);
        }

    }

}
