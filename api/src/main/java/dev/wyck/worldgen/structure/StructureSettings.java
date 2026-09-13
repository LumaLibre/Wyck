package dev.wyck.worldgen.structure;

import dev.wyck.annotations.AsOf;
import dev.wyck.biome.Biome;
import dev.wyck.biome.entity.MobCategory;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.tags.TagSet;
import dev.wyck.worldgen.Decoration;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import org.jspecify.annotations.NullMarked;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Structure placement settings.
 *
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
public interface StructureSettings extends Wrapper {

    /**
     * The biomes this structure may generate in, as an explicit set or a named biome tag.
     * @return the biomes this structure may generate in
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    TagSet<Biome> biomes();

    /**
     * The spawners this structure replaces inside its bounds, by category.
     * @return the spawn overrides, keyed by mob category
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    Map<MobCategory, StructureSpawnOverride> spawnOverrides();

    /**
     * The generation step this structure is placed in.
     * @return the generation step
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    Decoration step();

    /**
     * How this structure reshapes the terrain it lands on.
     * @return the terrain adaptation
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    TerrainAdaptation terrainAdaptation();

    /**
     * Converts this object back to a builder.
     * @return a builder with the same values as this object
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates new structure settings.
     * @param biomes the biomes the structure may generate in
     * @param spawnOverrides the spawners the structure replaces inside its bounds
     * @param step the generation step the structure is placed in
     * @param terrainAdaptation how the structure reshapes the terrain it lands on
     * @return new structure settings
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static StructureSettings of(TagSet<Biome> biomes, Map<MobCategory, StructureSpawnOverride> spawnOverrides, Decoration step, TerrainAdaptation terrainAdaptation) {
        record Holder() {
            static final ConstructWireProvider<StructureSettings> WIRE =
                ConstructWireProvider.create("dev.wyck.worldgen.structure.StructureSettingsImpl");
        }
        return Holder.WIRE.construct(biomes, Map.copyOf(spawnOverrides), step, terrainAdaptation);
    }

    /**
     * Creates new structure settings with no spawn overrides, placed in
     * {@link Decoration#SURFACE_STRUCTURES} without reshaping terrain.
     * @param biomes the biomes the structure may generate in
     * @return new structure settings
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static StructureSettings of(TagSet<Biome> biomes) {
        return of(biomes, Map.of(), Decoration.SURFACE_STRUCTURES, TerrainAdaptation.NONE);
    }

    /**
     * Reads Minecraft structure settings.
     * @param minecraftSettings the structure settings to read
     * @return the decoded structure settings
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static StructureSettings decode(Object minecraftSettings) {
        record Holder() {
            static final Decoder<StructureSettings> DECODER =
                Decoder.create("dev.wyck.decode.worldgen.structure.StructureSettingsDecoder");
        }
        return Holder.DECODER.decode(minecraftSettings);
    }

    /**
     * Creates a new builder.
     * @param biomes the biomes the structure may generate in
     * @return a new builder
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static Builder builder(TagSet<Biome> biomes) {
        return new Builder().biomes(biomes);
    }

    /**
     * Builder for {@link StructureSettings}.
     * @since 3.4.0
     * @version 3.4.0
     * @author Jsinco
     */
    @AsOf("3.4.0")
    final class Builder {
        private TagSet<Biome> biomes = TagSet.ofBiomes();
        private Map<MobCategory, StructureSpawnOverride> spawnOverrides = new LinkedHashMap<>();
        private Decoration step = Decoration.SURFACE_STRUCTURES;
        private TerrainAdaptation terrainAdaptation = TerrainAdaptation.NONE;

        public Builder() {}

        public Builder(StructureSettings settings) {
            this.biomes = settings.biomes();
            this.spawnOverrides = new LinkedHashMap<>(settings.spawnOverrides());
            this.step = settings.step();
            this.terrainAdaptation = settings.terrainAdaptation();
        }

        /**
         * Sets the biomes the structure may generate in.
         * @param biomes the biomes the structure may generate in
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder biomes(TagSet<Biome> biomes) {
            this.biomes = biomes;
            return this;
        }

        /**
         * Sets the spawn overrides.
         * @param spawnOverrides the spawners the structure replaces inside its bounds
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder spawnOverrides(Map<MobCategory, StructureSpawnOverride> spawnOverrides) {
            this.spawnOverrides = new LinkedHashMap<>(spawnOverrides);
            return this;
        }

        /**
         * Sets the generation step the structure is placed in.
         * @param step the generation step
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder step(Decoration step) {
            this.step = step;
            return this;
        }

        /**
         * Sets how the structure reshapes the terrain it lands on.
         * @param terrainAdaptation the terrain adaptation
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder terrainAdaptation(TerrainAdaptation terrainAdaptation) {
            this.terrainAdaptation = terrainAdaptation;
            return this;
        }

        // Friendly

        /**
         * Adds a spawn override for one mob category.
         * @param category the category to override
         * @param override the replacement spawners
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder spawnOverride(MobCategory category, StructureSpawnOverride override) {
            this.spawnOverrides.put(category, override);
            return this;
        }

        /**
         * Builds the structure settings.
         * @return the structure settings
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public StructureSettings build() {
            return of(biomes, spawnOverrides, step, terrainAdaptation);
        }
    }
}
