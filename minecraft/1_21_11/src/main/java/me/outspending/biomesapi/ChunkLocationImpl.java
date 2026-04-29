package me.outspending.biomesapi;

import me.outspending.biomesapi.api.ChunkLocation;
import me.outspending.biomesapi.api.annotations.AsOf;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.NotNull;

/**
 * A data class representing a chunk location.
 * @param x the chunk x coordinate
 * @param z the chunk z coordinate
 * @version 1.2.0
 * @since 0.0.6
 * @author Jsinco
 */
@AsOf("1.2.0")
public class ChunkLocationImpl implements ChunkLocation {

    private final int x;
    private final int z;

    public ChunkLocationImpl(int x, int z) {
        this.x = x;
        this.z = z;
    }

    @Override
    public int x() {
        return x;
    }

    @Override
    public int z() {
        return z;
    }

    @Override
    public boolean isChunk(Object nmsChunk) {
        if (nmsChunk instanceof LevelChunk levelChunk) {
            return this.x == levelChunk.getPos().x && this.z == levelChunk.getPos().z;
        }
        throw new IllegalArgumentException("Invalid NMS chunk type: " + nmsChunk.getClass().getName() + ". Expected net.minecraft.world.level.chunk.LevelChunk.");
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

    public @NotNull String toString() {
        return "ChunkLocation{x=" + x + ", z=" + z + "}";
    }
}
