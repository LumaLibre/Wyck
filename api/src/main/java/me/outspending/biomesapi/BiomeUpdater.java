package me.outspending.biomesapi;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.misc.PointRange2D;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Utility class for updating biomes in Minecraft.
 * This class provides methods for updating the biomes of chunks and regions in a Minecraft world.
 * It uses the UnsafeValues instance to perform unsafe operations.
 *
 * @version 1.2.0
 * @since 0.0.1
 * @author Outspending
 */
@AsOf("1.2.0")
public interface BiomeUpdater {

    /**
     * Detects if the server is running Folia by checking for the presence 'io.papermc.paper.threadedregions.RegionizedServer'.
     * @since 1.2.0
     */
    @AsOf("1.2.0")
    boolean FOLIA = classExists("io.papermc.paper.threadedregions.RegionizedServer");

    /**
     * Returns an instance of BiomeUpdater.
     * This method returns an instance of BiomeUpdaterImpl.
     *
     * @return an instance of BiomeUpdater.
     * @since 0.0.2
     * @deprecated {@link #of(Plugin)} is preferred for compatability reasons (Folia).
     */
    @Deprecated
    @AsOf("1.2.0")
    static @NotNull BiomeUpdater of() {
        Preconditions.checkArgument(!FOLIA, "Folia detected, please use BiomeUpdater#of(Plugin) instead.");
        return new BiomeUpdaterImpl(null);
    }

    /**
     * Returns an instance of BiomeUpdater with a plugin reference.
     * @param plugin the plugin to use for scheduling chunk updates. Necessary for Folia compatibility.
     * @return an instance of BiomeUpdater with a plugin reference
     * @since 1.2.0
     */
    @AsOf("1.2.0")
    static @NotNull BiomeUpdater of(@NotNull Plugin plugin) {
        return new BiomeUpdaterImpl(plugin);
    }

    /**
     * Returns a list of chunks between two locations.
     * This method calculates the chunks that are located between the 'from' and 'to' locations.
     * The locations must be in the same world.
     *
     * @param from The starting location.
     * @param to The ending location.
     * @return A list of chunks between the two locations.
     * @since 0.0.1
     */
    @AsOf("1.2.0")
    default @NotNull List<CompletableFuture<Chunk>> getChunksBetweenLocations(@NotNull Location from, @NotNull Location to) {
        if (!from.getWorld().equals(to.getWorld())) {
            throw new IllegalArgumentException("Locations must be in the same world.");
        }

        List<CompletableFuture<Chunk>> chunks = new ArrayList<>();

        PointRange2D range = PointRange2D.of(from, to);
        World world = from.getWorld();
        for (int x = range.minX(); x <= range.maxX(); x += 16) {
            for (int z = range.minZ(); z <= range.maxZ(); z += 16) {
                chunks.add(world.getChunkAtAsync(x >> 4, z >> 4));
            }
        }

        return chunks;
    }

    /**
     * Updates the biome of a chunk.
     * This method is a convenience method that calls the updateChunks method with a list containing the chunk.
     *
     * @param chunk The chunk to update.
     * @since 0.0.1
     */
    @AsOf("1.2.0")
    void updateChunk(@NotNull CompletableFuture<Chunk> chunk);

    /**
     * Updates the biomes of the chunks between two locations.
     * This method is a convenience method that calls the updateChunks method with the chunks between the 'from' and 'to' locations.
     *
     * @param from The starting location.
     * @param to The ending location.
     * @version 0.0.1
     */
    @AsOf("0.0.1")
    @Contract("null, _ -> fail; _, null -> fail")
    void updateChunks(Location from, Location to);

    /**
     * Updates the biomes of a list of chunks within a certain distance.
     * This method sends an update packet to all players within the specified distance of each chunk in the list.
     * The update packet contains the new biome data for the chunk.
     *
     * @param chunks The chunks to update.
     * @since 0.0.1
     */
    @AsOf("1.2.0")
    void updateChunks(@NotNull List<CompletableFuture<Chunk>> chunks);


    /**
     * Updates the biomes of all chunks within a certain radius of a given chunk.
     * This method calculates all chunks within the specified radius and sends an update packet to them.
     *
     * @param chunk The center chunk.
     * @param radius The radius around the chunk to update.
     */
    @AsOf("0.0.15")
    void updateChunkRadius(@NotNull Chunk chunk, int radius);


    /**
     * Updates the biomes of all chunks within the player's view distance.
     * This method calculates all chunks within the player's view distance and sends an update packet to them.
     * @param player the player for whom to update the chunks
     */
    @AsOf("0.0.15")
    void updateChunksForPlayer(@NotNull Player player);


    private static boolean classExists(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
