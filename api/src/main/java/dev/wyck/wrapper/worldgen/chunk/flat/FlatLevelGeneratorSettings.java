package dev.wyck.wrapper.worldgen.chunk.flat;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.model.biome.Biome;
import dev.wyck.wrapper.internal.Wrapper;
import dev.wyck.wrapper.worldgen.chunk.ChunkGenerator;
import dev.wyck.wrapper.worldgen.placement.PlacedFeature;
import dev.wyck.wrapper.worldgen.placement.PlacedFeatures;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

/**
 * Flat source generator settings.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface FlatLevelGeneratorSettings extends Wrapper {

    @ApiStatus.Internal
    ConstructWireProvider<FlatLevelGeneratorSettings> WIRE = ConstructWireProvider.construct("dev.wyck.wrapper.worldgen.chunk.flat.FlatLevelGeneratorSettingsImpl");

    /**
     * Returns the list of layers.
     * @return the list of layers
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    List<FlatLayerInfo> layers();

    /**
     * If features should be generated.
     * @return whether features should be generated
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    boolean decoration();

    /**
     * Returns the biome.
     * @apiNote Overridden by {@link ChunkGenerator#biomeSource()}
     * @return the biome
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    @ApiStatus.Obsolete
    Biome biome();

    /**
     * Returns the fallback biome.
     * @apiNote Overridden by {@link ChunkGenerator#biomeSource()}
     * @return the fallback biome
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    @ApiStatus.Obsolete
    Biome fallbackBiome();

    /**
     * Lakes to generate.
     * @return the lakes to generate
     * @since 3.0.0
     */
    List<PlacedFeature> lakes();

    /**
     * Converts this object back to a builder.
     * @return a builder with the same values as this object
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new FlatLevelGeneratorSettings.
     * @param layers the layers
     * @param decoration whether features should be generated
     * @param biome the biome
     * @param fallbackBiome the fallback biome
     * @param lakes the lakes to generate
     * @return a new FlatLevelGeneratorSettings
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static FlatLevelGeneratorSettings of(List<FlatLayerInfo> layers, boolean decoration, Biome biome, Biome fallbackBiome, List<PlacedFeature> lakes) {
        return WIRE.construct(layers, decoration, biome, fallbackBiome, lakes);
    }

    /**
     * Creates a new builder.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link FlatLevelGeneratorSettings}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private static final Biome PLAINS = Biome.reference(ResourceKey.minecraft("plains"));

        // classic flat
        private List<FlatLayerInfo> layers = new ArrayList<>(List.of(
            FlatLayerInfo.of(Material.GRASS_BLOCK, 1),
            FlatLayerInfo.of(Material.DIRT, 2),
            FlatLayerInfo.of(Material.BEDROCK, 1)
        ));
        private boolean decoration = false;
        private Biome biome = PLAINS;
        private Biome fallbackBiome = PLAINS;
        private List<PlacedFeature> lakes = new ArrayList<>(List.of(
            PlacedFeatures.LAKE_LAVA_UNDERGROUND,
            PlacedFeatures.LAKE_LAVA_SURFACE
        ));

        public Builder() {}

        public Builder(FlatLevelGeneratorSettings settings) {
            this.layers = new ArrayList<>(settings.layers());
            this.decoration = settings.decoration();
            this.biome = settings.biome();
            this.fallbackBiome = settings.fallbackBiome();
            this.lakes = new ArrayList<>(settings.lakes());
        }

        /**
         * Sets the layers.
         * @param layers the layers
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder layers(List<FlatLayerInfo> layers) {
            this.layers = layers;
            return this;
        }

        /**
         * Sets whether decoration should be generated.
         * @param decoration whether decoration should be generated
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder decoration(boolean decoration) {
            this.decoration = decoration;
            return this;
        }

        /**
         * Sets the biome.
         * @param biome the biome
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder biome(Biome biome) {
            this.biome = biome;
            return this;
        }

        /**
         * Sets the fallback biome.
         * @param fallbackBiome the fallback biome
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder fallbackBiome(Biome fallbackBiome) {
            this.fallbackBiome = fallbackBiome;
            return this;
        }

        /**
         * Sets the lakes to generate.
         * @param lakes the lakes to generate
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder lakes(List<PlacedFeature> lakes) {
            this.lakes = lakes;
            return this;
        }

        // Friendly

        /**
         * Adds a layer to the list of layers.
         * @param block the block to add
         * @param height the height of the layer
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder layer(Material block, int height) {
            this.layers.add(FlatLayerInfo.of(block, height));
            return this;
        }

        /**
         * Adds a lake to the list of lakes.
         * @param lake the lake to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder lake(PlacedFeature lake) {
            this.lakes.add(lake);
            return this;
        }

        /**
         * Builds the FlatLevelGeneratorSettings.
         * @return the FlatLevelGeneratorSettings
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public FlatLevelGeneratorSettings build() {
            return of(layers, decoration, biome, fallbackBiome, lakes);
        }
    }
}
