package me.outspending.biomesapi.api;

import me.outspending.biomesapi.api.annotations.AsOf;
import me.outspending.biomesapi.api.factory.WireFactories;
import me.outspending.biomesapi.api.factory.WireProvider;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public interface ChunkLocation {

    WireProvider<WireFactories.OfII<ChunkLocation>> FACTORY = WireProvider.create("ChunkLocation");


    /**
     * Creates a ChunkLocation from chunk coordinates.
     * @param chunkX the chunk x coordinate
     * @param chunkZ the chunk z coordinate
     * @return a ChunkLocation representing the given chunk coordinates
     */
    @AsOf("0.0.6")
    static ChunkLocation of(int chunkX, int chunkZ) {
        return FACTORY.get().create(chunkX, chunkZ);
    }

    /**
     * Creates a ChunkLocation from block coordinates.
     * @param blockX the block x coordinate
     * @param blockZ the block z coordinate
     * @return a ChunkLocation representing the chunk containing the given block coordinates
     */
    @AsOf("0.0.6")
    static ChunkLocation fromBlockCoords(int blockX, int blockZ) {
        return of(blockX >> 4, blockZ >> 4);
    }


    int x();

    int z();



    /**
     * Checks if this ChunkLocation matches the given Bukkit Chunk.
     * @param bukkitChunk the Bukkit Chunk to compare
     * @return true if the coordinates match, false otherwise
     */
    @AsOf("0.0.6")
    default boolean isChunk(Chunk bukkitChunk) {
        return this.x() == bukkitChunk.getX() && this.z() == bukkitChunk.getZ();
    }

    /**
     * Checks if this ChunkLocation matches the given NMS LevelChunk.
     * @param nmsChunk the NMS LevelChunk to compare
     * @return true if the coordinates match, false otherwise
     */
    @AsOf("0.0.6")
    boolean isChunk(Object nmsChunk);

    /**
     * Checks if this ChunkLocation matches the given chunk coordinates.
     * @param chunkX the chunk x coordinate to compare
     * @param chunkZ the chunk z coordinate to compare
     * @return true if the coordinates match, false otherwise
     */
    @AsOf("0.0.6")
    default boolean isChunk(int chunkX, int chunkZ) {
        return this.x() == chunkX && this.z() == chunkZ;
    }


    /**
     * Checks if this ChunkLocation is within a certain radius of another ChunkLocation.
     * @param other the other ChunkLocation to compare
     * @param radius the radius to check within
     * @return true if this ChunkLocation is within the radius of the other, false otherwise
     */
    @AsOf("0.0.11")
    default boolean isWithinRadius(ChunkLocation other, int radius) {
        int deltaX = this.x() - other.x();
        int deltaZ = this.z() - other.z();
        return (deltaX * deltaX + deltaZ * deltaZ) <= (radius * radius);
    }

    /**
     * Offsets this ChunkLocation by the given x and z offsets.
     * @param offsetX the x offset
     * @param offsetZ the z offset
     * @return a new ChunkLocation with the offset applied
     */
    @AsOf("0.0.11")
    default @NotNull ChunkLocation offset(int offsetX, int offsetZ) {
        return of(this.x() + offsetX, this.z() + offsetZ);
    }

    /**
     * Offsets this ChunkLocation by another ChunkLocation.
     * @param offset the ChunkLocation to offset by
     * @return a new ChunkLocation with the offset applied
     */
    @AsOf("0.0.11")
    default @NotNull ChunkLocation offset(@NotNull ChunkLocation offset) {
        return of(this.x() + offset.x(), this.z() + offset.z());
    }

    /**
     * Negates the chunk coordinates.
     * @return a new ChunkLocation with negated x and z coordinates
     */
    @AsOf("0.0.11")
    default @NotNull ChunkLocation negate() {
        return of(-this.x(), -this.z());
    }

    /**
     * Converts this ChunkLocation to a Bukkit Chunk in the given world.
     * @param world the world to get the chunk from
     * @return the Bukkit Chunk at this ChunkLocation in the given world
     */
    @AsOf("1.2.0")
    default @NotNull CompletableFuture<Chunk> toBukkitChunk(World world) {
        return world.getChunkAtAsync(this.x(), this.z());
    }

    /**
     * Gets the center block of this chunk in the given world.
     * @param world the world to get the block from
     * @return the center block of this chunk in the given world
     */
    @AsOf("1.2.0")
    default @NotNull CompletableFuture<Block> centerBlock(@NotNull World world) {
        return toBukkitChunk(world).thenApply(chunk -> chunk.getBlock(7, 0, 7));
    }

    /**
     * Gets the biome of the center block of this chunk in the given world.
     * @param world the world to get the biome from
     * @return the biome of the center block of this chunk in the given world
     */
    @AsOf("1.2.0")
    default @NotNull CompletableFuture<Biome> getCenterBiome(@NotNull World world) {
        return centerBlock(world).thenApply(Block::getBiome);
    }

}
