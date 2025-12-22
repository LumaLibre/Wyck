package me.outspending.biomesapi.packet.data;

public record ChunkBoundingTower(int fromX, int fromZ, int toX, int toZ) {

    public boolean contains(int chunkX, int chunkZ) {
        return chunkX >= fromX && chunkX <= toX && chunkZ >= fromZ && chunkZ <= toZ;
    }

    public boolean contains(ChunkLocation chunkLocation) {
        return contains(chunkLocation.x(), chunkLocation.z());
    }
}
