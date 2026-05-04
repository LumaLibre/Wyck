package me.outspending.biomesapi.unsafe;

import me.outspending.biomesapi.annotations.AsOf;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * An interface for accessing unsafe methods in the Minecraft server.
 *
 * @version 0.0.1
 * @since 0.0.1
 * @author Outspending
 */
@AsOf("0.0.1")
public interface UnsafeNMS {

    /**
     * Checks if a given chunk is within the view distance of a player.
     *
     * @param player The player for whom to check the view distance.
     * @param chunk The chunk to check if it's within the player's view distance.
     * @since 0.0.1
     * @return true if the chunk is within the player's view distance, false otherwise.
     */
    @AsOf("0.0.1")
    private boolean inChunkViewDistance(@NotNull Player player, @NotNull Chunk chunk) {
        Location playerLocation = player.getLocation();

        int viewDistance = Bukkit.getViewDistance();
        int playerChunkX = playerLocation.getBlockX() >> 4;
        int playerChunkZ = playerLocation.getBlockZ() >> 4;

        int targetChunkX = chunk.getX();
        int targetChunkZ = chunk.getZ();

        int deltaX = Math.abs(playerChunkX - targetChunkX);
        int deltaZ = Math.abs(playerChunkZ - targetChunkZ);

        return deltaX <= viewDistance && deltaZ <= viewDistance;
    }

    /**
     * Gets a list of players who are within the view distance of a given chunk.
     *
     * @param chunk The chunk for which to get the players within its view distance.
     * @since 0.0.1
     * @return A list of players who are within the view distance of the chunk.
     */
    @AsOf("0.0.1")
    default List<Player> getPlayersInDistance(@NotNull Chunk chunk) {
        World world = chunk.getWorld();

        return world.getPlayers().stream()
                .filter(player -> inChunkViewDistance(player, chunk))
                .toList();
    }

    /**
     * Updates a list of chunks.
     *
     * @param chunks The chunks to update.
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    void updateChunks(@NotNull List<CompletableFuture<Chunk>> chunks, @Nullable Plugin plugin);

    /**
     * Locks or unlocks the biome registry.
     *
     * @apiNote Do not unlock the biome registry and leave it unlocked.
     * @param isLocked true to lock the biome registry, false to unlock it.
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    @ApiStatus.Internal
    void biomeRegistryLock(boolean isLocked);

    /**
     * Unlocks the registry with a given supplier.
     *
     * @param supplier The supplier to use to unlock the registry.
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    void unlockRegistry(@NotNull Supplier<?> supplier);

    /**
     * Checks if the biome registry is locked.
     * @return true if the biome registry is locked, false otherwise.
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    boolean isBiomeRegistryLocked();

    /**
     * Retrieves the biome registry from the Minecraft server.
     *
     * This method gets the server instance from the Bukkit API, accesses the registry of the server,
     * and retrieves the biome registry. If the biome registry cannot be retrieved, it throws a RuntimeException.
     *
     * @return The biome registry from the Minecraft server.
     * @throws RuntimeException if the biome registry cannot be retrieved.
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    @NotNull Object getRegistry();

    /**
     * Updates the biome of a region.
     * @param minLoc 1st corner of the region
     * @param maxLoc 2nd corner of the region
     * @param namespacedKey the biome to set
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    void updateBiome(@NotNull Location minLoc, @NotNull Location maxLoc, @NotNull NamespacedKey namespacedKey);

}
