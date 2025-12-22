package me.outspending.biomesapi.packet.data;

import net.minecraft.world.level.chunk.LevelChunk;
import org.bukkit.Chunk;

public record ChunkLocation(int x, int z) {

    public static ChunkLocation fromBlockCoords(int blockX, int blockZ) {
        return new ChunkLocation(blockX >> 4, blockZ >> 4);
    }

    public static ChunkLocation of(int chunkX, int chunkZ) {
        return new ChunkLocation(chunkX, chunkZ);
    }

    public boolean isChunk(Chunk bukkitChunk) {
        return this.x == bukkitChunk.getX() && this.z == bukkitChunk.getZ();
    }

    public boolean isChunk(LevelChunk nmsChunk) {
        return this.x == nmsChunk.getPos().x && this.z == nmsChunk.getPos().z;
    }

    public boolean isChunk(int chunkX, int chunkZ) {
        return this.x == chunkX && this.z == chunkZ;
    }
}
