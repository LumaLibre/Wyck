package me.outspending.biomesapi.wrapper.level;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.biome.AbstractBiome;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import me.outspending.biomesapi.wrapper.worldgen.climate.BiomeClimatePoint;
import org.bukkit.block.Biome;
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
@ApiStatus.Experimental
public interface BiomeSource extends NmsHandle {

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.level.BiomeSourceFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        BiomeSource fixed(ResourceKey biome);
        BiomeSource multiNoise(List<MultiNoiseEntry> entries);
    }

    @AsOf("2.4.0")
    static BiomeSource fixed(ResourceKey biome) {
        return WIRE.get().fixed(biome);
    }

    @AsOf("2.4.0")
    static MultiNoiseBuilder multiNoise() {
        return new MultiNoiseBuilder();
    }

    @AsOf("2.4.0")
    record MultiNoiseEntry(ResourceKey biome, BiomeClimatePoint point) {}

    @AsOf("2.4.0")
    final class MultiNoiseBuilder {

        private final List<MultiNoiseEntry> entries = new ArrayList<>();

        @AsOf("2.4.0")
        public MultiNoiseBuilder add(ResourceKey biome, BiomeClimatePoint point) {
            this.entries.add(new MultiNoiseEntry(biome, point));
            return this;
        }

        @AsOf("2.4.0")
        public MultiNoiseBuilder add(AbstractBiome biome, BiomeClimatePoint point) {
            return this.add(biome.getResourceKey(), point);
        }

        @AsOf("2.4.0")
        public MultiNoiseBuilder add(Biome biome, BiomeClimatePoint point) {
            return this.add(ResourceKey.of(biome.getKey().asString()), point);
        }

        @AsOf("2.4.0")
        public BiomeSource build() {
            return WIRE.get().multiNoise(List.copyOf(this.entries));
        }
    }
}