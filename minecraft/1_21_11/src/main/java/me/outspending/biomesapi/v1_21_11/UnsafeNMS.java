package me.outspending.biomesapi.v1_21_11;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.annotations.AsOf;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.lighting.LevelLightEngine;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.CraftChunk;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * Implementation of UnsafeNMS for Minecraft version 1.21.11.
 * This class provides methods to interact with the Minecraft server's internal mechanics,
 * such as updating chunks and managing the biome registry.
 *
 * @author Jsinco
 * @version 0.0.6
 * @since 0.0.6
 */
@AsOf("0.0.6")
public class UnsafeNMS implements me.outspending.biomesapi.unsafe.UnsafeNMS {

    /**
     * Retrieves the registry for a given key.
     *
     * @param key The key for the registry to retrieve.
     * @return The registry associated with the given key.
     */
    private static <T> MappedRegistry<@NotNull T> getRegistry(ResourceKey<@NotNull Registry<@NotNull T>> key) {
        DedicatedServer server = ((CraftServer) Bukkit.getServer()).getServer();
        return (MappedRegistry<@NotNull T>) server.registryAccess().lookup(key).orElseThrow();
    }

    /**
     * Updates a list of chunks asynchronously.
     * For each chunk, it creates a packet with the chunk's data and sends it to all players within the chunk's view distance.
     *
     * @param chunks The chunks to update.
     */
    @Override
    public void updateChunks(@NotNull List<CompletableFuture<Chunk>> chunks, @Nullable Plugin plugin) {
        CompletableFuture.runAsync(() -> {
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
        }).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });
    }

    private void doUpdateChunk(Chunk chunk) {
        LevelChunk levelChunk = (LevelChunk) ((CraftChunk) chunk).getHandle(ChunkStatus.BIOMES);
        LevelLightEngine levelLightEngine = levelChunk.getLevel().getLightEngine();

        ClientboundLevelChunkWithLightPacket packet = new ClientboundLevelChunkWithLightPacket(levelChunk, levelLightEngine, null, null, true);
        for (Player player : getPlayersInDistance(chunk)) {
            ((CraftPlayer) player).getHandle().connection.send(packet);
        }
    }

    /**
     * Locks or unlocks the biome registry.
     * It uses reflection to access the private boolean field in the registry class and sets its value.
     *
     * @param lock true to lock the biome registry, false to unlock it.
     */
    @Override
    public void biomeRegistryLock(boolean lock) {
        MappedRegistry<@NotNull Biome> biomes = getRegistry(Registries.BIOME);
        try {
            Class<?> registryClass = biomes.getClass();
            Field field = registryClass.getDeclaredField("frozen");
            field.setAccessible(true);
            field.setBoolean(biomes, lock);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to change biome registry lock state", e);
        }
    }

    /**
     * Unlocks the registry, performs an operation supplied by the supplier, and then freezes the registry.
     * This method is useful for performing operations on the registry that require it to be unlocked.
     *
     * @param supplier The supplier that provides the operation to perform on the unlocked registry.
     */
    @Override
    public void unlockRegistry(@NotNull Supplier<?> supplier) {
        MappedRegistry<@NotNull Biome> registry = getRegistry(Registries.BIOME);
        biomeRegistryLock(false);
        supplier.get();

        try {
            // Use reflection to set the 'allTags' field to an unbound TagSet
            Class<?> registryClass = registry.getClass();
            Field field = registryClass.getDeclaredField("allTags");
            field.setAccessible(true);
            Class<?> tagSetClass = Class.forName("net.minecraft.core.MappedRegistry$TagSet");
            Method unboundMethod = tagSetClass.getDeclaredMethod("unbound");
            unboundMethod.setAccessible(true);
            Object unboundTagSet = unboundMethod.invoke(null);
            field.set(registry, unboundTagSet);
        } catch (NoSuchFieldException | IllegalAccessException | ClassNotFoundException | InvocationTargetException |
                 NoSuchMethodException e) {
            e.printStackTrace();
        }
        registry.freeze();
    }

    /**
     * Checks if the biome registry is locked.
     * @return true if the biome registry is locked, false otherwise.
     */
    @Override
    public boolean isBiomeRegistryLocked() {
        MappedRegistry<@NotNull Biome> biomes = getRegistry(Registries.BIOME);
        try {
            Class<?> registryClass = biomes.getClass();
            Field field = registryClass.getDeclaredField("frozen");
            field.setAccessible(true);
            return field.getBoolean(biomes);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to determine biome registry lock state", e);
        }
    }

    /**
     * Retrieves the biome registry from the Minecraft server.
     *
     * This method gets the server instance from the Bukkit API, accesses the registry of the server,
     * and retrieves the biome registry. If the biome registry cannot be retrieved, it throws a RuntimeException.
     *
     * @return The biome registry from the Minecraft server.
     * @throws RuntimeException if the biome registry cannot be retrieved.
     */
    @Override
    public @NotNull Registry<@NotNull Biome> getRegistry() {

        return ((CraftServer) Bukkit.getServer()).getServer()
                .registryAccess()
                .lookup(Registries.BIOME)
                .orElseThrow(() -> new RuntimeException("Could not retrieve biome registry"));
    }

    @Override
    public void updateBiome(@NotNull Location minLoc, @NotNull Location maxLoc, @NotNull NamespacedKey namespacedKey) {
        CompletableFuture.runAsync(() -> {

            String namespace = namespacedKey.getNamespace();
            String path = namespacedKey.getKey();

            ResourceKey<@NotNull Biome> biomeKey = ResourceKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(namespace, path));
            Optional<Holder.Reference<@NotNull Biome>> biomeOptional = getRegistry().get(biomeKey);

            Preconditions.checkArgument(biomeOptional.isPresent(), "Biome with namespace " + namespace + ":" + path + " does not exist");

            Holder<@NotNull Biome> biome = biomeOptional.orElseThrow();

            for (int x = minLoc.getBlockX(); x <= maxLoc.getBlockX(); x++) {
                for (int y = minLoc.getBlockY(); y <= maxLoc.getBlockY(); y++) {
                    for (int z = minLoc.getBlockZ(); z <= maxLoc.getBlockZ(); z++) {
                        final int finalX = x;
                        final int finalY = y;
                        final int finalZ = z;

                        minLoc.getWorld().getChunkAtAsync(x, z).thenAccept(chunk -> {
                            LevelChunk levelChunk = (LevelChunk) ((CraftChunk) chunk).getHandle(ChunkStatus.BIOMES);
                            levelChunk.setBiome(finalX, finalY, finalZ, biome);
                        });
                    }
                }
            }

        });
    }

}

