package me.outspending.biomesapi;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.unsafe.UnsafeNMSHandler;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the BiomeUpdater interface.
 *
 * @version 0.0.1
 * @since 0.0.1
 * @author Outspending
 */
@AsOf("0.0.1")
public class BiomeUpdaterImpl implements BiomeUpdater {

    @Override
    public void updateChunk(@NotNull Chunk chunk) {
        updateChunks(List.of(chunk));
    }

    @Override
    public void updateChunks(Location from, Location to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Locations cannot be null.");
        } else {
            List<Chunk> updateChunks = getChunksBetweenLocations(from, to);

            updateChunks(updateChunks);
        }
    }

    @Override
    public void updateChunks(@NotNull List<Chunk> chunks) {
        UnsafeNMSHandler.executeNMS(nms -> nms.updateChunks(chunks));
    }


    @Override
    public void updateChunkRadius(@NotNull Chunk chunk, int radius) {
        List<Chunk> chunks = new ArrayList<>();
        World world = chunk.getWorld();
        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();

        for (int x = chunkX - radius; x <= chunkX + radius; x++) {
            for (int z = chunkZ - radius; z <= chunkZ + radius; z++) {
                chunks.add(world.getChunkAt(x, z));
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
        List<Chunk> chunksToUpdate = new ArrayList<>();
        for (int x = playerChunkX - viewDistance; x <= playerChunkX + viewDistance; x++) {
            for (int z = playerChunkZ - viewDistance; z <= playerChunkZ + viewDistance; z++) {
                chunksToUpdate.add(world.getChunkAt(x, z));
            }
        }

        updateChunks(chunksToUpdate);
    }
}
