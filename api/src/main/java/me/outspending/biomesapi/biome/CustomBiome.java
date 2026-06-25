package me.outspending.biomesapi.biome;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.biome.impl.CustomBiomeImpl;
import me.outspending.biomesapi.keys.KeyChains;
import me.outspending.biomesapi.registry.BiomeRegistry;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.renderer.packet.PacketHandler;
import me.outspending.biomesapi.renderer.packet.data.BlockReplacement;
import me.outspending.biomesapi.renderer.packet.data.PhonyCustomBiome;
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
import org.bukkit.Color;
import org.bukkit.Material;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collection;

/**
 * This interface represents a custom biome in the BiomesAPI.
 * It provides methods to retrieve and modify the properties of the custom biome.
 *
 * @since 0.0.1
 * @version 2.3.0
 * @author Outspending
 */
@NullMarked
@AsOf("2.3.0")
public interface CustomBiome extends AbstractBiome {

    Codec<CustomBiome> CODEC = Codec.lazyInitialized(() -> CustomBiomeImpl.CODEC.xmap(biome -> biome, biome -> (CustomBiomeImpl) biome));

    /**
     * Returns the BlockReplacements of the CustomBiome.
     *
     * @return the BlockReplacements of the CustomBiome
     * @since 0.0.6
     */
    @AsOf("0.0.6")
    BlockReplacement[] getBlockReplacements();

    /**
     * Sets the BlockReplacements of the CustomBiome.
     *
     * @apiNote Block replacements are only supported when rendering custom biomes to clients via the {@link PacketHandler}.
     * @param blockReplacements the BlockReplacements of the CustomBiome
     * @since 0.0.6
     * @return the CustomBiome with the updated BlockReplacements
     */
    @AsOf("0.0.6")
    CustomBiome setBlockReplacements(BlockReplacement[] blockReplacements);

    /**
     * Compares this CustomBiome to another CustomBiome to determine if they are similar.
     * Two CustomBiomes are considered similar if they have the same properties.
     *
     * @param otherBiome The other CustomBiome to compare to.
     * @return true if the CustomBiomes are similar, false otherwise.
     * @since 0.0.17
     */
    @AsOf("0.0.17")
    boolean isSimilar(CustomBiome otherBiome);

    /**
     * Registers the CustomBiome to the biome registry.
     *
     * @return the registered CustomBiome
     * @since 0.0.2
     */
    @AsOf("0.0.2")
    default CustomBiome register() {
        BiomeRegistry.registry().register(this);
        return this;
    }

    /**
     * Returns a new Builder instance with the properties of the CustomBiome.
     *
     * @return a new Builder instance with the properties of the CustomBiome
     * @since 0.0.5
     */
    @AsOf("0.0.5")
    default CustomBiome.Builder toBuilder() {
        return new CustomBiome.Builder(this);
    }

    /**
     * Returns a new Builder instance with the properties of the CustomBiome.
     *
     * @return a new Builder instance with the properties of the CustomBiome
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    default CustomBiome.Builder asBuilder() {
        return new CustomBiome.Builder(this);
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
        return KeyChains.BIOMES.isRegistered(this.getResourceKey());
    }

    /**
     * Returns a new instance of the Builder class.
     *
     * @return a new Builder instance
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    static CustomBiome.Builder builder() {
        return new CustomBiome.Builder();
    }

    /**
     * Returns a new instance of the Builder class with the provided resource key.
     * @param resourceKey the ResourceKey to set for the CustomBiome being built
     * @return a new Builder instance
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    static CustomBiome.Builder builder(ResourceKey resourceKey) {
        Preconditions.checkNotNull(resourceKey, "Resource key cannot be null.");
        return new CustomBiome.Builder().resourceKey(resourceKey);
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
    final class Builder {

        private ResourceKey resourceKey = null;
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
        private WrappedEnvironmentAttributeMap attributeMap = WrappedEnvironmentAttributeMap.EMPTY;
        private @Nullable BiomeSpawner biomeSpawner = null;
        private @Nullable BiomeGenerationSettings generationSettings = null;

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
        public Builder(CustomBiome biome) {
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
            this.attributeMap = biome.getAttributes();
            this.biomeSpawner = biome.getBiomeSpawner();
            this.generationSettings = biome.getGenerationSettings();
        }

        /**
         * This method sets the ResourceKey property of the CustomBiome.
         *
         * @param resourceKey The ResourceKey of the custom biome.
         * @since 0.0.1
         * @return The Builder object, for chaining method calls.
         */
        @AsOf("0.0.1")
        public Builder resourceKey(ResourceKey resourceKey) {
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
        public Builder settings(BiomeSettings settings) {
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
        public Builder fogColor(String fogColor) {
            this.fogColor = AbstractBiome.parseHex(fogColor);
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
        public Builder fogColor(Color fogColor) {
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
        public Builder waterColor(String waterColor) {
            @Nullable Integer parsedColor = AbstractBiome.parseHex(waterColor);
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
        public Builder waterColor(Color waterColor) {
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
        public Builder waterFogColor(String waterFogColor) {
            this.waterFogColor = AbstractBiome.parseHex(waterFogColor);
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
        public Builder waterFogColor(Color waterFogColor) {
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
        public Builder skyColor(String skyColor) {
            this.skyColor = AbstractBiome.parseHex(skyColor);
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
        public Builder skyColor(Color skyColor) {
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
        public Builder foliageColor(String foliageColor) {
            this.foliageColor = AbstractBiome.parseHex(foliageColor);
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
        public Builder foliageColor(Color foliageColor) {
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
        public Builder grassColor(String grassColor) {
            this.grassColor = AbstractBiome.parseHex(grassColor);
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
        public Builder grassColor(Color grassColor) {
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
        public Builder dryFoliageColor(String dryFoliageColor) {
            this.dryFoliageColor = AbstractBiome.parseHex(dryFoliageColor);
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
        public Builder dryFoliageColor(Color dryFoliageColor) {
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
        public Builder grassColorModifier(GrassColorModifier grassColorModifier) {
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
        public Builder particleCatalog(ParticleCatalog particleCatalog) {
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
        public Builder ambientParticle(WrappedParticleTypes particleType, float probability) {
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
        public <T extends ParticleData> Builder ambientParticle(WrappedParticleTypes particleType, float probability, @Nullable T data) {
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
        public Builder blockReplacements(BlockReplacement... blockReplacements) {
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
        public Builder blockReplacements(Collection<BlockReplacement> blockReplacements) {
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
        public Builder replace(Material from, Material to) {
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
        public Builder replace(BlockReplacement replacement) {
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
        public Builder environmentAttributeMap(WrappedEnvironmentAttributeMap environmentAttributeMap) {
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
        public Builder setAttributes(WrappedEnvironmentAttributeMap environmentAttributeMap) {
            this.attributeMap = environmentAttributeMap;
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
        public <T, K> Builder setAttribute(WrappedEnvironmentAttributeSupplier<T, K> supplier, K value) {
            this.attributeMap = this.attributeMap.with(supplier, value);
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
        public Builder setAttribute(IntColorSupplier supplier, String hex) {
            this.attributeMap = this.attributeMap.with(supplier, hex);
            return this;
        }

        /**
         * This method sets the biome spawner property of the CustomBiome.
         * @param biomeSpawner The biome spawner of the custom biome.
         * @return The Builder object, for chaining method calls.
         */
        @AsOf("2.3.0")
        public Builder setSpawner(@Nullable BiomeSpawner biomeSpawner) {
            this.biomeSpawner = biomeSpawner;
            return this;
        }

        /**
         * This method sets the generation settings property of the CustomBiome.
         *
         * @param generationSettings The generation settings of the custom biome.
         * @return The Builder object, for chaining method calls.
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder setGenerationSettings(@Nullable BiomeGenerationSettings generationSettings) {
            this.generationSettings = generationSettings;
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
        public CustomBiome build() {
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
                attributeMap,
                biomeSpawner,
                generationSettings
            );
        }

        /**
         * This method registers the CustomBiome in the biome registry.
         *
         * @return The registered CustomBiome.
         * @since 2.1.0
         */
        @AsOf("2.1.0")
        public CustomBiome register() {
            return build().register();
        }

    }
}
