package dev.wyck.wrapper.level;

import dev.wyck.annotations.AsOf;
import dev.wyck.model.biome.Biome;
import dev.wyck.factory.WireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.internal.Wrapper;
import dev.wyck.wrapper.worldgen.climate.ClimatePoint;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a biome source, which determines how biomes are distributed in a world.
 *
 * @since 2.4.0
 * @version 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
public interface BiomeSource extends Wrapper {

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("dev.wyck.wrapper.level.BiomeSourceFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        BiomeSource fixed(ResourceKey biome);
        BiomeSource multiNoise(List<MultiNoiseEntry> entries);
        BiomeSource preset(ResourceKey preset);
    }

    /**
     * Creates a biome source that always returns the specified biome.
     * @param biome the biome to return
     * @return a biome source that always returns the specified biome
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static BiomeSource fixed(ResourceKey biome) {
        return WIRE.get().fixed(biome);
    }

    /**
     * Creates a biome source that always returns the specified biome.
     * @param biome the biome to return
     * @return a biome source that always returns the specified biome
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static BiomeSource fixed(Biome biome) {
        return fixed(biome.resourceKey());
    }

    /**
     * Creates a biome source that always returns the specified biome.
     * @param biome the biome to return
     * @return a biome source that always returns the specified biome
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static BiomeSource fixed(org.bukkit.block.Biome biome) {
        return fixed(ResourceKey.of(biome.getKey().asString()));
    }

    /**
     * Creates a multi-noise biome source.
     * @return a new multi-noise biome source builder
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static MultiNoiseBuilder multiNoise() {
        return new MultiNoiseBuilder();
    }

    /**
     * Creates a biome source preset.
     * @param preset the preset to create
     * @return the biome source preset
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static BiomeSource preset(ResourceKey preset) {
        return WIRE.get().preset(preset);
    }

    /**
     * Biome source preset for the overworld.
     * @return the overworld biome source preset
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static BiomeSource overworld() {
        return preset(ResourceKey.minecraft("overworld"));
    }

    /**
     * Biome source preset for the nether.
     * @return the nether biome source preset
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static BiomeSource nether() {
        return preset(ResourceKey.minecraft("nether"));
    }

    /**
     * A multi-noise entry.
     * @param biome the biome to return
     * @param climatePoint the climate point to use
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    record MultiNoiseEntry(ResourceKey biome, ClimatePoint climatePoint) {}

    /**
     * A builder for multi-noise biome sources.
     * @since 2.4.0
     * @version 2.4.0
     * @author Jsinco
     */
    @AsOf("2.4.0")
    final class MultiNoiseBuilder {

        private final List<MultiNoiseEntry> entries = new ArrayList<>();

        public MultiNoiseBuilder() {}

        @AsOf("2.4.0")
        public MultiNoiseBuilder add(ResourceKey biome, ClimatePoint point) {
            this.entries.add(new MultiNoiseEntry(biome, point));
            return this;
        }

        @AsOf("2.4.0")
        public MultiNoiseBuilder add(Biome biome, ClimatePoint point) {
            return this.add(biome.resourceKey(), point);
        }

        @AsOf("2.4.0")
        public MultiNoiseBuilder add(org.bukkit.block.Biome biome, ClimatePoint point) {
            return this.add(ResourceKey.of(biome.getKey().asString()), point);
        }

        @AsOf("2.4.0")
        public BiomeSource build() {
            return WIRE.get().multiNoise(List.copyOf(this.entries));
        }
    }
}