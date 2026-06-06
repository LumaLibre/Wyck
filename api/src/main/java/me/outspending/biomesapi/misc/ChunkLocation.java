package me.outspending.biomesapi.misc;

import me.outspending.biomesapi.annotations.AsOf;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.jspecify.annotations.NullMarked;

import java.util.concurrent.CompletableFuture;

/**
 * A chunk location, represented as (x, z) chunk coordinates.
 *
 * @param x the chunk x coordinate
 * @param z the chunk z coordinate
 * @version 2.0.0
 * @since 0.0.6
 * @author Jsinco
 */
@NullMarked
@AsOf("2.0.0")
public record ChunkLocation(int x, int z) {

    /**
     * Creates a ChunkLocation from chunk coordinates.
     * @param chunkX the chunk x coordinate
     * @param chunkZ the chunk z coordinate
     * @return a ChunkLocation representing the given chunk coordinates
     */
    @AsOf("0.0.6")
    public static ChunkLocation of(int chunkX, int chunkZ) {
        return new ChunkLocation(chunkX, chunkZ);
    }

    /**
     * Creates a ChunkLocation from block coordinates.
     * @param blockX the block x coordinate
     * @param blockZ the block z coordinate
     * @return a ChunkLocation representing the chunk containing the given block coordinates
     */
    @AsOf("0.0.6")
    public static ChunkLocation fromBlockCoords(int blockX, int blockZ) {
        return new ChunkLocation(blockX >> 4, blockZ >> 4);
    }

    /**
     * Checks if this ChunkLocation matches the given Bukkit Chunk.
     * @param bukkitChunk the Bukkit Chunk to compare
     * @return true if the coordinates match, false otherwise
     */
    @AsOf("0.0.6")
    public boolean isChunk(Chunk bukkitChunk) {
        return x == bukkitChunk.getX() && z == bukkitChunk.getZ();
    }

    /**
     * Checks if this ChunkLocation matches the given chunk coordinates.
     * @param chunkX the chunk x coordinate to compare
     * @param chunkZ the chunk z coordinate to compare
     * @return true if the coordinates match, false otherwise
     */
    @AsOf("0.0.6")
    public boolean isChunk(int chunkX, int chunkZ) {
        return x == chunkX && z == chunkZ;
    }

    /**
     * @deprecated Use isChunk(Chunk) or isChunk(int, int) instead.
     * @throws UnsupportedOperationException always
     */
    @Deprecated(forRemoval = true, since = "2.0.0")
    public boolean isChunk(Object obj) {
        throw new UnsupportedOperationException("isChunk(Object) is not supported. Use isChunk(Chunk) or isChunk(int, int) instead.");
    }

    /**
     * Checks if this ChunkLocation is within a certain radius of another ChunkLocation.
     * @param other the other ChunkLocation to compare
     * @param radius the radius to check within
     * @return true if this ChunkLocation is within the radius of the other, false otherwise
     */
    @AsOf("0.0.11")
    public boolean isWithinRadius(ChunkLocation other, int radius) {
        int deltaX = x - other.x;
        int deltaZ = z - other.z;
        return (deltaX * deltaX + deltaZ * deltaZ) <= (radius * radius);
    }

    /**
     * Offsets this ChunkLocation by the given x and z offsets.
     * @param offsetX the x offset
     * @param offsetZ the z offset
     * @return a new ChunkLocation with the offset applied
     */
    @AsOf("0.0.11")
    public ChunkLocation offset(int offsetX, int offsetZ) {
        return new ChunkLocation(x + offsetX, z + offsetZ);
    }

    /**
     * Offsets this ChunkLocation by another ChunkLocation.
     * @param offset the ChunkLocation to offset by
     * @return a new ChunkLocation with the offset applied
     */
    @AsOf("0.0.11")
    public ChunkLocation offset(ChunkLocation offset) {
        return new ChunkLocation(x + offset.x, z + offset.z);
    }

    /**
     * Negates the chunk coordinates.
     * @return a new ChunkLocation with negated x and z coordinates
     */
    @AsOf("0.0.11")
    public ChunkLocation negate() {
        return new ChunkLocation(-x, -z);
    }

    /**
     * Converts this ChunkLocation to a Bukkit Chunk in the given world.
     * @param world the world to get the chunk from
     * @return the Bukkit Chunk at this ChunkLocation in the given world
     */
    @AsOf("1.2.0")
    public CompletableFuture<Chunk> toBukkitChunk(World world) {
        return world.getChunkAtAsync(x, z);
    }

    /**
     * Gets the center block of this chunk in the given world.
     * @param world the world to get the block from
     * @return the center block of this chunk in the given world
     */
    @AsOf("1.2.0")
    public CompletableFuture<Block> centerBlock(World world) {
        return toBukkitChunk(world).thenApply(chunk -> chunk.getBlock(7, 0, 7));
    }

    /**
     * Gets the biome of the center block of this chunk in the given world.
     * @param world the world to get the biome from
     * @return the biome of the center block of this chunk in the given world
     */
    @AsOf("1.2.0")
    public CompletableFuture<Biome> getCenterBiome(World world) {
        return centerBlock(world).thenApply(Block::getBiome);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ChunkLocation that = (ChunkLocation) obj;
        return x == that.x() && z == that.z();
    }

    @Override
    public int hashCode() {
        return 31 * x + z;
    }

    @Override
    public String toString() {
        return "ChunkLocation{x=" + x + ", z=" + z + "}";
    }
}