package me.outspending.biomesapi.biome;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.renderer.packet.PacketHandler;
import me.outspending.biomesapi.renderer.packet.data.BlockReplacement;
import me.outspending.biomesapi.wrapper.BiomeSettings;
import me.outspending.biomesapi.wrapper.environment.GrassColorModifier;
import me.outspending.biomesapi.wrapper.environment.attribute.WrappedEnvironmentAttributeMap;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleCatalog;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This interface represents a custom biome in the BiomesAPI.
 * It provides methods to retrieve and modify the properties of the custom biome.
 *
 * @version 1.1.0
 * @since 0.0.1
 * @author Outspending
 */
@AsOf("1.1.0")
public interface CustomBiome {

    /**
     * Returns a new instance of the Builder class.
     *
     * @return a new Builder instance
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    static @NotNull Builder builder() {
        return new Builder();
    }

    /**
     * Returns the NamespacedKey of the CustomBiome.
     *
     * @return the NamespacedKey of the CustomBiome
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    @NotNull NamespacedKey toNamespacedKey();

    /**
     * Returns the Bukkit Biome of the CustomBiome.
     *
     * @return the Bukkit Biome of the CustomBiome
     * @since 0.0.6
     */
    @AsOf("0.0.6")
    @NotNull Biome toBukkitBiome();

    /**
     * Returns the BiomeResourceKey of the CustomBiome.
     *
     * @return the BiomeResourceKey of the CustomBiome
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    @NotNull BiomeResourceKey getResourceKey();

    /**
     * Returns the BiomeSettings of the CustomBiome.
     *
     * @return the BiomeSettings of the CustomBiome
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    @NotNull BiomeSettings getSettings();

    /**
     * Returns the fog color of the CustomBiome.
     *
     * @return the fog color of the CustomBiome
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    @Nullable Integer getFogColor();

    /**
     * Returns the water color of the CustomBiome.
     *
     * @return the water color of the CustomBiome
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    int getWaterColor();

    /**
     * Returns the water fog color of the CustomBiome.
     *
     * @return the water fog color of the CustomBiome
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    @Nullable Integer getWaterFogColor();

    /**
     * Returns the sky color of the CustomBiome.
     *
     * @return the sky color of the CustomBiome
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    @Nullable Integer getSkyColor();

    /**
     * Returns the foliage color of the CustomBiome.
     *
     * @return the foliage color of the CustomBiome
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    @Nullable Integer getFoliageColor();

    /**
     * Returns the grass color of the CustomBiome.
     *
     * @return the grass color of the CustomBiome
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    @Nullable Integer getGrassColor();

    /**
     * Returns the dry foliage color of the CustomBiome.
     *
     * @return the dry foliage color of the CustomBiome
     * @since 1.0.2
     */
    @AsOf("1.0.2")
    @Nullable Integer getDryFoliageColor();

    /**
     * Returns the GrassColorModifier of the CustomBiome.
     *
     * @return the GrassColorModifier of the CustomBiome
     * @since 0.0.24
     */
    @AsOf("0.0.24")
    GrassColorModifier getGrassColorModifier();

    /**
     * Returns the ParticleRenderer of the CustomBiome.
     *
     * @return the ParticleRenderer of the CustomBiome
     * @since 1.1.0
     */
    @AsOf("1.1.0")
    ParticleCatalog getParticleCatalog();

    /**
     * Returns the BlockReplacements of the CustomBiome.
     *
     * @return the BlockReplacements of the CustomBiome
     * @since 0.0.6
     */
    @AsOf("0.0.6")
    @NotNull BlockReplacement[] getBlockReplacements();


    /**
     * Returns the WrappedEnvironmentAttributeMap of the CustomBiome.
     *
     * @return the WrappedEnvironmentAttributeMap of the CustomBiome
     * @since 1.1.0
     */
    @AsOf("1.1.0")
    @NotNull WrappedEnvironmentAttributeMap getEnvironmentAttributeMap();

    /**
     * Sets the fog color of the CustomBiome.
     *
     * @param fogColor the fog color of the CustomBiome
     * @since 0.0.5
     */
    @AsOf("1.1.0")
    void setFogColor(@Nullable Integer fogColor);

    /**
     * Sets the water color of the CustomBiome.
     *
     * @param waterColor the water color of the CustomBiome
     * @since 0.0.5
     */
    @AsOf("0.0.5")
    void setWaterColor(int waterColor);

    /**
     * Sets the water fog color of the CustomBiome.
     *
     * @param waterFogColor the water fog color of the CustomBiome
     * @since 0.0.5
     */
    @AsOf("1.1.0")
    void setWaterFogColor(@Nullable Integer waterFogColor);

    /**
     * Sets the sky color of the CustomBiome.
     *
     * @param skyColor the sky color of the CustomBiome
     * @since 0.0.5
     */
    @AsOf("1.1.0")
    void setSkyColor(@Nullable Integer skyColor);

    /**
     * Sets the foliage color of the CustomBiome.
     *
     * @param foliageColor the foliage color of the CustomBiome
     * @since 0.0.5
     */
    @AsOf("1.1.0")
    void setFoliageColor(@Nullable Integer foliageColor);

    /**
     * Sets the grass color of the CustomBiome.
     *
     * @param grassColor the grass color of the CustomBiome
     * @since 0.0.5
     */
    @AsOf("1.1.0")
    void setGrassColor(@Nullable Integer grassColor);

    /**
     * Sets the dry foliage color of the CustomBiome.
     *
     * @param dryFoliageColor the dry foliage color of the CustomBiome
     * @since 1.0.2
     */
    @AsOf("1.1.0")
    void setDryFoliageColor(@Nullable Integer dryFoliageColor);

    /**
     * Sets the GrassColorModifier of the CustomBiome.
     *
     * @param grassColorModifier the GrassColorModifier of the CustomBiome
     * @since 0.0.24
     */
    @AsOf("0.0.24")
    void setGrassColorModifier(@NotNull GrassColorModifier grassColorModifier);

    /**
     * Sets the ParticleRenderer of the CustomBiome.
     *
     * @param particleCatalog the ParticleRenderer of the CustomBiome
     * @since 1.1.0
     */
    @AsOf("1.1.0")
    void setParticleCatalog(@NotNull ParticleCatalog particleCatalog);

    /**
     * Sets the BlockReplacements of the CustomBiome.
     *
     * @apiNote Block replacements are only supported when rendering custom biomes to clients via the {@link PacketHandler}.
     * @param blockReplacements the BlockReplacements of the CustomBiome
     * @since 0.0.6
     */
    @AsOf("0.0.6")
    void setBlockReplacements(@NotNull BlockReplacement... blockReplacements);


    /**
     * Sets the WrappedEnvironmentAttributeMap of the CustomBiome.
     *
     * @param environmentAttributeMap the WrappedEnvironmentAttributeMap of the CustomBiome
     * @since 1.1.0
     */
    @AsOf("1.1.0")
    void setEnvironmentAttributeMap(@NotNull WrappedEnvironmentAttributeMap environmentAttributeMap);

    /**
     * Returns a new Builder instance with the properties of the CustomBiome.
     *
     * @return a new Builder instance with the properties of the CustomBiome
     * @since 0.0.5
     */
    @AsOf("0.0.5")
    Builder toBuilder();

    /**
     * Registers the CustomBiome to the biome registry.
     *
     * @since 0.0.2
     */
    @AsOf("0.0.2")
    void register();


    /**
     * Modifies the existing biome in the biome registry with the properties of this CustomBiome.
     *
     * @throws IllegalArgumentException if the biome does not already exist in the registry.
     * @apiNote This method can only change the properties of an existing biome on the <b>server</b>.
     * Clients which have already received the biome will not see any changes until they enter the reconfiguration phase
     * (e.g., by rejoining the server.)
     * @since 0.0.17
     */
    @AsOf("0.0.17")
    void modify();

    /**
     * Compares this CustomBiome to another CustomBiome to determine if they are similar.
     * Two CustomBiomes are considered similar if they have the same properties.
     *
     * @param otherBiome The other CustomBiome to compare to.
     * @return true if the CustomBiomes are similar, false otherwise.
     * @since 0.0.17
     */
    @AsOf("0.0.17")
    boolean isSimilar(@NotNull CustomBiome otherBiome);

    /**
     * This class is used to create a new CustomBiome object.
     * It provides methods to set the properties of the CustomBiome.
     *
     * @version 0.0.1
     */
    @AsOf("0.0.1")
    class Builder {

        private BiomeResourceKey resourceKey = null;
        private BiomeSettings settings = BiomeSettings.defaultSettings();

        private int waterColor = 0xF54927; // Meadow biome default water color

        private @Nullable Integer fogColor = -1;
        private @Nullable Integer waterFogColor = -1;
        private @Nullable Integer skyColor = -1;
        private @Nullable Integer foliageColor = -1;
        private @Nullable Integer grassColor = -1;
        private @Nullable Integer dryFoliageColor = -1;

        private GrassColorModifier grassColorModifier = GrassColorModifier.NONE;
        private ParticleCatalog particleCatalog = ParticleCatalog.EMPTY;
        private BlockReplacement[] blockReplacements = new BlockReplacement[0];
        private WrappedEnvironmentAttributeMap environmentAttributeMap = WrappedEnvironmentAttributeMap.EMPTY;


        /**
         * Formats a hexadecimal color string by removing the leading '#' if present.
         * This method is used to ensure that the color strings are in the correct format for parsing.
         *
         * @param color the color string to format
         * @return the formatted color string
         * @version 0.0.1
         */
        @AsOf("0.0.1")
        private String formatHex(@NotNull String color) {
            if (color.startsWith("#")) color = color.substring(1);
            return color;
        }

        /**
         * Parses a hexadecimal color string into an integer.
         *
         * @param color the color string to parse
         * @return the integer representation of the color
         * @since 0.0.1
         */
        @AsOf("1.0.2")
        private @Nullable Integer parseHex(@NotNull String color) {
            if (color.isEmpty()) return null;
            return Integer.parseInt(formatHex(color), 16);
        }

        /**
         * This method creates a new Builder object.
         *
         * @version 0.0.1
         */
        @AsOf("0.0.1")
        public Builder() {}

        /**
         * This method creates a new Builder object with the properties of the provided CustomBiome.
         *
         * @param biome The CustomBiome object to copy the properties from.
         * @version 0.0.5
         */
        @AsOf("0.0.5")
        public Builder(@NotNull CustomBiome biome) {
            this.resourceKey = biome.getResourceKey();
            this.settings = biome.getSettings();
            this.fogColor = biome.getFogColor();
            this.waterColor = biome.getWaterColor();
            this.waterFogColor = biome.getWaterFogColor();
            this.skyColor = biome.getSkyColor();
            this.foliageColor = biome.getFoliageColor();
            this.grassColor = biome.getGrassColor();
            this.dryFoliageColor = biome.getDryFoliageColor();
            this.grassColorModifier = biome.getGrassColorModifier();
            this.particleCatalog = biome.getParticleCatalog();
            this.blockReplacements = biome.getBlockReplacements();
            this.environmentAttributeMap = biome.getEnvironmentAttributeMap();
        }

        /**
         * This method sets the BiomeResourceKey property of the CustomBiome.
         *
         * @param resourceKey The BiomeResourceKey of the custom biome.
         * @version 0.0.1
         * @return The Builder object, for chaining method calls.
         */
        @AsOf("0.0.1")
        public @NotNull Builder resourceKey(@NotNull BiomeResourceKey resourceKey) {
            this.resourceKey = resourceKey;
            return this;
        }

        /**
         * This method sets the BiomeSettings property of the CustomBiome.
         *
         * @param settings The BiomeSettings of the custom biome.
         * @version 0.0.1
         * @return The Builder object, for chaining method calls.
         */
        @AsOf("0.0.1")
        public @NotNull Builder settings(@NotNull BiomeSettings settings) {
            this.settings = settings;
            return this;
        }

        /**
         * This method sets the fog color property of the CustomBiome.
         *
         * @param fogColor The fog color of the custom biome.
         * @version 0.0.1
         * @return The Builder object, for chaining method calls.
         */
        @AsOf("0.0.1")
        public @NotNull Builder fogColor(@NotNull String fogColor) {
            this.fogColor = parseHex(fogColor);
            return this;
        }

        /**
         * This method sets the fog color property of the CustomBiome.
         *
         * @param fogColor The fog color of the custom biome.
         * @version 0.0.1
         * @return The Builder object, for chaining method calls.
         */
        @AsOf("0.0.1")
        public @NotNull Builder fogColor(@NotNull Color fogColor) {
            this.fogColor = fogColor.asRGB();
            return this;
        }

        /**
         * This method sets the water color property of the CustomBiome.
         *
         * @param waterColor The water color of the custom biome.
         * @version 0.0.1
         * @return The Builder object, for chaining method calls.
         */
        @AsOf("0.0.1")
        public @NotNull Builder waterColor(@NotNull String waterColor) {
            this.waterColor = parseHex(waterColor);
            return this;
        }

        /**
         * This method sets the water color property of the CustomBiome.
         *
         * @param waterColor The water color of the custom biome.
         * @version 0.0.1
         * @return The Builder object, for chaining method calls.
         */
        @AsOf("0.0.1")
        public @NotNull Builder waterColor(@NotNull Color waterColor) {
            this.waterColor = waterColor.asRGB();
            return this;
        }

        /**
         * This method sets the water fog color property of the CustomBiome.
         *
         * @param waterFogColor The water fog color of the custom biome.
         * @version 0.0.1
         * @return The Builder object, for chaining method calls.
         */
        @AsOf("0.0.1")
        public @NotNull Builder waterFogColor(@NotNull String waterFogColor) {
            this.waterFogColor = parseHex(waterFogColor);
            return this;
        }

        /**
         * This method sets the water fog color property of the CustomBiome.
         *
         * @param waterFogColor The water fog color of the custom biome.
         * @version 0.0.1
         * @return The Builder object, for chaining method calls.
         */
        @AsOf("0.0.1")
        public @NotNull Builder waterFogColor(@NotNull Color waterFogColor) {
            this.waterFogColor = waterFogColor.asRGB();
            return this;
        }

        /**
         * This method sets the sky color property of the CustomBiome.
         *
         * @param skyColor The sky color of the custom biome.
         * @version 0.1.0
         * @return The Builder object, for chaining method calls.
         */
        @AsOf("0.0.1")
        public @NotNull Builder skyColor(@NotNull String skyColor) {
            this.skyColor = parseHex(skyColor);
            return this;
        }

        /**
         * This method sets the sky color property of the CustomBiome.
         *
         * @param skyColor The sky color of the custom biome.
         * @version 0.0.1
         * @return The Builder object, for chaining method calls.
         */
        @AsOf("0.0.1")
        public @NotNull Builder skyColor(@NotNull Color skyColor) {
            this.skyColor = skyColor.asRGB();
            return this;
        }

        /**
         * This method sets the foliage color property of the CustomBiome.
         *
         * @param foliageColor The foliage color of the custom biome.
         * @version 0.1.0
         * @return The Builder object, for chaining method calls.
         */
        @AsOf("0.0.1")
        public @NotNull Builder foliageColor(@NotNull String foliageColor) {
            this.foliageColor = parseHex(foliageColor);
            return this;
        }

        /**
         * This method sets the foliage color property of the CustomBiome.
         *
         * @param foliageColor The foliage color of the custom biome.
         * @version 0.0.1
         * @return The Builder object, for chaining method calls.
         */
        @AsOf("0.0.1")
        public @NotNull Builder foliageColor(@NotNull Color foliageColor) {
            this.foliageColor = foliageColor.asRGB();
            return this;
        }

        /**
         * This method sets the grass color property of the CustomBiome.
         *
         * @param grassColor The grass color of the custom biome.
         * @version 0.1.0
         * @return The Builder object, for chaining method calls.
         */
        @AsOf("0.0.1")
        public @NotNull Builder grassColor(@NotNull String grassColor) {
            this.grassColor = parseHex(grassColor);
            return this;
        }

        /**
         * This method sets the grass color property of the CustomBiome.
         *
         * @param grassColor The grass color of the custom biome.
         * @version 0.0.1
         * @return The Builder object, for chaining method calls.
         */
        @AsOf("0.0.1")
        public @NotNull Builder grassColor(@NotNull Color grassColor) {
            this.grassColor = grassColor.asRGB();
            return this;
        }

        /**
         * This method sets the dry foliage color property of the CustomBiome.
         *
         * @param dryFoliageColor The dry foliage color of the custom biome.
         * @since 1.0.2
         * @return The Builder object, for chaining method calls.
         */
        @AsOf("1.2.0")
        public @NotNull Builder dryFoliageColor(@NotNull String dryFoliageColor) {
            this.dryFoliageColor = parseHex(dryFoliageColor);
            return this;
        }

        /**
         * This method sets the dry foliage color property of the CustomBiome.
         *
         * @param dryFoliageColor The dry foliage color of the custom biome.
         * @since 1.2.0
         * @return The Builder object, for chaining method calls.
         */
        @AsOf("1.2.0")
        public @NotNull Builder dryFoliageColor(@NotNull Color dryFoliageColor) {
            this.dryFoliageColor = dryFoliageColor.asRGB();
            return this;
        }

        /**
         * This method sets the grass color modifier property of the CustomBiome.
         *
         * @param grassColorModifier The grass color modifier of the custom biome.
         * @version 0.0.24
         * @return The Builder object, for chaining method calls.
         */
        @AsOf("0.0.24")
        public @NotNull Builder grassColorModifier(@NotNull GrassColorModifier grassColorModifier) {
            this.grassColorModifier = grassColorModifier;
            return this;
        }

        /**
         * This method sets the particle catalog property of the CustomBiome.
         *
         * @param particleCatalog The particle catalog of the custom biome.
         * @since 1.1.0
         * @return The Builder object, for chaining method calls.
         */
        @AsOf("1.1.0")
        public @NotNull Builder particleCatalog(@NotNull ParticleCatalog particleCatalog) {
            this.particleCatalog = particleCatalog;
            return this;
        }

        /**
         * This method sets the block replacements property of the CustomBiome.
         *
         * @apiNote Block replacements are only supported when rendering custom biomes to clients via the {@link PacketHandler}.
         * @param blockReplacements The block replacements of the custom biome.
         * @since  0.0.6
         * @return The Builder object, for chaining method calls.
         */
        @AsOf("0.0.6")
        public @NotNull Builder blockReplacements(@NotNull BlockReplacement... blockReplacements) {
            this.blockReplacements = blockReplacements;
            return this;
        }


        /**
         * This method sets the environment attribute map property of the CustomBiome.
         *
         * @param environmentAttributeMap The environment attribute map of the custom biome.
         * @since 1.1.0
         * @return The Builder object, for chaining method calls.
         */
        @AsOf("1.1.0")
        public @NotNull Builder environmentAttributeMap(@NotNull WrappedEnvironmentAttributeMap environmentAttributeMap) {
            this.environmentAttributeMap = environmentAttributeMap;
            return this;
        }

        /**
         * This method creates a new CustomBiome object with the properties set in the Builder.
         *
         * @since 0.0.1
         * @return a new CustomBiome object.
         */
        @AsOf("1.1.0")
        public @NotNull CustomBiome build() {
            Preconditions.checkArgument(resourceKey != null, "Resource key must be set");
            Preconditions.checkArgument(settings != null, "Settings must be set");

            return new CustomBiomeImpl(
                    resourceKey,
                    settings,
                    fogColor,
                    waterColor,
                    waterFogColor,
                    skyColor,
                    foliageColor,
                    grassColor,
                    dryFoliageColor,
                    grassColorModifier,
                    particleCatalog,
                    blockReplacements,
                    environmentAttributeMap
            );
        }

    }

}
