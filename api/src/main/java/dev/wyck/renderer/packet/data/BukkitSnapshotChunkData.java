package dev.wyck.renderer.packet.data;

import dev.wyck.annotations.AsOf;
import dev.wyck.misc.ChunkLocation;
import org.bukkit.ChunkSnapshot;
import org.bukkit.block.Biome;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

/**
 * A {@link SnapshotChunkData} backed by a real Bukkit {@link ChunkSnapshot}.
 *
 * <p>This backing exposes full block data, but it can only be constructed by a caller that
 * already holds a snapshot. Obtaining a snapshot ({@code world.getChunkAt(...).getChunkSnapshot()})
 * touches the live world and must be done on the main thread  <strong>never</strong> on the
 * Netty thread inside a packet listener. Use this only when you legitimately have a snapshot in
 * hand; otherwise the packet-backed {@code NmsSnapshotChunkData} is the correct, safe choice.
 *
 * @version 2.2.0
 * @since 2.2.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.2.0")
public record BukkitSnapshotChunkData(ChunkLocation location, ChunkSnapshot snapshot) implements SnapshotChunkData {

    @Override
    public Biome centerBiome() {
        return snapshot.getBiome(7, 0, 7);
    }

    @Override
    public Biome biomeAt(int x, int y, int z) {
        return snapshot.getBiome(x, y, z);
    }

    @Override
    public Optional<ChunkSnapshot> bukkitSnapshot() {
        return Optional.of(snapshot);
    }
}