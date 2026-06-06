package me.outspending.biomesapi.renderer.setter;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.misc.PointRange3D;
import me.outspending.biomesapi.renderer.updater.BiomeUpdater;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.RegionAccessor;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import org.jspecify.annotations.NullMarked;

import java.util.concurrent.CompletableFuture;

/**
 * This class provides methods to set the biome of blocks, chunks, and regions in the game.
 *
 * @version 1.0.1
 * @since 0.0.1
 * @author Outspending
 */
@NullMarked
@AsOf("1.0.1")
public class GlobalBiomeSetter implements BiomeSetter {

    private final BiomeUpdater biomeUpdater;

    public GlobalBiomeSetter() {
        this.biomeUpdater = BiomeUpdater.of();
    }

    public GlobalBiomeSetter(Plugin plugin) {
        this.biomeUpdater = BiomeUpdater.of(plugin);
    }

    public GlobalBiomeSetter(BiomeUpdater biomeUpdater) {
        this.biomeUpdater = biomeUpdater;
    }

    @Override
    public void setBlockBiome(Block block, CustomBiome customBiome) {
        setBlockBiome(block, customBiome, false);
    }

    @Override
    public void setBlockBiome(Block block, CustomBiome customBiome, boolean updateBiome) {
        Preconditions.checkNotNull(block, "block cannot be null");
        Preconditions.checkNotNull(customBiome, "customBiome cannot be null");

        Location location = block.getLocation();
        RegionAccessor accessor = getRegionAccessor(location);

        accessor.setBiome(location.getBlockX(), location.getBlockY(), location.getBlockZ(), customBiome.toBukkitBiome());

        if (updateBiome) {
            biomeUpdater.updateChunkAsync(location.getWorld().getChunkAtAsync(location));
        }
    }

    @Override
    public void setChunkBiome(Chunk chunk, CustomBiome customBiome) {
        World.Environment env = chunk.getWorld().getEnvironment();
        int minHeight = getMinHeight(env);
        int maxHeight = getMaxHeight(env);
        setChunkBiome(chunk, minHeight, maxHeight, customBiome);
    }

    @Override
    public void setChunkBiome(Chunk chunk, CustomBiome customBiome, boolean updateBiome) {
        World.Environment env = chunk.getWorld().getEnvironment();
        int minHeight = getMinHeight(env);
        int maxHeight = getMaxHeight(env);
        setChunkBiome(chunk, minHeight, maxHeight, customBiome, updateBiome);
    }

    @Override
    public void setChunkBiome(Chunk chunk, int minHeight, int maxHeight, CustomBiome customBiome) {
        setChunkBiome(chunk, minHeight, maxHeight, customBiome, false);
    }

    @Override
    public void setChunkBiome(Chunk chunk, int minHeight, int maxHeight, CustomBiome customBiome, boolean updateBiome) {
        Preconditions.checkNotNull(chunk, "chunk cannot be null");
        Preconditions.checkNotNull(customBiome, "customBiome cannot be null");

        RegionAccessor accessor = chunk.getWorld();
        Biome biome = customBiome.toBukkitBiome();

        int minX = chunk.getX() << 4;
        int maxX = minX + 16;

        int minZ = chunk.getZ() << 4;
        int maxZ = minZ + 16;

        for (int x = minX; x < maxX; x++) {
            for (int y = minHeight; y < maxHeight; y++) {
                for (int z = minZ; z < maxZ; z++) {
                    // Set the biome of each block to the custom biome
                    accessor.setBiome(x, y, z, biome);
                }
            }
        }

        if (updateBiome) {
            biomeUpdater.updateChunk(CompletableFuture.completedFuture(chunk));
        }
    }

    @Override
    public void setBoundingBoxBiome(World world, BoundingBox boundingBox, CustomBiome customBiome) {
        setRegionBiome(world, boundingBox.getMin(), boundingBox.getMax(), customBiome);
    }

    @Override
    public void setRegionBiome(Location from, Location to, CustomBiome customBiome) {
        World world = from.getWorld();
        if (!world.equals(to.getWorld())) {
            throw new IllegalArgumentException("Locations must be in the same world!");
        }

        setRegionBiome(world, from, to, customBiome, false);
    }

    @Override
    public void setRegionBiome(Location from, Location to, CustomBiome customBiome, boolean updateBiome) {
        World world = from.getWorld();
        if (!world.equals(to.getWorld())) {
            throw new IllegalArgumentException("Locations must be in the same world!");
        }

        setRegionBiome(world, from, to, customBiome, updateBiome);
    }

    @Override
    public void setRegionBiome(World world, Vector from, Vector to, CustomBiome customBiome) {
        setRegionBiome(world, from, to, customBiome, false);
    }

    @Override
    public void setRegionBiome(World world, Vector from, Vector to, CustomBiome customBiome, boolean updateBiome) {
        setRegionBiome(world, from.toLocation(world), to.toLocation(world), customBiome, updateBiome);
    }

    @Override
    public void setRegionBiome(World world, Location from, Location to, CustomBiome customBiome, boolean updateBiome) {
        Preconditions.checkNotNull(world, "world cannot be null");
        Preconditions.checkNotNull(from, "from cannot be null");
        Preconditions.checkNotNull(to, "to cannot be null");
        Preconditions.checkNotNull(customBiome, "customBiome cannot be null");

        Preconditions.checkArgument(!from.getWorld().equals(to.getWorld()), "Locations must be in the same world!");

        Biome biome = customBiome.toBukkitBiome();
        PointRange3D range = PointRange3D.of(from, to);

        for (int x = range.minX(); x <= range.maxX(); x++) {
            for (int y = range.minY(); y <= range.maxY(); y++) {
                for (int z = range.minZ(); z <= range.maxZ(); z++) {
                    world.setBiome(x, y, z, biome);
                }
            }
        }

        if (updateBiome) {
            biomeUpdater.updateChunks(from, to);
        }
    }


    private int getMinHeight(World.Environment environment) {
        return switch (environment) {
            case NORMAL, CUSTOM -> OVERWORLD_MIN_HEIGHT;
            case NETHER, THE_END -> NETHER_END_MIN_HEIGHT;
        };
    }

    private int getMaxHeight(World.Environment environment) {
        return switch (environment) {
            case NORMAL, CUSTOM -> OVERWORLD_MAX_HEIGHT;
            case NETHER, THE_END -> NETHER_END_MAX_HEIGHT;
        };
    }

}
