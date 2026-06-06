package me.outspending.biomesapi.renderer.packet;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.misc.ChunkLocation;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.renderer.packet.data.PhonyCustomBiome;
import me.outspending.biomesapi.renderer.packet.data.SnapshotChunkData;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;

/**
 * A collector for managing PhonyCustomBiome instances.
 *
 * @version 2.2.0
 * @since 0.0.6
 * @author Jsinco
 */
@NullMarked
@AsOf("0.0.6")
public class PhonyCustomBiomeCollector {

    private final Set<PhonyCustomBiome> backing = new HashSet<>();


    /**
     * Appends a phony custom biome to the collector.
     * @param biome the phony custom biome to append
     */
    @AsOf("0.0.6")
    public void appendBiome(PhonyCustomBiome biome) {
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
    public boolean hasBiome(PhonyCustomBiome biome) {
        return backing.contains(biome);
    }

    /**
     * Checks if the collector has a phony custom biome with the given BiomeResourceKey.
     * @param biomeKey the BiomeResourceKey of the biome to check
     * @return true if the collector has the biome, false otherwise
     * @version 0.0.8
     */
    @AsOf("0.0.6")
    public boolean hasBiome(BiomeResourceKey biomeKey) {
        return backing.stream().anyMatch((PhonyCustomBiome biome) -> biome.biomeResourceKey().equals(biomeKey));
    }

    /**
     * Removes a phony custom biome from the collector.
     * @param biome the phony custom biome to remove
     * @return true if the biome was removed, false if it was not found
     */
    @AsOf("0.0.6")
    public boolean removeBiome(PhonyCustomBiome biome) {
        return backing.remove(biome);
    }


    /**
     * Removes a phony custom biome with the given BiomeResourceKey from the collector.
     * @param biomeKey the BiomeResourceKey of the biome to remove
     * @return true if the biome was removed, false if it was not found
     * @version 0.0.8
     */
    @AsOf("0.0.6")
    public boolean removeBiome(BiomeResourceKey biomeKey) {
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
     *
     * <p>Note: this only evaluates the cheap spatial {@link PhonyCustomBiome#conditional()}.
     * Biome-aware conditions are ignored here; use {@link #resolverFor(Player, ChunkLocation)}
     * for the full biome-aware path used by the chunk packet listener.
     *
     * @param player the player
     * @param chunkLocation the chunk location
     * @return the best phony custom biome, or null if none match
     */
    @AsOf("0.0.6")
    public @Nullable PhonyCustomBiome bestBiomeFor(Player player, ChunkLocation chunkLocation) {
        if (backing.isEmpty()) {
            return null;
        }

        return backing.stream()
                .filter((PhonyCustomBiome phony) -> phony.conditional().test(player, chunkLocation))
                .max(Comparator.comparingInt((PhonyCustomBiome phony) -> phony.priority().getLevel()))
                .orElse(null);
    }

    /**
     * Picks the 'best' custom biome for the given player and chunk location (spatial only).
     * @param player the player
     * @param chunkLocation the chunk location
     * @return the best custom biome, or null if none match
     */
    @AsOf("0.0.8")
    public @Nullable CustomBiome bestCustomBiomeFor(Player player, ChunkLocation chunkLocation) {
        PhonyCustomBiome phony = bestBiomeFor(player, chunkLocation);
        return phony != null ? phony.toCustomBiome() : null;
    }

    /**
     * Cheap pre-decode gate. Returns a biome-aware resolver only when at least one phony biome
     * spatially applies to this chunk for this player, otherwise {@code null}.
     *
     * <p>Returning {@code null} is the hot path: the caller skips chunk decoding entirely.
     * The returned resolver is invoked by the NMS handler <em>after</em> it has decoded the
     * chunk once, and evaluates the biome-aware conditions against the decoded data.
     *
     * @param player the player the packet is being sent to
     * @param chunkLocation the chunk being sent
     * @return a resolver, or {@code null} if nothing could apply
     */
    @AsOf("2.2.0")
    public @Nullable PhonyBiomeResolver resolverFor(Player player, ChunkLocation chunkLocation) {
        List<PhonyCustomBiome> candidates = spatialCandidates(player, chunkLocation);
        if (candidates.isEmpty()) {
            return null;
        }
        return snapshot -> {
            PhonyCustomBiome best = bestMatching(candidates, player, snapshot);
            return best == null ? null : best.toCustomBiome();
        };
    }

    /**
     * Stage 1 (cheap, pre-decode): the phony biomes whose spatial {@link PhonyCustomBiome#conditional()}
     * accepts this player + chunk. Needs no biome data, so it can run before the chunk is decoded.
     */
    private List<PhonyCustomBiome> spatialCandidates(Player player, ChunkLocation chunkLocation) {
        if (backing.isEmpty()) {
            return List.of();
        }
        List<PhonyCustomBiome> candidates = new ArrayList<>();
        for (PhonyCustomBiome phony : backing) {
            if (phony.conditional().test(player, chunkLocation)) {
                candidates.add(phony);
            }
        }
        return candidates;
    }

    /**
     * Stage 2 (post-decode): among the spatial candidates, keep those whose biome-aware condition
     * accepts the decoded chunk (a {@code null} condition always passes), then pick the highest priority.
     * Biome reads on {@code snapshot} are lazy + memoized, so candidates without a biome condition
     * never trigger a biome lookup.
     */
    private @Nullable PhonyCustomBiome bestMatching(List<PhonyCustomBiome> candidates, Player player, SnapshotChunkData snapshot) {
        PhonyCustomBiome best = null;
        int bestLevel = Integer.MIN_VALUE;
        for (PhonyCustomBiome phony : candidates) {
            BiPredicate<Player, SnapshotChunkData> biomeCondition = phony.biomeCondition();
            if (biomeCondition != null && !biomeCondition.test(player, snapshot)) {
                continue;
            }
            int level = phony.priority().getLevel();
            if (level > bestLevel) {
                bestLevel = level;
                best = phony;
            }
        }
        return best;
    }
}