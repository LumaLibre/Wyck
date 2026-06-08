package me.outspending.biomesapi.renderer.updater;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.BiomesAPI;
import me.outspending.biomesapi.annotations.AsOf;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.lighting.LevelLightEngine;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftChunk;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Implementation of the BiomeUpdater interface.
 *
 * @version 0.0.1
 * @since 0.0.1
 * @author Outspending
 */
@NullMarked
@AsOf("0.0.1")
public class BiomeUpdaterImpl implements BiomeUpdater {

    private final Plugin plugin;

    @AsOf("2.1.0")
    public BiomeUpdaterImpl(Plugin plugin) {
        this.plugin = BiomesAPI.biomesapi().isExternal() ? BiomesAPI.biomesapi().plugin() : plugin;
    }

    @Override
    public void updateChunkAsync(CompletableFuture<Chunk> chunk) {
        updateChunksAsync(List.of(chunk));
    }

    @Override
    public void updateChunks(Location from, Location to) {
        Preconditions.checkNotNull(from, "from cannot be null");
        Preconditions.checkNotNull(to, "to cannot be null");
        List<CompletableFuture<Chunk>> updateChunks = getChunksBetweenLocations(from, to);
        updateChunksAsync(updateChunks);
    }

    @Override
    public void updateChunksAsync(Collection<CompletableFuture<Chunk>> chunks) {
        for (CompletableFuture<Chunk> chunkFuture : chunks) {
            chunkFuture.thenAccept(chunk -> {
                if (plugin != null) {
                    Bukkit.getRegionScheduler().run(plugin, chunk.getWorld(), chunk.getX(), chunk.getZ(), task -> {
                        doUpdateChunk(chunk);
                    });
                } else {
                    doUpdateChunk(chunk);
                }
            }).exceptionally(ex -> {
                ex.printStackTrace();
                return null;
            });
        }
    }


    @Override
    public void updateChunkRadius(Chunk chunk, int radius) {
        List<CompletableFuture<Chunk>> chunks = new ArrayList<>();
        World world = chunk.getWorld();
        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();

        for (int x = chunkX - radius; x <= chunkX + radius; x++) {
            for (int z = chunkZ - radius; z <= chunkZ + radius; z++) {
                chunks.add(world.getChunkAtAsync(x, z));
            }
        }
        updateChunksAsync(chunks);
    }

    @Override
    public void updateChunksForPlayer(Player player) {
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

        updateChunksAsync(chunksToUpdate);
    }

    private void doUpdateChunk(Chunk chunk) {
        LevelChunk levelChunk = (LevelChunk) ((CraftChunk) chunk).getHandle(ChunkStatus.BIOMES);
        LevelLightEngine levelLightEngine = levelChunk.getLevel().getLightEngine();

        ClientboundLevelChunkWithLightPacket packet = new ClientboundLevelChunkWithLightPacket(levelChunk, levelLightEngine, null, null, true);
        for (Player player : getPlayersInDistance(chunk)) {
            ((CraftPlayer) player).getHandle().connection.send(packet);
        }
    }
}
