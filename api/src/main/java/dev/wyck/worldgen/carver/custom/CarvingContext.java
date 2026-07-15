package dev.wyck.worldgen.carver.custom;

import dev.wyck.annotations.AsOf;
import dev.wyck.biome.Biome;
import dev.wyck.misc.ChunkLocation;
import dev.wyck.wrapper.Wrapper;
import org.bukkit.block.data.BlockData;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;
import java.util.Random;

/**
 * The carving surface handed to a CustomCarver during world generation.
 * <p>
 *     Positions are absolute world coordinates unless a method says otherwise.
 *     Only the chunk at {@link #chunkLocation()} is writable and writes outside it are
 *     silently dropped you can check with {@link #canReach}
 * </p>
 *
 * @param <C> the carver's configuration type
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
@ApiStatus.Experimental
public interface CarvingContext<C> extends Wrapper {

    /**
     * The carver's configuration.
     * @return the config instance
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    C config();

    /**
     * A random source seeded for this carve.
     * @return a view of the world generation random
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Random random();

    /**
     * The chunk this carve originated from. Not necessarily the chunk being written.
     * @return the source chunk location
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    ChunkLocation sourceChunk();

    /**
     * The chunk currently being written.
     * @return the target chunk location
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    ChunkLocation chunkLocation();

    /**
     * @return the lowest y-level generation may produce
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int minGenY();

    /**
     * @return the total height of the generation region
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int genDepth();

    /**
     * Carves an axis-aligned ellipsoid centered on the given position, delegating to
     * Minecraft. Blocks resolve to lava below the carver's lava level and to the
     * aquifer's substance elsewhere; surface material is reapplied to exposed dirt.
     * Positions the checker rejects are left alone.
     *
     * @return whether any block was carved
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    boolean carveEllipsoid(double x, double y, double z, double horizontalRadius, double verticalRadius, CarveSkipChecker skipChecker);

    /**
     * Carves an axis-aligned ellipsoid, skipping nothing beyond its own bounds.
     * @return whether any block was carved
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default boolean carveEllipsoid(double x, double y, double z, double horizontalRadius, double verticalRadius) {
        return carveEllipsoid(x, y, z, horizontalRadius, verticalRadius, (ctx, xd, yd, zd, worldY) -> xd * xd + yd * yd + zd * zd >= 1.0D);
    }

    /**
     * Whether a carve of the given thickness could still reach the chunk being written.
     * Walks that keep stepping past a false return waste work.
     *
     * @return whether the chunk is still reachable
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    boolean canReach(double x, double z, int currentStep, int totalSteps, float thickness);

    /**
     * Resolves the state a carve would leave at the given position: lava below the
     * carver's lava level, otherwise the aquifer's substance.
     *
     * @return the carve state, or empty if the aquifer declines to carve
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Optional<BlockData> carveState(int x, int y, int z);

    @AsOf("3.0.0")
    boolean shouldScheduleFluidUpdate();

    /**
     * Gets the block data at the given absolute world position.
     * @return the block data currently at that position
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockData getBlock(int x, int y, int z);

    /**
     * Gets the block data at the given absolute world position.
     * @return the block data currently at that position
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default BlockData getBlock(BlockVector position) {
        return getBlock(position.getBlockX(), position.getBlockY(), position.getBlockZ());
    }

    /**
     * Sets a block at the given absolute world position.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    void setBlock(int x, int y, int z, BlockData data);

    @AsOf("3.0.0")
    Biome biome(int x, int y, int z);

    /**
     * Sets a block at the given absolute world position.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default void setBlock(BlockVector position, BlockData data) {
        setBlock(position.getBlockX(), position.getBlockY(), position.getBlockZ(), data);
    }

    /**
     * Whether the given absolute world position has already been carved this chunk.
     * @return whether the position is masked
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    boolean isCarved(int x, int y, int z);

    /**
     * Marks the given absolute world position as carved. Positions outside the chunk
     * being written are ignored.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    void markCarved(int x, int y, int z);

    /**
     * Whether the carver's replaceable set admits the given state.
     * @return whether the state may be replaced
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    boolean canReplaceBlock(BlockData state);

    /**
     * Whether the chunk being written is upgrading from an older format.
     * @return whether the chunk is upgrading
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    boolean isUpgrading();

    /**
     * Queues the given absolute world position for post-processing. Required after
     * placing a fluid by hand.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    void markPosForPostProcessing(int x, int y, int z);

    /**
     * Resolves the surface material the biome's surface rule would place at the given
     * absolute world position.
     * @deprecated Deprecated in Minecraft.
     * @return the surface material, or empty if the surface rule declines
     * @since 3.0.0
     */
    @Deprecated
    @AsOf("3.0.0")
    Optional<BlockData> topMaterial(int x, int y, int z, boolean underFluid);

    @ApiStatus.Internal
    default boolean isOutsideChunk(int relativeX, int y, int relativeZ) {
        if (relativeX < 0 || relativeX > 15 || relativeZ < 0 || relativeZ > 15) {
            return true;
        }

        return y < this.minGenY() || y >= this.minGenY() + this.genDepth();
    }

    /**
     * Mirror of: {@code net.minecraft.world.level.levelgen.carver.WorldCarver.CarveSkipChecker}.
     * <p>
     *     The deltas are normalized to the ellipsoid: a position lies inside it when
     *     {@code xd * xd + yd * yd + zd * zd < 1.0}. Return true to leave a position
     *     untouched. This is where a cave's cross-sectional shape lives.
     * </p>
     *
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    @FunctionalInterface
    interface CarveSkipChecker {

        @AsOf("3.0.0")
        boolean shouldSkip(CarvingContext<?> context, double xd, double yd, double zd, int y);
    }
}