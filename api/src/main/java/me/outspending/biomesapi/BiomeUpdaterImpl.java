package me.outspending.biomesapi;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.unsafe.UnsafeNMSHandler;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Implementation of the BiomeUpdater interface.
 *
 * @version 0.0.1
 * @since 0.0.1
 * @author Outspending
 */
@AsOf("0.0.1")
public class BiomeUpdaterImpl implements BiomeUpdater {

    private final @Nullable Plugin plugin;

    public BiomeUpdaterImpl(@Nullable Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void updateChunk(@NotNull CompletableFuture<Chunk> chunk) {
        updateChunks(List.of(chunk));
    }

    @Override
    public void updateChunks(Location from, Location to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Locations cannot be null.");
        } else {
            List<CompletableFuture<Chunk>> updateChunks = getChunksBetweenLocations(from, to);

            updateChunks(updateChunks);
        }
    }

    @Override
    public void updateChunks(@NotNull List<CompletableFuture<Chunk>> chunks) {
        UnsafeNMSHandler.executeNMS(nms -> nms.updateChunks(chunks, plugin));
    }


    @Override
    public void updateChunkRadius(@NotNull Chunk chunk, int radius) {
        List<CompletableFuture<Chunk>> chunks = new ArrayList<>();
        World world = chunk.getWorld();
        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();

        for (int x = chunkX - radius; x <= chunkX + radius; x++) {
            for (int z = chunkZ - radius; z <= chunkZ + radius; z++) {
                chunks.add(world.getChunkAtAsync(x, z));
            }
        }
        updateChunks(chunks);
    }

    @Override
    public void updateChunksForPlayer(@NotNull Player player) {
        int viewDistance = player.getViewDistance();
        Location playerLocation = player.getLocation();
        World world = player.getWorld();

        int playerChunkX = playerLocation.getBlockX() >> 4;
        int playerChunkZ = playerLocation.getBlockZ() >> 4;
        List<CompletableFuture<Chunk>> chunksToUpdate = new ArrayList<>();
        for (int x = playerChunkX - viewDistance; x <= playerChunkX + viewDistance; x++) {
            for (int z = playerChunkZ - viewDistance; z <= playerChunkZ + viewDistance; z++) {
                chunksToUpdate.add(world.getChunkAtAsync(x, z));
            }
        }

        updateChunks(chunksToUpdate);
    }
}
