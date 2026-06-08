package me.outspending.biomesapi.biome;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.biome.impl.AbstractBiomeImpl;
import me.outspending.biomesapi.registry.BiomeRegistry;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.wrapper.BiomeSettings;
import me.outspending.biomesapi.wrapper.entity.BiomeSpawner;
import me.outspending.biomesapi.wrapper.environment.GrassColorModifier;
import me.outspending.biomesapi.wrapper.environment.attribute.IntColorSupplier;
import me.outspending.biomesapi.wrapper.environment.attribute.WrappedEnvironmentAttributeMap;
import me.outspending.biomesapi.wrapper.environment.attribute.WrappedEnvironmentAttributeSupplier;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleCatalog;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleData;
import me.outspending.biomesapi.wrapper.environment.particle.WrappedParticleTypes;
import me.outspending.biomesapi.wrapper.worldgen.BiomeGenerationSettings;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Represents an abstract biome in the game.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public interface AbstractBiome extends Keyed {

    /**
     * @return the NamespacedKey of the AbstractBiome.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    NamespacedKey getKey();

    /**
     * Returns the Bukkit Biome of the AbstractBiome.
     *
     * @return the Bukkit Biome of the AbstractBiome
     * @since 0.0.6
     */
    @AsOf("0.0.6")
    Biome toBukkitBiome();

    /**
     * Returns the BiomeResourceKey of the AbstractBiome.
     *
     * @return the BiomeResourceKey of the AbstractBiome
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    BiomeResourceKey getResourceKey();

    /**
     * Returns the BiomeSettings of the AbstractBiome.
     *
     * @return the BiomeSettings of the AbstractBiome
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    BiomeSettings getSettings();

    /**
     * Returns the water color of the AbstractBiome.
     *
     * @return the water color of the AbstractBiome
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    int getWaterColor();

    /**
     * Returns the fog color of the AbstractBiome.
     *
     * @return the fog color of the AbstractBiome
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    @Nullable Integer getFogColor();

    /**
     * Returns the water fog color of the AbstractBiome.
     *
     * @return the water fog color of the AbstractBiome
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    @Nullable Integer getWaterFogColor();

    /**
     * Returns the sky color of the AbstractBiome.
     *
     * @return the sky color of the AbstractBiome
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    @Nullable Integer getSkyColor();

    /**
     * Returns the foliage color of the AbstractBiome.
     *
     * @return the foliage color of the AbstractBiome
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    @Nullable Integer getFoliageColor();

    /**
     * Returns the grass color of the AbstractBiome.
     *
     * @return the grass color of the AbstractBiome
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    @Nullable Integer getGrassColor();

    /**
     * Returns the dry foliage color of the AbstractBiome.
     *
     * @return the dry foliage color of the AbstractBiome
     * @since 1.0.2
     */
    @AsOf("1.0.2")
    @Nullable Integer getDryFoliageColor();

    /**
     * Returns the GrassColorModifier of the AbstractBiome.
     *
     * @return the GrassColorModifier of the AbstractBiome
     * @since 0.0.24
     */
    @AsOf("0.0.24")
    GrassColorModifier getGrassColorModifier();

    /**
     * Returns the ParticleRenderer of the AbstractBiome.
     *
     * @return the ParticleRenderer of the AbstractBiome
     * @since 1.1.0
     */
    @AsOf("1.1.0")
    ParticleCatalog getParticleCatalog();

    /**
     * Returns the WrappedEnvironmentAttributeMap of the AbstractBiome.
     * 
     * @return the WrappedEnvironmentAttributeMap of the AbstractBiome
     * @since 1.1.0
     */
    @AsOf("1.1.0")
    WrappedEnvironmentAttributeMap getAttributes();

    /**
     * Returns the BiomeSpawner of the AbstractBiome.
     * 
     * @return the BiomeSpawner of the AbstractBiome
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    @Nullable BiomeSpawner getBiomeSpawner();

    /**
     * Returns the BiomeGenerationSettings of the AbstractBiome.
     *
     * @return the BiomeGenerationSettings of the AbstractBiome
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    @Nullable BiomeGenerationSettings getGenerationSettings();


    /**
     * Sets the BiomeSettings of the AbstractBiome.
     * @param settings the BiomeSettings of the AbstractBiome
     * @return the AbstractBiome with the updated BiomeSettings
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    AbstractBiome setSettings(BiomeSettings settings);

    /**
     * Sets the water color of the AbstractBiome.
     *
     * @param waterColor the water color of the AbstractBiome
     * @return the AbstractBiome with the updated water color
     * @since 0.0.5
     */
    @AsOf("0.0.5")
    AbstractBiome setWaterColor(int waterColor);

    /**
     * Sets the water fog color of the AbstractBiome.
     * 
     * @param waterColor the water fog color of the AbstractBiome
     * @return the AbstractBiome with the updated water fog color
     * @since 2.2.0
     */
    @AsOf("2.2.0")
    default AbstractBiome setWaterColor(String waterColor) {
        @Nullable Integer parsedColor = parseHex(waterColor);
        Preconditions.checkNotNull(parsedColor, "Invalid water fog color: %s", waterColor);
        return setWaterColor(parsedColor);
    }

    /**
     * Sets the fog color of the AbstractBiome.
     *
     * @param fogColor the fog color of the AbstractBiome
     * @return the AbstractBiome with the updated fog color
     * @since 0.0.5
     */
    @AsOf("1.1.0")
    AbstractBiome setFogColor(@Nullable Integer fogColor);

    /**
     * Sets the fog color of the AbstractBiome.
     * @param fogColor the fog color of the AbstractBiome
     * @return the AbstractBiome with the updated fog color
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    default AbstractBiome setFogColor(@Nullable String fogColor) {
        return setFogColor(parseHex(fogColor));
    }

    /**
     * Sets the water fog color of the AbstractBiome.
     *
     * @param waterFogColor the water fog color of the AbstractBiome
     * @return the AbstractBiome with the updated water fog color
     * @since 0.0.5
     */
    @AsOf("1.1.0")
    AbstractBiome setWaterFogColor(@Nullable Integer waterFogColor);

    /**
     * Sets the water fog color of the AbstractBiome.
     * @param waterFogColor the water fog color of the AbstractBiome
     * @return the AbstractBiome with the updated water fog color
     * @since 2.2.0
     */
    @AsOf("2.2.0")
    default AbstractBiome setWaterFogColor(@Nullable String waterFogColor) {
        return setWaterFogColor(parseHex(waterFogColor));
    }

    /**
     * Sets the sky color of the AbstractBiome.
     *
     * @param skyColor the sky color of the AbstractBiome
     * @return the AbstractBiome with the updated sky color
     * @since 0.0.5
     */
    @AsOf("1.1.0")
    AbstractBiome setSkyColor(@Nullable Integer skyColor);

    /**
     * Sets the sky color of the AbstractBiome.
     * @param skyColor the sky color of the AbstractBiome
     * @return the AbstractBiome with the updated sky color
     * @since 2.2.0
     */
    @AsOf("2.2.0")
    default AbstractBiome setSkyColor(@Nullable String skyColor) {
        return setSkyColor(parseHex(skyColor));
    }

    /**
     * Sets the foliage color of the AbstractBiome.
     *
     * @param foliageColor the foliage color of the AbstractBiome
     * @since 0.0.5
     * @return the AbstractBiome with the updated foliage color
     */
    @AsOf("1.1.0")
    AbstractBiome setFoliageColor(@Nullable Integer foliageColor);

    /**
     * Sets the foliage color of the AbstractBiome.
     * @param foliageColor the foliage color of the AbstractBiome
     * @return the AbstractBiome with the updated foliage color
     * @since 2.2.0
     */
    @AsOf("2.2.0")
    default AbstractBiome setFoliageColor(@Nullable String foliageColor) {
        return setFoliageColor(parseHex(foliageColor));
    }

    /**
     * Sets the grass color of the AbstractBiome.
     *
     * @param grassColor the grass color of the AbstractBiome
     * @return the AbstractBiome with the updated grass color
     * @since 0.0.5
     */
    @AsOf("1.1.0")
    AbstractBiome setGrassColor(@Nullable Integer grassColor);

    /**
     * Sets the grass color of the AbstractBiome.
     * @param grassColor the grass color of the AbstractBiome
     * @return the AbstractBiome with the updated grass color
     * @since 2.2.0
     */
    @AsOf("2.2.0")
    default AbstractBiome setGrassColor(@Nullable String grassColor) {
        return setGrassColor(parseHex(grassColor));
    }

    /**
     * Sets the dry foliage color of the AbstractBiome.
     *
     * @param dryFoliageColor the dry foliage color of the AbstractBiome
     * @since 1.0.2
     * @return the AbstractBiome with the updated dry foliage color
     */
    @AsOf("1.1.0")
    AbstractBiome setDryFoliageColor(@Nullable Integer dryFoliageColor);

    /**
     * Sets the dry foliage color of the AbstractBiome.
     * @param dryFoliageColor the dry foliage color of the AbstractBiome
     * @return the AbstractBiome with the updated dry foliage color
     * @since 2.2.0
     */
    @AsOf("2.2.0")
    default AbstractBiome setDryFoliageColor(@Nullable String dryFoliageColor) {
        return setDryFoliageColor(parseHex(dryFoliageColor));
    }

    /**
     * Sets the GrassColorModifier of the AbstractBiome.
     *
     * @param grassColorModifier the GrassColorModifier of the AbstractBiome
     * @return the AbstractBiome with the updated GrassColorModifier
     * @since 0.0.24
     */
    @AsOf("0.0.24")
    AbstractBiome setGrassColorModifier(GrassColorModifier grassColorModifier);

    /**
     * Sets the ParticleRenderer of the AbstractBiome.
     *
     * @param particleCatalog the ParticleRenderer of the AbstractBiome
     * @return the AbstractBiome with the updated ParticleRenderer
     * @since 1.1.0
     */
    @AsOf("1.1.0")
    AbstractBiome setParticleCatalog(ParticleCatalog particleCatalog);

    /**
     * Sets the WrappedEnvironmentAttributeMap of the AbstractBiome.
     *
     * @param attributes the WrappedEnvironmentAttributeMap of the AbstractBiome
     * @return the AbstractBiome with the updated WrappedEnvironmentAttributeMap
     * @since 2.2.0
     */
    @AsOf("2.2.0")
    AbstractBiome setAttributes(WrappedEnvironmentAttributeMap attributes);

    /**
     * Sets the BiomeSpawner of the AbstractBiome.
     *
     * @param biomeSpawner the BiomeSpawner of the AbstractBiome
     * @return the AbstractBiome with the updated BiomeSpawner
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    AbstractBiome setBiomeSpawner(@Nullable BiomeSpawner biomeSpawner);

    /**
     * Sets the BiomeGenerationSettings of the AbstractBiome.
     *
     * @param generationSettings the BiomeGenerationSettings of the AbstractBiome
     * @return the AbstractBiome with the updated BiomeGenerationSettings
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    AbstractBiome setGenerationSettings(@Nullable BiomeGenerationSettings generationSettings);

    /**
     * Compares this AbstractBiome to another AbstractBiome to determine if they are similar.
     * Two AbstractBiomes are considered similar if they have the same properties.
     *
     * @param otherBiome The other AbstractBiome to compare to.
     * @return true if the AbstractBiomes are similar, false otherwise.
     * @since 0.0.17
     */
    @AsOf("0.0.17")
    boolean isSimilar(AbstractBiome otherBiome);

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
    default AbstractBiome modify() {
        BiomeRegistry.registry().modify(this);
        return this;
    }
    
    @Override
    default Key key() {
        return getResourceKey().key();
    }

    /**
     * Parses a hexadecimal color string into an integer.
     *
     * @param color the color string to parse
     * @return the integer representation of the color
     * @since 0.0.1
     */
    @AsOf("1.0.2")
    static @Nullable Integer parseHex(@Nullable String color) {
        if (color == null || color.isEmpty()) return null;
        final String formatted = color.startsWith("#") ? color.substring(1) : color;
        return Integer.parseInt(formatted, 16);
    }

    /**
     * @return a new, empty {@link Builder}.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * @param resourceKey the BiomeResourceKey to seed the builder with
     * @return a new {@link Builder} seeded with the given resource key.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static Builder builder(BiomeResourceKey resourceKey) {
        Preconditions.checkNotNull(resourceKey, "Resource key cannot be null.");
        return new Builder().resourceKey(resourceKey);
    }

    /**
     * Builder for {@link AbstractBiome}. Configures every property the base biome exposes.
     *
     * @since 2.3.0
     * @author Jsinco
     */
    @AsOf("2.3.0")
    final class Builder {

        private @Nullable BiomeResourceKey resourceKey = null;
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
        private WrappedEnvironmentAttributeMap attributeMap = WrappedEnvironmentAttributeMap.EMPTY;
        private @Nullable BiomeSpawner biomeSpawner = null;
        private @Nullable BiomeGenerationSettings generationSettings = null;

        @AsOf("2.3.0")
        public Builder() {}

        /**
         * Creates a builder seeded with the properties of an existing AbstractBiome.
         *
         * @param biome the AbstractBiome to copy from
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder(AbstractBiome biome) {
            this.resourceKey = biome.getResourceKey();
            this.settings = biome.getSettings();
            this.waterColor = biome.getWaterColor();
            this.fogColor = biome.getFogColor();
            this.waterFogColor = biome.getWaterFogColor();
            this.skyColor = biome.getSkyColor();
            this.foliageColor = biome.getFoliageColor();
            this.grassColor = biome.getGrassColor();
            this.dryFoliageColor = biome.getDryFoliageColor();
            this.grassColorModifier = biome.getGrassColorModifier();
            this.particleCatalog = biome.getParticleCatalog();
            this.attributeMap = biome.getAttributes();
            this.biomeSpawner = biome.getBiomeSpawner();
            this.generationSettings = biome.getGenerationSettings();
        }

        /**
         * @param resourceKey the BiomeResourceKey of the biome
         * @return this builder, for chaining
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder resourceKey(BiomeResourceKey resourceKey) {
            this.resourceKey = resourceKey;
            return this;
        }

        /**
         * @param settings the BiomeSettings of the biome
         * @return this builder, for chaining
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder settings(BiomeSettings settings) {
            this.settings = settings;
            return this;
        }

        @AsOf("2.3.0")
        public Builder fogColor(String fogColor) {
            this.fogColor = AbstractBiome.parseHex(fogColor);
            return this;
        }

        @AsOf("2.3.0")
        public Builder fogColor(Color fogColor) {
            this.fogColor = fogColor.asRGB();
            return this;
        }

        @AsOf("2.3.0")
        public Builder waterColor(String waterColor) {
            @Nullable Integer parsedColor = AbstractBiome.parseHex(waterColor);
            if (parsedColor != null) this.waterColor = parsedColor;
            return this;
        }

        @AsOf("2.3.0")
        public Builder waterColor(Color waterColor) {
            this.waterColor = waterColor.asRGB();
            return this;
        }

        @AsOf("2.3.0")
        public Builder waterFogColor(String waterFogColor) {
            this.waterFogColor = AbstractBiome.parseHex(waterFogColor);
            return this;
        }

        @AsOf("2.3.0")
        public Builder waterFogColor(Color waterFogColor) {
            this.waterFogColor = waterFogColor.asRGB();
            return this;
        }

        @AsOf("2.3.0")
        public Builder skyColor(String skyColor) {
            this.skyColor = AbstractBiome.parseHex(skyColor);
            return this;
        }

        @AsOf("2.3.0")
        public Builder skyColor(Color skyColor) {
            this.skyColor = skyColor.asRGB();
            return this;
        }

        @AsOf("2.3.0")
        public Builder foliageColor(String foliageColor) {
            this.foliageColor = AbstractBiome.parseHex(foliageColor);
            return this;
        }

        @AsOf("2.3.0")
        public Builder foliageColor(Color foliageColor) {
            this.foliageColor = foliageColor.asRGB();
            return this;
        }

        @AsOf("2.3.0")
        public Builder grassColor(String grassColor) {
            this.grassColor = AbstractBiome.parseHex(grassColor);
            return this;
        }

        @AsOf("2.3.0")
        public Builder grassColor(Color grassColor) {
            this.grassColor = grassColor.asRGB();
            return this;
        }

        @AsOf("2.3.0")
        public Builder dryFoliageColor(String dryFoliageColor) {
            this.dryFoliageColor = AbstractBiome.parseHex(dryFoliageColor);
            return this;
        }

        @AsOf("2.3.0")
        public Builder dryFoliageColor(Color dryFoliageColor) {
            this.dryFoliageColor = dryFoliageColor.asRGB();
            return this;
        }

        @AsOf("2.3.0")
        public Builder grassColorModifier(GrassColorModifier grassColorModifier) {
            this.grassColorModifier = grassColorModifier;
            return this;
        }

        @AsOf("2.3.0")
        public Builder particleCatalog(ParticleCatalog particleCatalog) {
            this.particleCatalog = particleCatalog;
            return this;
        }

        @AsOf("2.3.0")
        public Builder ambientParticle(WrappedParticleTypes particleType, float probability) {
            this.particleCatalog = this.particleCatalog.with(particleType, probability);
            return this;
        }

        @AsOf("2.3.0")
        public <T extends ParticleData> Builder ambientParticle(WrappedParticleTypes particleType, float probability, @Nullable T data) {
            this.particleCatalog = this.particleCatalog.with(particleType, probability, data);
            return this;
        }

        @AsOf("2.3.0")
        public Builder setAttributes(WrappedEnvironmentAttributeMap environmentAttributeMap) {
            this.attributeMap = environmentAttributeMap;
            return this;
        }

        @AsOf("2.3.0")
        public <T, K> Builder setAttribute(WrappedEnvironmentAttributeSupplier<T, K> supplier, K value) {
            this.attributeMap = this.attributeMap.with(supplier, value);
            return this;
        }

        @AsOf("2.3.0")
        public Builder setAttribute(IntColorSupplier supplier, String hex) {
            this.attributeMap = this.attributeMap.with(supplier, hex);
            return this;
        }

        /**
         * @param biomeSpawner the BiomeSpawner of the biome, or {@code null} for none
         * @return this builder, for chaining
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder setSpawner(@Nullable BiomeSpawner biomeSpawner) {
            this.biomeSpawner = biomeSpawner;
            return this;
        }

        /**
         * Sets the generation settings of the biome.
         * @param generationSettings the generation settings of the biome, or {@code null} for none
         * @return this builder, for chaining
         */
        @AsOf("2.3.0")
        public Builder setGenerationSettings(@Nullable BiomeGenerationSettings generationSettings) {
            this.generationSettings = generationSettings;
            return this;
        }

        /**
         * Builds the {@link AbstractBiome}.
         *
         * @return a new AbstractBiome
         * @throws IllegalArgumentException if the resource key or settings are not set
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public AbstractBiome build() {
            Preconditions.checkArgument(resourceKey != null, "Resource key must be set");
            Preconditions.checkArgument(settings != null, "Settings must be set");

            return new AbstractBiomeImpl(
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
                attributeMap,
                biomeSpawner,
                generationSettings
            );
        }
    }
}
