package dev.wyck.model.biome;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.model.biome.impl.BiomeImpl;
import dev.wyck.registry.BiomeRegistry;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.BiomeSettings;
import dev.wyck.wrapper.biome.BiomeSpecialEffects;
import dev.wyck.wrapper.biome.ClimateSettings;
import dev.wyck.wrapper.entity.BiomeSpawner;
import dev.wyck.wrapper.environment.GrassColorModifier;
import dev.wyck.wrapper.environment.attribute.EnvironmentAttributeMap;
import dev.wyck.wrapper.environment.attribute.EnvironmentAttributeSupplier;
import dev.wyck.wrapper.environment.attribute.EnvironmentAttributes;
import dev.wyck.wrapper.environment.attribute.FriendlyColorSupplier;
import dev.wyck.wrapper.environment.particle.ParticleCatalog;
import dev.wyck.wrapper.environment.particle.ParticleData;
import dev.wyck.wrapper.environment.particle.ParticleTypes;
import dev.wyck.wrapper.worldgen.BiomeGenerationSettings;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Represents an abstract biome in Minecraft.
 *
 * @since 2.3.0
 * @version 2.5.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public interface Biome extends Keyed {

    /**
     * The key associated with this biome.
     * @return the key of this biome.
     * @since 2.5.0
     */
    @AsOf("2.5.0")
    ResourceKey resourceKey();

    /**
     * Climate settings associated with this biome.
     * @return the climate settings associated with this biome.
     * @since 2.5.0
     */
    @AsOf("2.5.0")
    ClimateSettings climateSettings();

    /**
     * Sets new climate settings for this biome.
     * @param climateSettings the new climate settings to set for this biome.
     * @since 2.5.0
     */
    @AsOf("2.5.0")
    void climateSettings(ClimateSettings climateSettings);

    /**
     * Various biome-specific visuals as they appear in vanilla.
     * @return the biome special effects associated with this biome.
     * @since 2.5.0
     */
    @AsOf("2.5.0")
    BiomeSpecialEffects specialEffects();

    /**
     * Sets new special effects for this biome.
     * @param specialEffects the new special effects to set for this biome.
     * @since 2.5.0
     */
    @AsOf("2.5.0")
    void specialEffects(BiomeSpecialEffects specialEffects);

    /**
     * A map of the environment attributes associated with this biome.
     * @return an environment attribute map populated with the environment attributes of this biome.
     * @since 1.1.0
     */
    @AsOf("1.1.0")
    EnvironmentAttributeMap attributes();

    /**
     * Sets the environment attributes for this biome.
     * @param attributes the environment attribute map to set for this biome
     * @since 2.2.0
     */
    @AsOf("2.2.0")
    void attributes(EnvironmentAttributeMap attributes);

    /**
     * Gets the biome spawner associated with this biome.
     * @return the BiomeSpawner of this biome, or null if none is set.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    @Nullable BiomeSpawner biomeSpawner();

    /**
     * Sets or removes the BiomeSpawner of this biome.
     * @param biomeSpawner the BiomeSpawner to set for this biome, or null to remove it.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    void biomeSpawner(@Nullable BiomeSpawner biomeSpawner);

    /**
     * Gets world generation settings associated with this biome.
     * @return the BiomeGenerationSettings of this biome, or null if none is set.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    @Nullable BiomeGenerationSettings generationSettings();


    /**
     * Sets or removes the world generation settings for this biome.
     * @param generationSettings the BiomeGenerationSettings to set for this biome, or null to remove it.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    void generationSettings(@Nullable BiomeGenerationSettings generationSettings);

    /**
     * Compares this biome to another biome to determine if they are similar.
     * Two AbstractBiomes are considered similar if they have the same properties.
     * @param otherBiome The other AbstractBiome to compare to.
     * @return true if the AbstractBiomes are similar, false otherwise.
     * @since 0.0.17
     */
    @AsOf("0.0.17")
    boolean isSimilar(Biome otherBiome);

    /**
     * Gets the Bukkit representation of this biome.
     * @return the Bukkit {@link org.bukkit.block.Biome} equivalent of this biome.
     * @since 0.0.6
     */
    @AsOf("0.0.6")
    org.bukkit.block.Biome bukkitBiome();

    /**
     * Registers this biome in the biome registry.
     * @return the registered biome
     * @since 2.5.0
     */
    @AsOf("2.5.0")
    default Biome register() {
        BiomeRegistry.registry().register(this);
        return this;
    }

    /**
     * Modifies the existing biome in the biome registry with the properties of this CustomBiome.
     * @throws IllegalArgumentException if the biome is not registered.
     * @since 0.0.17
     */
    @AsOf("0.0.17")
    default Biome modify() {
        BiomeRegistry.registry().modify(this);
        return this;
    }

    /**
     * Converts this back into a builder.
     * @return a builder with the same properties as this biome.
     * @since 2.5.0
     */
    @AsOf("2.5.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Gets a reference to a biome from the biome registry.
     * @param resourceKey the ResourceKey of the biome to get a reference to
     * @return a reference to the biome with the given ResourceKey
     * @since 2.5.0
     */
    @AsOf("2.5.0")
    static Biome reference(ResourceKey resourceKey) {
        return BiomeRegistry.registry().getBiome(resourceKey);
    }

    /**
     * Creates or references new biome with the given ResourceKey.
     * @param resourceKey the ResourceKey of the biome to create
     * @return a new biome with the given ResourceKey
     * @since 2.5.0
     */
    @AsOf("2.5.0")
    static Biome of(ResourceKey resourceKey) {
        return builder(resourceKey).build();
    }

    /**
     * A new builder for this class.
     * @return a new, empty {@link Builder}.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * @param resourceKey the ResourceKey to seed the builder with
     * @return a new {@link Builder} seeded with the given resource key.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static Builder builder(ResourceKey resourceKey) {
        Preconditions.checkNotNull(resourceKey, "resourceKey cannot be null.");
        return new Builder().resourceKey(resourceKey);
    }

    /**
     * Builder for {@link Biome}. Configures every property the base biome exposes.
     *
     * @since 2.3.0
     * @author Jsinco
     */
    @AsOf("2.3.0")
    class Builder {
        private @Nullable ResourceKey resourceKey = null;
        private ClimateSettings climateSettings = ClimateSettings.DEFAULT;
        private BiomeSpecialEffects specialEffects = BiomeSpecialEffects.DEFAULT;
        private EnvironmentAttributeMap attributeMap = EnvironmentAttributeMap.EMPTY;
        private @Nullable BiomeSpawner biomeSpawner = null;
        private @Nullable BiomeGenerationSettings generationSettings = null;

        @ApiStatus.Obsolete(since = "2.5.0")
        private ParticleCatalog.@Nullable Builder friendly$ParticleCatalog;

        @AsOf("2.3.0")
        public Builder() {}

        @AsOf("2.3.0")
        public Builder(Biome other) {
            this.resourceKey = other.resourceKey();
            this.climateSettings = other.climateSettings();
            this.specialEffects = other.specialEffects();
            this.attributeMap = other.attributes();
            this.biomeSpawner = other.biomeSpawner();
            this.generationSettings = other.generationSettings();
        }

        /**
         * Sets the ResourceKey of the biome.
         * @param resourceKey the ResourceKey of the biome
         * @return this builder
         */
        @AsOf("2.3.0")
        public Builder resourceKey(ResourceKey resourceKey) {
            this.resourceKey = resourceKey;
            return this;
        }


        /**
         * Sets the climate settings of the biome.
         * @param climateSettings the climate settings of the biome
         * @return this builder
         * @since 2.5.0
         */
        @AsOf("2.5.0")
        public Builder climateSettings(ClimateSettings climateSettings) {
            this.climateSettings = climateSettings;
            return this;
        }

        /**
         * Sets the special effects of the biome.
         * @param specialEffects the special effects of the biome
         * @return this builder
         * @since 2.5.0
         */
        @AsOf("2.5.0")
        public Builder specialEffects(BiomeSpecialEffects specialEffects) {
            this.specialEffects = specialEffects;
            return this;
        }

        /**
         * Sets the environment attributes of the biome.
         * @param attributeMap the environment attributes of the biome
         * @return this builder
         * @since 2.5.0
         */
        @AsOf("2.5.0")
        public Builder attributes(EnvironmentAttributeMap attributeMap) {
            this.attributeMap = attributeMap;
            return this;
        }

        /**
         * Sets the BiomeSpawner of the biome.
         * @param biomeSpawner the BiomeSpawner of the biome, or null to remove it
         * @return this builder
         * @since 2.3.0
         */
        public Builder biomeSpawner(@Nullable BiomeSpawner biomeSpawner) {
            this.biomeSpawner = biomeSpawner;
            return this;
        }

        /**
         * Sets the generation settings of the biome.
         * @param generationSettings the generation settings of the biome, or null to remove it
         * @return this builder
         * @since 2.3.0
         */
        public Builder generationSettings(@Nullable BiomeGenerationSettings generationSettings) {
            this.generationSettings = generationSettings;
            return this;
        }

        // Friendly builder methods

        @AsOf("2.5.0")
        public Builder waterColor(String waterColor) {
            this.specialEffects = this.specialEffects.toBuilder().waterColor(waterColor).build();
            return this;
        }

        @AsOf("2.5.0")
        public Builder foliageColor(@Nullable String foliageColor) {
            this.specialEffects = this.specialEffects.toBuilder().foliageColorOverride(foliageColor).build();
            return this;
        }

        @AsOf("2.5.0")
        public Builder dryFoliageColor(@Nullable String dryFoliageColor) {
            this.specialEffects = this.specialEffects.toBuilder().dryFoliageColorOverride(dryFoliageColor).build();
            return this;
        }

        @AsOf("2.5.0")
        public Builder grassColor(@Nullable String grassColor) {
            this.specialEffects = this.specialEffects.toBuilder().grassColorOverride(grassColor).build();
            return this;
        }

        @AsOf("2.5.0")
        public Builder grassColorModifier(GrassColorModifier grassColorModifier) {
            this.specialEffects = this.specialEffects.toBuilder().grassColorModifier(grassColorModifier).build();
            return this;
        }

        @AsOf("2.5.0")
        public Builder waterFogColor(@Nullable String waterFogColor) {
            this.attribute(EnvironmentAttributes.WATER_FOG_COLOR, waterFogColor);
            return this;
        }

        @AsOf("2.5.0")
        public Builder fogColor(@Nullable String fogColor) {
            this.attribute(EnvironmentAttributes.FOG_COLOR, fogColor);
            return this;
        }

        @AsOf("2.5.0")
        public Builder skyColor(@Nullable String skyColor) {
            this.attribute(EnvironmentAttributes.SKY_COLOR, skyColor);
            return this;
        }

        @AsOf("2.5.0")
        public Builder particle(ParticleTypes type, float probability, @Nullable ParticleData data) {
            if (this.friendly$ParticleCatalog == null) {
                this.friendly$ParticleCatalog = ParticleCatalog.builder();
            }
            this.friendly$ParticleCatalog.particle(type, probability, data);
            return this;
        }

        @AsOf("2.5.0")
        public Builder particle(ParticleTypes type, float probability) {
            return this.particle(type, probability, null);
        }

        @AsOf("2.5.0")
        public Builder settings(BiomeSettings settings) {
            this.climateSettings = ClimateSettings.of(settings.hasPrecipitation(), settings.temperature(), settings.modifier(), settings.downfall());
            return this;
        }

        @AsOf("2.5.0")
        public <V> Builder attribute(EnvironmentAttributeSupplier<V> attribute, @Nullable V value) {
            this.attributeMap = this.attributeMap.with(attribute, value);
            return this;
        }

        @AsOf("2.5.0")
        public Builder attribute(FriendlyColorSupplier attribute, @Nullable String hex) {
            this.attributeMap = this.attributeMap.with(attribute, hex);
            return this;
        }

        /**
         * Builds the biome.
         * @return a new biome
         * @throws IllegalArgumentException if the resource key is not set
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Biome build() {
            Preconditions.checkArgument(resourceKey != null, "resourceKey must be set");
            Preconditions.checkNotNull(climateSettings, "climateSettings cannot be null");
            Preconditions.checkNotNull(specialEffects, "specialEffects cannot be null");
            Preconditions.checkNotNull(attributeMap, "attributeMap cannot be null");

            if (this.friendly$ParticleCatalog != null) {
                this.attribute(EnvironmentAttributes.AMBIENT_PARTICLES, this.friendly$ParticleCatalog.build());
            }

            return new BiomeImpl(
                resourceKey,
                climateSettings,
                specialEffects,
                attributeMap,
                biomeSpawner,
                generationSettings
            );
        }

        @AsOf("2.5.0")
        public Biome register() {
            return build().register();
        }
    }


}
