package me.outspending.biomesapi.misc;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.outspending.biomesapi.annotations.AsOf;
import org.bukkit.Location;
import org.bukkit.World;
import org.jspecify.annotations.NullMarked;

/**
 * Represents a 2D range of points in a Minecraft world.
 * The range is defined by minimum and maximum X and Z coordinates.
 *
 * @version 0.0.1
 * @since 0.0.1
 * @author Outspending
 */
@NullMarked
@AsOf("0.0.1")
public record PointRange2D(int minX, int maxX, int minZ, int maxZ) {

    public static final Codec<PointRange2D> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.INT.fieldOf("minX").forGetter(PointRange2D::minX),
        Codec.INT.fieldOf("maxX").forGetter(PointRange2D::maxX),
        Codec.INT.fieldOf("minZ").forGetter(PointRange2D::minZ),
        Codec.INT.fieldOf("maxZ").forGetter(PointRange2D::maxZ)
    ).apply(instance, PointRange2D::new));

    /**
     * Creates a new PointRange2D from two given locations.
     * The minimum and maximum X and Z coordinates are calculated from the given locations.
     *
     * @param from The first location.
     * @param to The second location.
     * @return A new PointRange2D that spans from the minimum to the maximum coordinates of the given locations.
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    public static PointRange2D of(Location from, Location to) {
        return new PointRange2D(
                Math.min(from.getBlockX(), to.getBlockX()),
                Math.max(from.getBlockX(), to.getBlockX()),
                Math.min(from.getBlockZ(), to.getBlockZ()),
                Math.max(from.getBlockZ(), to.getBlockZ())
        );
    }

    /**
     * Returns the minimum location within the range.
     * The minimum location is defined by the minimum X and Z coordinates.
     * The Y coordinate is always 0.
     *
     * @param world The world in which the location is.
     * @return The minimum location within the range.
     * @since 0.0.2
     */
    @AsOf("0.0.2")
    public Location getMinLocation(World world) {
        return new Location(world, minX, 0, minZ);
    }

    /**
     * Returns the maximum location within the range.
     * The maximum location is defined by the maximum X and Z coordinates.
     * The Y coordinate is always 0.
     *
     * @param world The world in which the location is.
     * @return The maximum location within the range.
     * @since 0.0.2
     */
    @AsOf("0.0.2")
    public Location getMaxLocation(World world) {
        return new Location(world, maxX, 0, maxZ);
    }

}