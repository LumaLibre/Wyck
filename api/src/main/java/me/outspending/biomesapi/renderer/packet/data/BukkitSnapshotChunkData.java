package me.outspending.biomesapi.renderer.packet.data;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.misc.ChunkLocation;
import org.bukkit.ChunkSnapshot;
import org.bukkit.block.Biome;
import org.jetbrains.annotations.NotNull;

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
@AsOf("2.2.0")
public record BukkitSnapshotChunkData(@NotNull ChunkLocation location, @NotNull ChunkSnapshot snapshot) implements SnapshotChunkData {

    @Override
    public @NotNull Biome centerBiome() {
        return snapshot.getBiome(7, 0, 7);
    }

    @Override
    public @NotNull Biome biomeAt(int x, int y, int z) {
        return snapshot.getBiome(x, y, z);
    }

    @Override
    public @NotNull Optional<ChunkSnapshot> bukkitSnapshot() {
        return Optional.of(snapshot);
    }
}