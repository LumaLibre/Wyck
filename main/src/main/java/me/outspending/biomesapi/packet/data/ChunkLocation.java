package me.outspending.biomesapi.packet.data;

import me.outspending.biomesapi.annotations.AsOf;
import net.minecraft.world.level.chunk.LevelChunk;
import org.bukkit.Chunk;

/**
 * A data class representing a chunk location.
 * @param x the chunk x coordinate
 * @param z the chunk z coordinate
 * @version 0.0.4
 * @since 0.0.4
 * @author Jsinco
 */
@AsOf("0.0.4")
public record ChunkLocation(int x, int z) {

    /**
     * Creates a ChunkLocation from block coordinates.
     * @param blockX the block x coordinate
     * @param blockZ the block z coordinate
     * @return a ChunkLocation representing the chunk containing the given block coordinates
     */
    @AsOf("0.0.4")
    public static ChunkLocation fromBlockCoords(int blockX, int blockZ) {
        return new ChunkLocation(blockX >> 4, blockZ >> 4);
    }

    /**
     * Creates a ChunkLocation from chunk coordinates.
     * @param chunkX the chunk x coordinate
     * @param chunkZ the chunk z coordinate
     * @return a ChunkLocation representing the given chunk coordinates
     */
    @AsOf("0.0.4")
    public static ChunkLocation of(int chunkX, int chunkZ) {
        return new ChunkLocation(chunkX, chunkZ);
    }

    /**
     * Checks if this ChunkLocation matches the given Bukkit Chunk.
     * @param bukkitChunk the Bukkit Chunk to compare
     * @return true if the coordinates match, false otherwise
     */
    @AsOf("0.0.4")
    public boolean isChunk(Chunk bukkitChunk) {
        return this.x == bukkitChunk.getX() && this.z == bukkitChunk.getZ();
    }

    /**
     * Checks if this ChunkLocation matches the given NMS LevelChunk.
     * @param nmsChunk the NMS LevelChunk to compare
     * @return true if the coordinates match, false otherwise
     */
    @AsOf("0.0.4")
    public boolean isChunk(LevelChunk nmsChunk) {
        return this.x == nmsChunk.getPos().x && this.z == nmsChunk.getPos().z;
    }

    /**
     * Checks if this ChunkLocation matches the given chunk coordinates.
     * @param chunkX the chunk x coordinate to compare
     * @param chunkZ the chunk z coordinate to compare
     * @return true if the coordinates match, false otherwise
     */
    @AsOf("0.0.4")
    public boolean isChunk(int chunkX, int chunkZ) {
        return this.x == chunkX && this.z == chunkZ;
    }
}
