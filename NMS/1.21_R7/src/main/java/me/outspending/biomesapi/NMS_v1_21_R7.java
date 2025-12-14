package me.outspending.biomesapi;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.nms.NMS;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import org.bukkit.Chunk;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.lighting.LevelLightEngine;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.CraftChunk;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Supplier;

/**
 * This class provides the implementation for the NMS interface for version 1.21_R3.
 * It provides methods to interact with the game's chunks and biome registry.
 */
public class NMS_v1_21_R7 implements NMS {

    /**
     * Retrieves the registry for a given key.
     *
     * @param key The key for the registry to retrieve.
     * @return The registry associated with the given key.
     */
    private static <T> MappedRegistry<T> getRegistry(ResourceKey<Registry<T>> key) {

        DedicatedServer server = ((CraftServer) Bukkit.getServer()).getServer();
        return (MappedRegistry<T>) server.registryAccess().lookupOrThrow(key);
    }

    /**
     * Updates a list of chunks asynchronously.
     * For each chunk, it creates a packet with the chunk's data and sends it to all players within the chunk's view distance.
     *
     * @param chunks The chunks to update.
     */
    @Override
    public void updateChunks(@NotNull List<Chunk> chunks) {
        CompletableFuture.runAsync(() -> {
            for (Chunk chunk : chunks) {
                LevelChunk levelChunk = (LevelChunk) ((CraftChunk) chunk).getHandle(ChunkStatus.BIOMES);
                LevelLightEngine levelLightEngine = levelChunk.getLevel().getLightEngine();

                ClientboundLevelChunkWithLightPacket packet = new ClientboundLevelChunkWithLightPacket(levelChunk, levelLightEngine, null, null, true);
                for (Player player : getPlayersInDistance(chunk)) {
                    ((CraftPlayer) player).getHandle().connection.send(packet);
                }
            }

        });
    }

    /**
     * Locks or unlocks the biome registry.
     * It uses reflection to access the private boolean field in the registry class and sets its value.
     *
     * @param isLocked true to lock the biome registry, false to unlock it.
     */
    @Override
    public void biomeRegistryLock(boolean isLocked) {
        MappedRegistry<Biome> biomes = getRegistry(Registries.BIOME);
        try {
            Class<?> registryBiomeClass = Class.forName("net.minecraft.core.RegistryMaterials");
            for (Field field : registryBiomeClass.getDeclaredFields()) {
                if (field.getType() == boolean.class) {
                    field.setAccessible(true);
                    field.setBoolean(biomes, isLocked);
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
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
        MappedRegistry<Biome> registry = getRegistry(Registries.BIOME);
        //boolean wasFrozen = true;
        biomeRegistryLock(false);
        supplier.get();

        // Yeah, there's a lot more going on here.
        // I 'heard' there are some new custom biomes api in paper,
        // but I've yet to find it.

        try {
            // We're doing this whole reflection mess to properly re-freeze the registry.
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
     * Retrieves the biome registry from the Minecraft server.
     *
     * This method gets the server instance from the Bukkit API, accesses the registry of the server,
     * and retrieves the biome registry. If the biome registry cannot be retrieved, it throws a RuntimeException.
     *
     * @return The biome registry from the Minecraft server.
     * @throws RuntimeException if the biome registry cannot be retrieved.
     */
    @Override
    public @NotNull Registry<Biome> getRegistry() {
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

            ResourceKey<Biome> biomeKey = ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(namespace, path));
            Optional<Holder.Reference<Biome>> biomeOptional = getRegistry().get(biomeKey);

            Preconditions.checkArgument(biomeOptional.isPresent(), "Biome with namespace " + namespace + ":" + path + " does not exist");

            Holder<Biome> biome = biomeOptional.orElseThrow();

            for (int x = minLoc.getBlockX(); x <= maxLoc.getBlockX(); x++) {
                for (int y = minLoc.getBlockY(); y <= maxLoc.getBlockY(); y++) {
                    for (int z = minLoc.getBlockZ(); z <= maxLoc.getBlockZ(); z++) {
                        Chunk chunk = minLoc.getWorld().getChunkAt(x, z);
                        LevelChunk levelChunk = (LevelChunk) ((CraftChunk) chunk).getHandle(ChunkStatus.BIOMES);

                        levelChunk.setBiome(x, y, z, biome);
                    }
                }
            }

        });
    }

}

