package dev.wyck.wrapper.level;

import dev.wyck.annotations.AsOf;
import dev.wyck.model.biome.AbstractBiome;
import dev.wyck.factory.WireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.internal.NmsHandle;
import dev.wyck.wrapper.worldgen.climate.ClimatePoint;
import org.bukkit.block.Biome;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

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
public interface BiomeSource extends NmsHandle {

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("dev.wyck.wrapper.level.BiomeSourceFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        BiomeSource fixed(ResourceKey biome);

        BiomeSource multiNoise(List<MultiNoiseEntry> entries);

        BiomeSource preset(ResourceKey preset);

        // TODO: Do when decoders are available
        //List<MultiNoiseEntry> presetEntries(ResourceKey preset);
    }

    // TODO: Decoders
    @ApiStatus.Internal
    @Nullable ResourceKey fixedBiome();

    @ApiStatus.Internal
    @Nullable List<MultiNoiseEntry> entries();

    @ApiStatus.Internal
    @Nullable ResourceKey preset();

    @AsOf("2.4.0")
    static BiomeSource fixed(ResourceKey biome) {
        return WIRE.get().fixed(biome);
    }

    @AsOf("2.4.0")
    static BiomeSource fixed(AbstractBiome biome) {
        return fixed(biome.getResourceKey());
    }

    @AsOf("2.4.0")
    static BiomeSource fixed(Biome biome) {
        return fixed(ResourceKey.of(biome.getKey().asString()));
    }

    @AsOf("2.4.0")
    static MultiNoiseBuilder multiNoise() {
        return new MultiNoiseBuilder();
    }

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


    @AsOf("2.4.0")
    record MultiNoiseEntry(ResourceKey biome, ClimatePoint climatePoint) {}

    @AsOf("2.4.0")
    final class MultiNoiseBuilder {

        private final List<MultiNoiseEntry> entries = new ArrayList<>();

        private MultiNoiseBuilder() {}

        private MultiNoiseBuilder(MultiNoiseBuilder builder) {
            this.entries.addAll(builder.entries);
        }

        @AsOf("2.4.0")
        public MultiNoiseBuilder add(ResourceKey biome, ClimatePoint point) {
            this.entries.add(new MultiNoiseEntry(biome, point));
            return this;
        }

        @AsOf("2.4.0")
        public MultiNoiseBuilder add(AbstractBiome biome, ClimatePoint point) {
            return this.add(biome.getResourceKey(), point);
        }

        @AsOf("2.4.0")
        public MultiNoiseBuilder add(Biome biome, ClimatePoint point) {
            return this.add(ResourceKey.of(biome.getKey().asString()), point);
        }

        @AsOf("2.4.0")
        public BiomeSource build() {
            return WIRE.get().multiNoise(List.copyOf(this.entries));
        }
    }
}