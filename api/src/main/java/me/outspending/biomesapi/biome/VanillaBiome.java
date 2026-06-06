package me.outspending.biomesapi.biome;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.biome.impl.VanillaBiomeImpl;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.wrapper.BiomeSettings;
import me.outspending.biomesapi.wrapper.entity.BiomeSpawner;
import me.outspending.biomesapi.wrapper.environment.GrassColorModifier;
import me.outspending.biomesapi.wrapper.environment.attribute.WrappedEnvironmentAttributeMap;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleCatalog;
import me.outspending.biomesapi.wrapper.worldgen.BiomeGenerationSettings;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a vanilla Minecraft biome.
 * <p>
 * Every property of a vanilla biome is read-only except for its {@link BiomeSpawner} for now; all
 * other setters throw {@link UnsupportedOperationException}.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@AsOf("2.3.0")
public interface VanillaBiome extends AbstractBiome {

    @Override
    @AsOf("2.3.0")
    default VanillaBiome setSettings(@NotNull BiomeSettings settings) {
        throw new UnsupportedOperationException("Cannot set settings of vanilla biomes.");
    }

    @Override
    @AsOf("2.3.0")
    default VanillaBiome setWaterColor(int waterColor) {
        throw new UnsupportedOperationException("Cannot set water color of vanilla biomes.");
    }

    @Override
    @AsOf("2.3.0")
    default VanillaBiome setWaterColor(@NotNull String waterColor) {
        throw new UnsupportedOperationException("Cannot set water color of vanilla biomes.");
    }

    @Override
    @AsOf("2.3.0")
    default VanillaBiome setFogColor(@Nullable Integer fogColor) {
        throw new UnsupportedOperationException("Cannot set fog color of vanilla biomes.");
    }

    @Override
    @AsOf("2.3.0")
    default VanillaBiome setFogColor(@Nullable String fogColor) {
        throw new UnsupportedOperationException("Cannot set fog color of vanilla biomes.");
    }

    @Override
    @AsOf("2.3.0")
    default VanillaBiome setWaterFogColor(@Nullable Integer waterFogColor) {
        throw new UnsupportedOperationException("Cannot set water fog color of vanilla biomes.");
    }

    @Override
    @AsOf("2.3.0")
    default VanillaBiome setWaterFogColor(@Nullable String waterFogColor) {
        throw new UnsupportedOperationException("Cannot set water fog color of vanilla biomes.");
    }

    @Override
    @AsOf("2.3.0")
    default VanillaBiome setSkyColor(@Nullable Integer skyColor) {
        throw new UnsupportedOperationException("Cannot set sky color of vanilla biomes.");
    }

    @Override
    @AsOf("2.3.0")
    default VanillaBiome setSkyColor(@Nullable String skyColor) {
        throw new UnsupportedOperationException("Cannot set sky color of vanilla biomes.");
    }

    @Override
    @AsOf("2.3.0")
    default VanillaBiome setFoliageColor(@Nullable Integer foliageColor) {
        throw new UnsupportedOperationException("Cannot set foliage color of vanilla biomes.");
    }

    @Override
    @AsOf("2.3.0")
    default VanillaBiome setFoliageColor(@Nullable String foliageColor) {
        throw new UnsupportedOperationException("Cannot set foliage color of vanilla biomes.");
    }

    @Override
    @AsOf("2.3.0")
    default VanillaBiome setGrassColor(@Nullable Integer grassColor) {
        throw new UnsupportedOperationException("Cannot set grass color of vanilla biomes.");
    }

    @Override
    @AsOf("2.3.0")
    default VanillaBiome setGrassColor(@Nullable String grassColor) {
        throw new UnsupportedOperationException("Cannot set grass color of vanilla biomes.");
    }

    @Override
    @AsOf("2.3.0")
    default VanillaBiome setDryFoliageColor(@Nullable Integer dryFoliageColor) {
        throw new UnsupportedOperationException("Cannot set dry foliage color of vanilla biomes.");
    }

    @Override
    @AsOf("2.3.0")
    default VanillaBiome setDryFoliageColor(@Nullable String dryFoliageColor) {
        throw new UnsupportedOperationException("Cannot set dry foliage color of vanilla biomes.");
    }

    @Override
    @AsOf("2.3.0")
    default VanillaBiome setGrassColorModifier(@NotNull GrassColorModifier grassColorModifier) {
        throw new UnsupportedOperationException("Cannot set grass color modifier of vanilla biomes.");
    }

    @Override
    @AsOf("2.3.0")
    default VanillaBiome setParticleCatalog(@NotNull ParticleCatalog particleCatalog) {
        throw new UnsupportedOperationException("Cannot set particle catalog of vanilla biomes.");
    }

    @Override
    @AsOf("2.3.0")
    default VanillaBiome setAttributes(@NotNull WrappedEnvironmentAttributeMap attributes) {
        throw new UnsupportedOperationException("Cannot set attributes of vanilla biomes.");
    }

    /**
     * Sets the {@link BiomeSpawner} of the vanilla biome.
     *
     * @param biomeSpawner the BiomeSpawner to apply, or {@code null} to clear it
     * @return this VanillaBiome with the updated spawner
     * @since 2.3.0
     */
    @Override
    @AsOf("2.3.0")
    VanillaBiome setBiomeSpawner(@Nullable BiomeSpawner biomeSpawner);


    /**
     * Sets the {@link BiomeGenerationSettings} of the vanilla biome.
     *
     * @param generationSettings the BiomeGenerationSettings of the AbstractBiome
     * @return this VanillaBiome with the updated BiomeGenerationSettings
     * @since 2.3.0
     */
    @Override
    @AsOf("2.3.0")
    VanillaBiome setGenerationSettings(@Nullable BiomeGenerationSettings generationSettings);

    /**
     * @return a new, empty {@link Builder}.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    @ApiStatus.Internal
    static @NotNull Builder builder() {
        return new Builder();
    }

    /**
     * @param biome the AbstractBiome to seed the builder with
     * @return a new {@link Builder} seeded with the given biome's properties.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    @ApiStatus.Internal
    static @NotNull Builder builder(@NotNull AbstractBiome biome) {
        return new Builder(biome);
    }

    /**
     * Builder for {@link VanillaBiome}. Vanilla biomes are read-only except for their
     * {@link BiomeSpawner}, so this builder is intended to be seeded from an existing
     * biome and then have its spawner adjusted before building.
     *
     * @since 2.3.0
     * @author Jsinco
     */
    @AsOf("2.3.0")
    final class Builder {

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
        private WrappedEnvironmentAttributeMap attributeMap = WrappedEnvironmentAttributeMap.EMPTY;
        private @Nullable BiomeSpawner biomeSpawner = null;
        private @Nullable BiomeGenerationSettings generationSettings = null;

        @AsOf("2.3.0")
        public Builder() {}

        /**
         * Creates a builder seeded with the properties of an existing {@link AbstractBiome},
         * mirroring {@link AbstractBiome.Builder#Builder(AbstractBiome)}.
         *
         * @param biome the AbstractBiome to copy from
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder(@NotNull AbstractBiome biome) {
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
        public @NotNull Builder resourceKey(@NotNull BiomeResourceKey resourceKey) {
            this.resourceKey = resourceKey;
            return this;
        }

        /**
         * The only mutable property of a vanilla biome.
         *
         * @param biomeSpawner the BiomeSpawner of the biome, or {@code null} for none
         * @return this builder, for chaining
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public @NotNull Builder setSpawner(@Nullable BiomeSpawner biomeSpawner) {
            this.biomeSpawner = biomeSpawner;
            return this;
        }

        /**
         * Sets the generation settings of the biome.
         *
         * @param generationSettings the generation settings of the biome, or {@code null} for none
         * @return this builder, for chaining
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public @NotNull Builder setGenerationSettings(@Nullable BiomeGenerationSettings generationSettings) {
            this.generationSettings = generationSettings;
            return this;
        }

        /**
         * Builds the {@link VanillaBiome}.
         *
         * @return a new VanillaBiome
         * @throws IllegalArgumentException if the resource key or settings are not set
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public @NotNull VanillaBiome build() {
            Preconditions.checkArgument(resourceKey != null, "Resource key must be set");
            Preconditions.checkArgument(settings != null, "Settings must be set");

            return new VanillaBiomeImpl(
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