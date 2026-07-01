package me.outspending.biomesapi.wrapper.level;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.biome.AbstractBiome;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.wrapper.internal.NmsDecoder;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import me.outspending.biomesapi.wrapper.worldgen.climate.ClimatePoint;
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
@ApiStatus.Experimental
public interface BiomeSource extends NmsHandle {

    Codec<BiomeSource> CODEC = Codec.STRING.dispatch(
        "type",
        source -> source.entries() == null ? "fixed" : "multi_noise",
        type -> switch (type) {
            case "fixed" -> ResourceKey.CODEC.fieldOf("biome").xmap(BiomeSource::fixed,
                source -> Preconditions.checkNotNull(source.fixedBiome(), "fixedBiome"));
            case "multi_noise" -> Codec.list(MultiNoiseEntry.CODEC).fieldOf("entries").xmap(BiomeSource::fromEntries,
                source -> Preconditions.checkNotNull(source.entries(), "entries"));
            default -> throw new IllegalStateException("unknown biome source: " + type);
        }
    );

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.level.BiomeSourceFactoryImpl");

    @ApiStatus.Internal
    interface Factory extends NmsDecoder<BiomeSource> {
        BiomeSource fixed(ResourceKey biome);

        BiomeSource multiNoise(List<MultiNoiseEntry> entries);
    }

    @AsOf("2.4.0")
    @ApiStatus.Internal
    @Nullable ResourceKey fixedBiome();

    @AsOf("2.4.0")
    @ApiStatus.Internal
    @Nullable List<MultiNoiseEntry> entries();

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
    static BiomeSource fromMinecraft(Object handle) {
        return WIRE.get().fromMinecraft(handle);
    }

    @ApiStatus.Internal
    private static BiomeSource fromEntries(List<MultiNoiseEntry> entries) {
        MultiNoiseBuilder b = multiNoise();
        for (MultiNoiseEntry e : entries) {
            b.add(e.biome(), e.climatePoint());
        }
        return b.build();
    }

    @AsOf("2.4.0")
    record MultiNoiseEntry(ResourceKey biome, ClimatePoint climatePoint) {
        public static final Codec<MultiNoiseEntry> CODEC = RecordCodecBuilder.create(i -> i.group(
            ResourceKey.CODEC.fieldOf("biome").forGetter(MultiNoiseEntry::biome),
            ClimatePoint.CODEC.fieldOf("climate_point").forGetter(MultiNoiseEntry::climatePoint)
        ).apply(i, MultiNoiseEntry::new));
    }

    @AsOf("2.4.0")
    final class MultiNoiseBuilder {

        private final List<MultiNoiseEntry> entries = new ArrayList<>();

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