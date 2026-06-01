package me.outspending.biomesapi.biome;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.renderer.packet.PacketHandler;
import me.outspending.biomesapi.renderer.packet.data.BlockReplacement;
import me.outspending.biomesapi.renderer.packet.data.PhonyCustomBiome;
import me.outspending.biomesapi.wrapper.BiomeSettings;
import me.outspending.biomesapi.wrapper.environment.GrassColorModifier;
import me.outspending.biomesapi.wrapper.environment.attribute.IntColorSupplier;
import me.outspending.biomesapi.wrapper.environment.attribute.WrappedEnvironmentAttributeMap;
import me.outspending.biomesapi.wrapper.environment.attribute.WrappedEnvironmentAttributeSupplier;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleCatalog;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleData;
import me.outspending.biomesapi.wrapper.environment.particle.WrappedParticleTypes;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * This interface represents a custom biome in the BiomesAPI.
 * It provides methods to retrieve and modify the properties of the custom biome.
 *
 * @version 2.2.0
 * @since 0.0.1
 * @author Outspending
 */
@AsOf("2.2.0")
public interface CustomBiome extends Biome {

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
     * Returns a new instance of the Builder class with the provided resource key.
     * @param resourceKey the BiomeResourceKey to set for the CustomBiome being built
     * @return a new Builder instance
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    static @NotNull Builder builder(@NotNull BiomeResourceKey resourceKey) {
        Preconditions.checkNotNull(resourceKey, "Resource key cannot be null.");
        return new Builder().resourceKey(resourceKey);
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
     * @return the CustomBiome with the updated fog color
     * @since 0.0.5
     */
    @AsOf("1.1.0")
    CustomBiome setFogColor(@Nullable Integer fogColor);

    /**
     * Sets the water color of the CustomBiome.
     *
     * @param waterColor the water color of the CustomBiome
     * @return the CustomBiome with the updated water color
     * @since 0.0.5
     */
    @AsOf("0.0.5")
    CustomBiome setWaterColor(int waterColor);

    /**
     * Sets the water fog color of the CustomBiome.
     * @param waterColor the water fog color of the CustomBiome
     * @return the CustomBiome with the updated water fog color
     * @since 2.2.0
     */
    @AsOf("2.2.0")
    default CustomBiome setWaterColor(@NotNull String waterColor) {
        @Nullable Integer parsedColor = parseHex(waterColor);
        Preconditions.checkNotNull(parsedColor, "Invalid water fog color: %s", waterColor);
        return setWaterColor(parsedColor);
    }

    /**
     * Sets the water fog color of the CustomBiome.
     *
     * @param waterFogColor the water fog color of the CustomBiome
     * @return the CustomBiome with the updated water fog color
     * @since 0.0.5
     */
    @AsOf("1.1.0")
    CustomBiome setWaterFogColor(@Nullable Integer waterFogColor);

    /**
     * Sets the water fog color of the CustomBiome.
     * @param waterFogColor the water fog color of the CustomBiome
     * @return the CustomBiome with the updated water fog color
     * @since 2.2.0
     */
    @AsOf("2.2.0")
    default CustomBiome setWaterFogColor(@Nullable String waterFogColor) {
        return setWaterFogColor(parseHex(waterFogColor));
    }

    /**
     * Sets the sky color of the CustomBiome.
     *
     * @param skyColor the sky color of the CustomBiome
     * @return the CustomBiome with the updated sky color
     * @since 0.0.5
     */
    @AsOf("1.1.0")
    CustomBiome setSkyColor(@Nullable Integer skyColor);

    /**
     * Sets the sky color of the CustomBiome.
     * @param skyColor the sky color of the CustomBiome
     * @return the CustomBiome with the updated sky color
     * @since 2.2.0
     */
    @AsOf("2.2.0")
    default CustomBiome setSkyColor(@Nullable String skyColor) {
        return setSkyColor(parseHex(skyColor));
    }

    /**
     * Sets the foliage color of the CustomBiome.
     *
     * @param foliageColor the foliage color of the CustomBiome
     * @since 0.0.5
     * @return the CustomBiome with the updated foliage color
     */
    @AsOf("1.1.0")
    CustomBiome setFoliageColor(@Nullable Integer foliageColor);

    /**
     * Sets the foliage color of the CustomBiome.
     * @param foliageColor the foliage color of the CustomBiome
     * @return the CustomBiome with the updated foliage color
     * @since 2.2.0
     */
    @AsOf("2.2.0")
    default CustomBiome setFoliageColor(@Nullable String foliageColor) {
        return setFoliageColor(parseHex(foliageColor));
    }

    /**
     * Sets the grass color of the CustomBiome.
     *
     * @param grassColor the grass color of the CustomBiome
     * @return the CustomBiome with the updated grass color
     * @since 0.0.5
     */
    @AsOf("1.1.0")
    CustomBiome setGrassColor(@Nullable Integer grassColor);

    /**
     * Sets the grass color of the CustomBiome.
     * @param grassColor the grass color of the CustomBiome
     * @return the CustomBiome with the updated grass color
     * @since 2.2.0
     */
    @AsOf("2.2.0")
    default CustomBiome setGrassColor(@Nullable String grassColor) {
        return setGrassColor(parseHex(grassColor));
    }

    /**
     * Sets the dry foliage color of the CustomBiome.
     *
     * @param dryFoliageColor the dry foliage color of the CustomBiome
     * @since 1.0.2
     * @return the CustomBiome with the updated dry foliage color
     */
    @AsOf("1.1.0")
    CustomBiome setDryFoliageColor(@Nullable Integer dryFoliageColor);

    /**
     * Sets the dry foliage color of the CustomBiome.
     * @param dryFoliageColor the dry foliage color of the CustomBiome
     * @return the CustomBiome with the updated dry foliage color
     * @since 2.2.0
     */
    @AsOf("2.2.0")
    default CustomBiome setDryFoliageColor(@Nullable String dryFoliageColor) {
        return setDryFoliageColor(parseHex(dryFoliageColor));
    }

    /**
     * Sets the GrassColorModifier of the CustomBiome.
     *
     * @param grassColorModifier the GrassColorModifier of the CustomBiome
     * @return the CustomBiome with the updated GrassColorModifier
     * @since 0.0.24
     */
    @AsOf("0.0.24")
    CustomBiome setGrassColorModifier(@NotNull GrassColorModifier grassColorModifier);

    /**
     * Sets the ParticleRenderer of the CustomBiome.
     *
     * @param particleCatalog the ParticleRenderer of the CustomBiome
     * @return the CustomBiome with the updated ParticleRenderer
     * @since 1.1.0
     */
    @AsOf("1.1.0")
    CustomBiome setParticleCatalog(@NotNull ParticleCatalog particleCatalog);

    /**
     * Sets the BlockReplacements of the CustomBiome.
     *
     * @apiNote Block replacements are only supported when rendering custom biomes to clients via the {@link PacketHandler}.
     * @param blockReplacements the BlockReplacements of the CustomBiome
     * @since 0.0.6
     * @return the CustomBiome with the updated BlockReplacements
     */
    @AsOf("0.0.6")
    CustomBiome setBlockReplacements(@NotNull BlockReplacement... blockReplacements);


    /**
     * Sets the WrappedEnvironmentAttributeMap of the CustomBiome.
     *
     * @param environmentAttributeMap the WrappedEnvironmentAttributeMap of the CustomBiome
     * @return the CustomBiome with the updated WrappedEnvironmentAttributeMap
     * @since 2.2.0
     */
    @AsOf("2.2.0")
    CustomBiome setAttributes(@NotNull WrappedEnvironmentAttributeMap environmentAttributeMap);

    /**
     * Sets the WrappedEnvironmentAttributeMap of the CustomBiome.
     *
     * @param environmentAttributeMap the WrappedEnvironmentAttributeMap of the CustomBiome
     * @return the CustomBiome with the updated WrappedEnvironmentAttributeMap
     * @since 1.1.0
     * @deprecated Use {@link #setAttributes(WrappedEnvironmentAttributeMap)} instead.
     */
    @AsOf("1.1.0")
    @Deprecated
    default CustomBiome setEnvironmentAttributeMap(@NotNull WrappedEnvironmentAttributeMap environmentAttributeMap) {
        return setAttributes(environmentAttributeMap);
    }


    /**
     * Registers the CustomBiome to the biome registry.
     *
     * @return the registered CustomBiome
     * @since 0.0.2
     */
    @AsOf("0.0.2")
    CustomBiome register();


    /**
     * Modifies the existing biome in the biome registry with the properties of this CustomBiome.
     *
     * @since 0.0.17
     * @throws IllegalArgumentException if the biome does not already exist in the registry.
     * @apiNote This method can only change the properties of an existing biome on the <b>server</b>.
     * Clients which have already received the biome will not see any changes until they enter the reconfiguration phase
     * (e.g., by rejoining the server.)
     */
    @AsOf("0.0.17")
    CustomBiome modify();

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
     * Returns a new Builder instance with the properties of the CustomBiome.
     *
     * @return a new Builder instance with the properties of the CustomBiome
     * @since 0.0.5
     */
    @AsOf("0.0.5")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Returns a new Builder instance with the properties of the CustomBiome.
     *
     * @return a new Builder instance with the properties of the CustomBiome
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    default Builder asBuilder() {
        return new Builder(this);
    }

    /**
     * Converts this CustomBiome to a PhonyCustomBiome for packet sending purposes.
     *
     * @see PacketHandler
     * @see PhonyCustomBiome
     * @since 2.1.0
     * @return a PhonyCustomBiome representation of this CustomBiome
     */
    default PhonyCustomBiome toPhony() {
        if (!isRegistered()) {
            register();
        }
        return asPhony().build();
    }

    /**
     * Converts this CustomBiome to a PhonyCustomBiome for packet sending purposes.
     *
     * @see PacketHandler
     * @see PhonyCustomBiome
     * @since 2.1.0
     * @return a PhonyCustomBiome representation of this CustomBiome
     */
    default PhonyCustomBiome.Builder asPhony() {
        if (!isRegistered()) {
            register();
        }
        return PhonyCustomBiome.builder().setCustomBiome(this);
    }

    /**
     * Checks if the CustomBiome is registered in the biome registry.
     * @return true if the CustomBiome is registered, false otherwise.
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    default boolean isRegistered() {
        return BiomeHandler.isBiome(getResourceKey());
    }

    /**
     * Parses a hexadecimal color string into an integer.
     *
     * @param color the color string to parse
     * @return the integer representation of the color
     * @since 0.0.1
     */
    @AsOf("1.0.2")
    private static @Nullable Integer parseHex(@Nullable String color) {
        if (color == null || color.isEmpty()) return null;
        final String formatted = color.startsWith("#") ? color.substring(1) : color;
        return Integer.parseInt(formatted, 16);
    }

    /**
     * This class is used to create a new CustomBiome object.
     * It provides methods to set the properties of the CustomBiome.
     *
     * @version 2.1.0
     * @since 0.0.1
     * @author Outspending
     */
    @AsOf("2.1.0")
    class Builder {

        private BiomeResourceKey resourceKey = null;
        private BiomeSettings settings = BiomeSettings.defaultSettings();

        private int waterColor = 0x3F75C4;

        private @Nullable Integer fogColor = null;
        private @Nullable Integer waterFogColor = null;
        private @Nullable Integer skyColor = null;
        private @Nullable Integer foliageColor = null;
        private @Nullable Integer grassColor = null;
        private @Nullable Integer dryFoliageColor = null;

        private GrassColorModifier grassColorModifier = GrassColorModifier.NONE;
        private ParticleCatalog particleCatalog = ParticleCatalog.EMPTY;
        private BlockReplacement[] blockReplacements = new BlockReplacement[0];
        private WrappedEnvironmentAttributeMap environmentAttributeMap = WrappedEnvironmentAttributeMap.EMPTY;

        /**
         * This method creates a new Builder object.
         *
         * @since 0.0.1
         */
        @AsOf("0.0.1")
        public Builder() {}

        /**
         * This method creates a new Builder object with the properties of the provided CustomBiome.
         *
         * @param biome The CustomBiome object to copy the properties from.
         * @since 0.0.5
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
         * @since 0.0.1
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
         * @since 0.0.1
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
         * @since 0.0.1
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
         * @since 0.0.1
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
         * @since 0.0.1
         * @return The Builder object, for chaining method calls.
         */
        @AsOf("0.0.1")
        public @NotNull Builder waterColor(@NotNull String waterColor) {
            @Nullable Integer parsedColor = parseHex(waterColor);
            if (parsedColor != null) this.waterColor = parsedColor;
            return this;
        }

        /**
         * This method sets the water color property of the CustomBiome.
         *
         * @param waterColor The water color of the custom biome.
         * @since 0.0.1
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
         * @since 0.0.1
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
         * @since 0.0.1
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
         * @since 0.1.0
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
         * @since 0.0.1
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
         * @since 0.1.0
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
         * @since 0.0.1
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
         * @since 0.1.0
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
         * @since 0.0.1
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
         * @since 0.0.24
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
         * Adds a particle to the particle catalog of the CustomBiome.
         * @param particleType The particle type to add.
         * @param probability The probability of the particle being spawned.
         * @return The Builder object, for chaining method calls.
         * @since 2.1.0
         */
        @AsOf("2.1.0")
        public @NotNull Builder ambientParticle(@NotNull WrappedParticleTypes particleType, float probability) {
            this.particleCatalog = this.particleCatalog.with(particleType, probability);
            return this;
        }

        /**
         * Adds a particle to the particle catalog of the CustomBiome.
         * @param particleType The particle type to add.
         * @param probability The probability of the particle being spawned.
         * @param data The data of the particle.
         * @return The Builder object, for chaining method calls.
         * @param <T> The type of the particle data.
         * @since 2.1.0
         */
        @AsOf("2.1.0")
        public @NotNull <T extends ParticleData<T>> Builder ambientParticle(@NotNull WrappedParticleTypes particleType, float probability, @Nullable T data) {
            this.particleCatalog = this.particleCatalog.with(particleType, probability, data);
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
         * This method sets the block replacements property of the CustomBiome.
         *
         * @apiNote Block replacements are only supported when rendering custom biomes to clients via the {@link PacketHandler}.
         * @param blockReplacements The block replacements of the custom biome.
         * @since 2.1.0
         * @return The Builder object, for chaining method calls.
         */
        @AsOf("2.1.0")
        public @NotNull Builder blockReplacements(@NotNull Collection<BlockReplacement> blockReplacements) {
            this.blockReplacements = blockReplacements.toArray(new BlockReplacement[0]);
            return this;
        }


        /**
         * This method adds a block to to the block replacements property of the CustomBiome.
         * @apiNote Block replacements are only supported when rendering custom biomes to clients via the {@link PacketHandler}.
         * @param from The from block material to be replaced.
         * @param to The to block material.
         * @return The Builder object, for chaining method calls.
         * @since 2.1.0
         */
        @AsOf("2.1.0")
        public @NotNull Builder replace(@NotNull Material from, @NotNull Material to) {
            BlockReplacement newReplacement = new BlockReplacement(from, to);
            BlockReplacement[] newArray = new BlockReplacement[blockReplacements.length + 1];
            System.arraycopy(blockReplacements, 0, newArray, 0, blockReplacements.length);
            newArray[newArray.length - 1] = newReplacement;
            this.blockReplacements = newArray;
            return this;
        }

        /**
         * This method adds a block replacement to the block replacements property of the CustomBiome.
         * @apiNote Block replacements are only supported when rendering custom biomes to clients via the {@link PacketHandler}.
         * @param replacement The block replacement to be added.
         * @return The Builder object, for chaining method calls.
         * @since 2.1.0
         */
        @AsOf("2.1.0")
        public @NotNull Builder replace(@NotNull BlockReplacement replacement) {
            BlockReplacement[] newArray = new BlockReplacement[blockReplacements.length + 1];
            System.arraycopy(blockReplacements, 0, newArray, 0, blockReplacements.length);
            newArray[newArray.length - 1] = replacement;
            this.blockReplacements = newArray;
            return this;
        }

        /**
         * This method sets the environment attribute map property of the CustomBiome.
         * Replaces any attributes previously added via {@link #setAttribute}.
         *
         * @param environmentAttributeMap The environment attribute map of the custom biome.
         * @return The Builder object, for chaining method calls.
         * @since 1.1.0
         * @deprecated Use {@link #setAttributes} instead.
         */
        @AsOf("1.1.0")
        @Deprecated(forRemoval = true, since = "2.1.0")
        public @NotNull Builder environmentAttributeMap(@NotNull WrappedEnvironmentAttributeMap environmentAttributeMap) {
            setAttributes(environmentAttributeMap);
            return this;
        }

        /**
         * This method sets the environment attribute map property of the CustomBiome.
         * Replaces any attributes previously added via {@link #setAttribute}.
         *
         * @param environmentAttributeMap The environment attribute map of the custom biome.
         * @return The Builder object, for chaining method calls.
         * @since 2.1.0
         */
        @AsOf("2.1.0")
        public @NotNull Builder setAttributes(@NotNull WrappedEnvironmentAttributeMap environmentAttributeMap) {
            this.environmentAttributeMap = environmentAttributeMap;
            return this;
        }

        /**
         * Adds an environment attribute to the environment attribute map property of the CustomBiome.
         *
         * @param supplier The supplier function that returns the environment attribute.
         * @param value The value of the environment attribute.
         * @param <T> The type of the environment attribute.
         * @param <K> The value type of the environment attribute.
         * @return The Builder object, for chaining method calls.
         * @since 2.1.0
         */
        @AsOf("2.1.0")
        public @NotNull <T, K> Builder setAttribute(@NotNull WrappedEnvironmentAttributeSupplier<T, K> supplier, @NotNull K value) {
            this.environmentAttributeMap = this.environmentAttributeMap.with(supplier, value);
            return this;
        }

        /**
         * Adds an environment attribute to the environment attribute map property of the CustomBiome.
         *
         * @param supplier The color attribute supplier.
         * @param hex The hex value (e.g. {@code "#FF10F0"}).
         * @return The Builder object, for chaining method calls.
         * @since 2.1.0
         */
        @AsOf("2.1.0")
        public @NotNull Builder setAttribute(@NotNull IntColorSupplier supplier, @NotNull String hex) {
            this.environmentAttributeMap = this.environmentAttributeMap.with(supplier, hex);
            return this;
        }

        /**
         * This method creates a new CustomBiome object with the properties set in the Builder.
         *
         * @since 0.0.1
         * @throws IllegalArgumentException if the resource key or settings are not set.
         * @return a new CustomBiome object.
         */
        @AsOf("1.1.0")
        public @NotNull CustomBiome build() {
            Preconditions.checkArgument(resourceKey != null, "Resource key must be set");
            Preconditions.checkArgument(settings != null, "Settings must be set");

            return new CustomBiomeImpl(
                    resourceKey,
                    settings,
                    waterColor,
                    fogColor,
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

        /**
         * This method registers the CustomBiome in the biome registry.
         *
         * @return The registered CustomBiome.
         * @since 2.1.0
         */
        @AsOf("2.1.0")
        public @NotNull CustomBiome register() {
            return build().register();
        }

    }

}
