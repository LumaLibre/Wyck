package dev.wyck.renderer.packet;

import dev.wyck.annotations.AsOf;
import dev.wyck.biome.Biome;
import dev.wyck.keys.ResourceKey;
import dev.wyck.misc.ChunkLocation;
import dev.wyck.renderer.packet.data.SnapshotChunkData;
import dev.wyck.renderer.packet.data.VirtualBiome;
import dev.wyck.misc.BiomePosition;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.BiPredicate;

/**
 * A collector for managing PhonyCustomBiome instances.
 *
 * @version 3.4.0
 * @since 0.0.6
 * @author Jsinco
 */
@NullMarked
@AsOf("0.0.6")
public class VirtualBiomeCollector {

    private static final VirtualBiome[] EMPTY = new VirtualBiome[0];

    private static final Comparator<VirtualBiome> BY_DESCENDING_PRIORITY =
        Comparator.comparingInt((VirtualBiome phony) -> phony.priority().getLevel()).reversed();

    private final Object mutationLock = new Object();

    private volatile VirtualBiome[] backing = EMPTY;

    /**
     * Appends a phony custom biome to the collector.
     * @param biome the phony custom biome to append
     */
    @AsOf("0.0.6")
    public void appendBiome(VirtualBiome biome) {
        synchronized (mutationLock) {
            VirtualBiome[] current = this.backing;
            if (indexOf(current, biome) >= 0) {
                throw new IllegalArgumentException("PhonyCustomBiome with key " + biome.biomeResourceKey() + " is already registered.");
            }
            VirtualBiome[] updated = Arrays.copyOf(current, current.length + 1);
            updated[current.length] = biome;
            Arrays.sort(updated, BY_DESCENDING_PRIORITY);
            this.backing = updated;
        }
    }

    /**
     * Checks if the collector has the given phony custom biome.
     * @param biome the phony custom biome to check
     * @return true if the collector has the biome, false otherwise
     */
    @AsOf("0.0.6")
    public boolean hasBiome(VirtualBiome biome) {
        return indexOf(this.backing, biome) >= 0;
    }

    /**
     * Checks if the collector has a phony custom biome with the given ResourceKey.
     * @param biomeKey the ResourceKey of the biome to check
     * @return true if the collector has the biome, false otherwise
     * @since 0.0.8
     */
    @AsOf("0.0.6")
    public boolean hasBiome(ResourceKey biomeKey) {
        return indexOf(this.backing, biomeKey) >= 0;
    }

    /**
     * Removes a phony custom biome from the collector.
     * @param biome the phony custom biome to remove
     * @return true if the biome was removed, false if it was not found
     */
    @AsOf("0.0.6")
    public boolean removeBiome(VirtualBiome biome) {
        synchronized (mutationLock) {
            VirtualBiome[] current = this.backing;
            int index = indexOf(current, biome);
            if (index < 0) {
                return false;
            }
            this.backing = without(current, index);
            return true;
        }
    }

    /**
     * Removes a phony custom biome with the given ResourceKey from the collector.
     * @param biomeKey the ResourceKey of the biome to remove
     * @return true if the biome was removed, false if it was not found
     * @since 0.0.8
     */
    @AsOf("0.0.6")
    public boolean removeBiome(ResourceKey biomeKey) {
        synchronized (mutationLock) {
            VirtualBiome[] current = this.backing;
            int index = indexOf(current, biomeKey);
            if (index < 0) {
                return false;
            }
            this.backing = without(current, index);
            return true;
        }
    }

    /**
     * Clears all phony custom biomes from the collector.
     */
    @AsOf("0.0.6")
    public void clearBiomes() {
        synchronized (mutationLock) {
            this.backing = EMPTY;
        }
    }

    /**
     * Picks the 'best' phony custom biome for the given player and chunk location.
     *
     * <p>Note: this only evaluates the cheap spatial {@link VirtualBiome#conditional()}.
     * Biome-aware conditions are ignored here; use {@link #resolverFor(Player, ChunkLocation)}
     * for the full biome-aware path used by the chunk packet listener.
     *
     * @param player the player
     * @param chunkLocation the chunk location
     * @return the best phony custom biome, or null if none match
     */
    @AsOf("0.0.6")
    public @Nullable VirtualBiome bestBiomeFor(Player player, ChunkLocation chunkLocation) {
        VirtualBiome[] snapshot = this.backing;
        for (VirtualBiome phony : snapshot) {
            if (phony.positionCondition() == null && phony.conditional().test(player, chunkLocation)) {
                return phony;
            }
        }
        return null;
    }

    /**
     * Picks the highest-priority virtual biome at the supplied block position without decoding
     * chunk data. Biome-aware conditions are not evaluated on this path.
     * @param player the player receiving the packet
     * @param blockX the world block X
     * @param blockY the world block Y
     * @param blockZ the world block Z
     * @return the matching virtual biome, or null if none match
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    public @Nullable VirtualBiome bestBiomeFor(Player player, int blockX, int blockY, int blockZ) {
        VirtualBiome[] snapshot = this.backing;
        if (snapshot.length == 0) {
            return null;
        }

        ChunkLocation location = ChunkLocation.fromBlockCoords(blockX, blockZ);
        // Built only if some candidate actually asks for it; deriving it needs a world lookup.
        BiomePosition position = null;

        for (VirtualBiome phony : snapshot) {
            if (!phony.conditional().test(player, location)) {
                continue;
            }
            BiPredicate<Player, BiomePosition> condition = phony.positionCondition();
            if (condition != null) {
                if (position == null) {
                    position = BiomePosition.fromBlock(
                        location, player.getWorld().getMinHeight() >> 2, blockX, blockY, blockZ
                    );
                }
                if (!condition.test(player, position)) {
                    continue;
                }
            }
            return phony;
        }
        return null;
    }

    // TODO: javadoc
    @AsOf("3.4.0")
    public @Nullable BlockResolver blockResolverFor(Player player, ChunkLocation chunkLocation) {
        VirtualBiome[] candidates = spatialCandidates(player, chunkLocation);
        if (candidates.length == 0) {
            return null;
        }
        return new BlockResolver(candidates, player, chunkLocation, player.getWorld().getMinHeight() >> 2);
    }

    // TODO: javadoc
    @AsOf("3.4.0")
    public static final class BlockResolver {

        private final VirtualBiome[] candidates;
        private final Player player;
        private final ChunkLocation chunkLocation;
        private final int minQuartY;
        private final boolean positionDependent;

        private int lastQuartX = Integer.MIN_VALUE;
        private int lastQuartY;
        private int lastQuartZ;
        private @Nullable VirtualBiome lastResolved;

        private BlockResolver(VirtualBiome[] candidates, Player player, ChunkLocation chunkLocation, int minQuartY) {
            this.candidates = candidates;
            this.player = player;
            this.chunkLocation = chunkLocation;
            this.minQuartY = minQuartY;
            this.positionDependent = anyPositionDependent(candidates);
        }

        @AsOf("3.4.0")
        public @Nullable VirtualBiome resolveAt(int blockX, int blockY, int blockZ) {
            if (!this.positionDependent) {
                return this.candidates[0];
            }

            int quartX = blockX >> 2;
            int quartY = blockY >> 2;
            int quartZ = blockZ >> 2;
            if (quartX == this.lastQuartX && quartY == this.lastQuartY && quartZ == this.lastQuartZ) {
                return this.lastResolved;
            }

            BiomePosition position = BiomePosition.fromBlock(
                this.chunkLocation, this.minQuartY, blockX, blockY, blockZ
            );
            VirtualBiome resolved = bestMatching(this.candidates, this.player, position);

            this.lastQuartX = quartX;
            this.lastQuartY = quartY;
            this.lastQuartZ = quartZ;
            this.lastResolved = resolved;
            return resolved;
        }
    }

    /**
     * Picks the 'best' custom biome for the given player and chunk location (spatial only).
     * @param player the player
     * @param chunkLocation the chunk location
     * @return the best custom biome, or null if none match
     */
    @AsOf("0.0.8")
    public @Nullable Biome bestCustomBiomeFor(Player player, ChunkLocation chunkLocation) {
        VirtualBiome phony = bestBiomeFor(player, chunkLocation);
        return phony != null ? phony.biome() : null;
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
    public @Nullable VirtualBiomeResolver resolverFor(Player player, ChunkLocation chunkLocation) {
        VirtualBiome[] candidates = spatialCandidates(player, chunkLocation);
        if (candidates.length == 0) {
            return null;
        }

        VirtualBiome top = candidates[0];
        if (top.biomeCondition() == null && coversWholeChunk(top, player, chunkLocation)) {
            return new UniformResolver(top);
        }

        if (!anyPositionDependent(candidates)) {
            return new ChunkWideResolver(candidates, player);
        }
        return new PerCellResolver(candidates, player, chunkLocation, player.getWorld().getMinHeight() >> 2);
    }

    private static boolean coversWholeChunk(VirtualBiome biome, Player player, ChunkLocation chunkLocation) {
        if (biome.positionCondition() == null) {
            return true;
        }
        BiPredicate<Player, ChunkLocation> wholeChunk = biome.wholeChunkCondition();
        return wholeChunk != null && wholeChunk.test(player, chunkLocation);
    }

    private VirtualBiome[] spatialCandidates(Player player, ChunkLocation chunkLocation) {
        VirtualBiome[] snapshot = this.backing;
        if (snapshot.length == 0) {
            return EMPTY;
        }

        VirtualBiome[] matches = null;
        int matched = 0;
        for (VirtualBiome phony : snapshot) {
            if (!phony.conditional().test(player, chunkLocation)) {
                continue;
            }
            if (matches == null) {
                matches = new VirtualBiome[snapshot.length];
            }
            matches[matched++] = phony;
        }

        if (matched == 0) {
            return EMPTY;
        }
        // Every biome applied, so the (immutable) snapshot already is the candidate list.
        return matched == snapshot.length ? snapshot : Arrays.copyOf(matches, matched);
    }

    private static boolean anyPositionDependent(VirtualBiome[] candidates) {
        for (VirtualBiome phony : candidates) {
            if (phony.positionCondition() != null) {
                return true;
            }
        }
        return false;
    }

    private static boolean anyBiomeDependent(VirtualBiome[] candidates) {
        for (VirtualBiome phony : candidates) {
            if (phony.biomeCondition() != null) {
                return true;
            }
        }
        return false;
    }

    private static VirtualBiome[] biomeCandidates(VirtualBiome[] candidates, Player player, SnapshotChunkData snapshot) {
        VirtualBiome[] matches = null;
        int matched = 0;
        for (VirtualBiome phony : candidates) {
            BiPredicate<Player, SnapshotChunkData> condition = phony.biomeCondition();
            if (condition != null && !condition.test(player, snapshot)) {
                continue;
            }
            if (matches == null) {
                matches = new VirtualBiome[candidates.length];
            }
            matches[matched++] = phony;
        }

        if (matched == 0) {
            return EMPTY;
        }
        return matched == candidates.length ? candidates : Arrays.copyOf(matches, matched);
    }

    private static @Nullable VirtualBiome bestMatching(VirtualBiome[] candidates, Player player, BiomePosition position) {
        for (VirtualBiome phony : candidates) {
            BiPredicate<Player, BiomePosition> positionCondition = phony.positionCondition();
            if (positionCondition == null || positionCondition.test(player, position)) {
                return phony;
            }
        }
        return null;
    }

    private static int indexOf(VirtualBiome[] backing, VirtualBiome biome) {
        for (int i = 0; i < backing.length; i++) {
            if (backing[i].equals(biome)) {
                return i;
            }
        }
        return -1;
    }

    private static int indexOf(VirtualBiome[] backing, ResourceKey biomeKey) {
        for (int i = 0; i < backing.length; i++) {
            if (backing[i].biomeResourceKey().equals(biomeKey)) {
                return i;
            }
        }
        return -1;
    }

    private static VirtualBiome[] without(VirtualBiome[] backing, int index) {
        VirtualBiome[] updated = new VirtualBiome[backing.length - 1];
        System.arraycopy(backing, 0, updated, 0, index);
        System.arraycopy(backing, index + 1, updated, index, updated.length - index);
        return updated;
    }

    private record UniformResolver(VirtualBiome biome) implements VirtualBiomeResolver {

        @Override
        public @Nullable VirtualBiome resolve(SnapshotChunkData chunkData, int localQuartX, int localQuartY, int localQuartZ) {
            return this.biome;
        }

        @Override
        public boolean positionDependent() {
            return false;
        }
    }

    private static final class ChunkWideResolver implements VirtualBiomeResolver {

        private final VirtualBiome[] candidates;
        private final Player player;

        private @Nullable SnapshotChunkData resolvedFor;
        private @Nullable VirtualBiome resolved;

        ChunkWideResolver(VirtualBiome[] candidates, Player player) {
            this.candidates = candidates;
            this.player = player;
        }

        @Override
        public @Nullable VirtualBiome resolve(SnapshotChunkData chunkData, int localQuartX, int localQuartY, int localQuartZ) {
            if (this.resolvedFor != chunkData) {
                VirtualBiome[] matches = biomeCandidates(this.candidates, this.player, chunkData);
                this.resolved = matches.length == 0 ? null : matches[0];
                this.resolvedFor = chunkData;
            }
            return this.resolved;
        }

        @Override
        public boolean positionDependent() {
            return false;
        }
    }

    private static final class PerCellResolver implements VirtualBiomeResolver {

        private final VirtualBiome[] candidates;
        private final Player player;
        private final ChunkLocation chunkLocation;
        private final int minQuartY;

        private @Nullable SnapshotChunkData preparedSnapshot;
        private VirtualBiome[] preparedCandidates = EMPTY;

        PerCellResolver(VirtualBiome[] candidates, Player player, ChunkLocation chunkLocation, int minQuartY) {
            this.candidates = candidates;
            this.player = player;
            this.chunkLocation = chunkLocation;
            this.minQuartY = minQuartY;
        }

        @Override
        public @Nullable VirtualBiome resolve(SnapshotChunkData chunkData, int localQuartX, int localQuartY, int localQuartZ) {
            if (this.preparedSnapshot != chunkData) {
                this.preparedCandidates = biomeCandidates(this.candidates, this.player, chunkData);
                this.preparedSnapshot = chunkData;
            }
            if (this.preparedCandidates.length == 0) {
                return null;
            }
            BiomePosition position = BiomePosition.fromLocalQuart(
                this.chunkLocation, this.minQuartY, localQuartX, localQuartY, localQuartZ
            );
            return bestMatching(this.preparedCandidates, this.player, position);
        }
    }
}
