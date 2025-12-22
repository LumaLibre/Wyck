package me.outspending.biomesapi.packet;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.packet.data.ChunkLocation;
import me.outspending.biomesapi.packet.data.PhonyCustomBiome;
import org.bukkit.NamespacedKey;
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
 * @version 0.0.4
 * @since 0.0.4
 * @author Jsinco
 */
@me.outspending.biomesapi.annotations.Experimental
@ApiStatus.Experimental
@AsOf("0.0.4")
public class PhonyCustomBiomeCollector {

    private final Set<PhonyCustomBiome> backing = new HashSet<>();


    /**
     * Appends a phony custom biome to the collector.
     * @param biome the phony custom biome to append
     */
    @AsOf("0.0.4")
    public void appendBiome(@NotNull PhonyCustomBiome biome) {
        if (backing.contains(biome)) {
            throw new IllegalArgumentException("PhonyCustomBiome with key " + biome.customBiome().toNamespacedKey() + " is already registered.");
        }
        backing.add(biome);
    }

    /**
     * Checks if the collector has the given phony custom biome.
     * @param biome the phony custom biome to check
     * @return true if the collector has the biome, false otherwise
     */
    @AsOf("0.0.4")
    public boolean hasBiome(@NotNull PhonyCustomBiome biome) {
        return backing.contains(biome);
    }

    /**
     * Checks if the collector has a phony custom biome with the given NamespacedKey.
     * @param biomeKey the NamespacedKey of the biome to check
     * @return true if the collector has the biome, false otherwise
     */
    @AsOf("0.0.4")
    public boolean hasBiome(@NotNull NamespacedKey biomeKey) {
        return backing.stream().anyMatch((PhonyCustomBiome biome) -> biome.customBiome().toNamespacedKey().equals(biomeKey));
    }

    /**
     * Removes a phony custom biome from the collector.
     * @param biome the phony custom biome to remove
     * @return true if the biome was removed, false if it was not found
     */
    @AsOf("0.0.4")
    public boolean removeBiome(@NotNull PhonyCustomBiome biome) {
        return backing.remove(biome);
    }

    /**
     * Removes a phony custom biome from the collector by its NamespacedKey.
     * @param biomeKey the NamespacedKey of the biome to remove
     * @return true if the biome was removed, false if it was not found
     */
    @AsOf("0.0.4")
    public boolean removeBiome(@NotNull NamespacedKey biomeKey) {
        return backing.removeIf((PhonyCustomBiome biome) -> biome.customBiome().toNamespacedKey().equals(biomeKey));
    }


    /**
     * Clears all phony custom biomes from the collector.
     */
    @AsOf("0.0.4")
    public void clearBiomes() {
        backing.clear();
    }

    /**
     * Picks the 'best' phony custom biome for the given player and chunk location.
     * @param player the player
     * @param chunkLocation the chunk location
     * @return the best phony custom biome, or null if none match
     */
    @AsOf("0.0.4")
    public @Nullable PhonyCustomBiome bestBiomeFor(@NotNull Player player, @NotNull ChunkLocation chunkLocation) {
        if (backing.isEmpty()) {
            return null;
        }

        return backing.stream()
                .filter((PhonyCustomBiome phony) -> phony.conditional().test(player, chunkLocation))
                .max(Comparator.comparingInt((PhonyCustomBiome phony) -> phony.priority().getLevel()))
                .orElse(null);
    }
}
