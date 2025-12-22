package me.outspending.biomesapi;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.nms.NMSHandler;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

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
        NMSHandler.executeNMS(nms -> nms.updateChunks(chunks));
    }

}
