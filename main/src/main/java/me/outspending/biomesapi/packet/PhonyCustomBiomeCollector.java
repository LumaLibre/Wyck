package me.outspending.biomesapi.packet;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.packet.data.ChunkLocation;
import me.outspending.biomesapi.packet.data.PhonyCustomBiome;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * A collector for managing PhonyCustomBiome instances.
 *
 * @version 0.0.6
 * @since 0.0.6
 * @author Jsinco
 */
@ApiStatus.Experimental
@AsOf("0.0.6")
public class PhonyCustomBiomeCollector {

    private final Set<PhonyCustomBiome> backing = new HashSet<>();


    /**
     * Appends a phony custom biome to the collector.
     * @param biome the phony custom biome to append
     */
    @AsOf("0.0.6")
    public void appendBiome(@NotNull PhonyCustomBiome biome) {
        if (backing.contains(biome)) {
            throw new IllegalArgumentException("PhonyCustomBiome with key " + biome.biomeResourceKey() + " is already registered.");
        }
        backing.add(biome);
    }

    /**
     * Checks if the collector has the given phony custom biome.
     * @param biome the phony custom biome to check
     * @return true if the collector has the biome, false otherwise
     */
    @AsOf("0.0.6")
    public boolean hasBiome(@NotNull PhonyCustomBiome biome) {
        return backing.contains(biome);
    }

    /**
     * Checks if the collector has a phony custom biome with the given BiomeResourceKey.
     * @param biomeKey the BiomeResourceKey of the biome to check
     * @return true if the collector has the biome, false otherwise
     * @version 0.0.8
     */
    @AsOf("0.0.6")
    public boolean hasBiome(@NotNull BiomeResourceKey biomeKey) {
        return backing.stream().anyMatch((PhonyCustomBiome biome) -> biome.biomeResourceKey().equals(biomeKey));
    }

    /**
     * Removes a phony custom biome from the collector.
     * @param biome the phony custom biome to remove
     * @return true if the biome was removed, false if it was not found
     */
    @AsOf("0.0.6")
    public boolean removeBiome(@NotNull PhonyCustomBiome biome) {
        return backing.remove(biome);
    }


    /**
     * Removes a phony custom biome with the given BiomeResourceKey from the collector.
     * @param biomeKey the BiomeResourceKey of the biome to remove
     * @return true if the biome was removed, false if it was not found
     * @version 0.0.8
     */
    @AsOf("0.0.6")
    public boolean removeBiome(@NotNull BiomeResourceKey biomeKey) {
        return backing.removeIf((PhonyCustomBiome biome) -> biome.biomeResourceKey().equals(biomeKey));
    }


    /**
     * Clears all phony custom biomes from the collector.
     */
    @AsOf("0.0.6")
    public void clearBiomes() {
        backing.clear();
    }

    /**
     * Picks the 'best' phony custom biome for the given player and chunk location.
     * @param player the player
     * @param chunkLocation the chunk location
     * @return the best phony custom biome, or null if none match
     */
    @AsOf("0.0.6")
    public @Nullable PhonyCustomBiome bestBiomeFor(@NotNull Player player, @NotNull ChunkLocation chunkLocation) {
        if (backing.isEmpty()) {
            return null;
        }

        return backing.stream()
                .filter((PhonyCustomBiome phony) -> phony.conditional().test(player, chunkLocation))
                .max(Comparator.comparingInt((PhonyCustomBiome phony) -> phony.priority().getLevel()))
                .orElse(null);
    }

    /**
     * Picks the 'best' custom biome for the given player and chunk location.
     * @param player the player
     * @param chunkLocation the chunk location
     * @return the best custom biome, or null if none match
     */
    @AsOf("0.0.8")
    public @Nullable CustomBiome bestCustomBiomeFor(@NotNull Player player, @NotNull ChunkLocation chunkLocation) {
        PhonyCustomBiome phony = bestBiomeFor(player, chunkLocation);
        return phony != null ? phony.toCustomBiome() : null;
    }
}
