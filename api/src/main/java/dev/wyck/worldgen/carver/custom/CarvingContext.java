package dev.wyck.worldgen.carver.custom;

import dev.wyck.annotations.AsOf;
import dev.wyck.misc.ChunkLocation;
import dev.wyck.wrapper.Wrapper;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Random;

/**
 * The mask-only output context supplied to a {@link CustomCarver}.
 * Minecraft 26.3 owns the block and fluid states produced for carved positions.
 *
 * @param <C> the custom carver configuration type
 * @since 4.0.0
 * @version 4.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("4.0.0")
@ApiStatus.Experimental
public interface CarvingContext<C> extends Wrapper {

    /**
     * The configuration supplied to the custom carver.
     * @return the custom carver configuration
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    C config();

    /**
     * The random source for the current carving operation.
     * @return the carving random source
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    Random random();

    /**
     * The chunk from which the carver started.
     * @return the source chunk location
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    ChunkLocation sourceChunk();

    /**
     * The chunk whose carving mask is being written.
     * @return the target chunk location
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    ChunkLocation chunkLocation();

    /**
     * The minimum generation y-coordinate available to the carver.
     * @return the minimum generation y-coordinate
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    int minGenY();

    /**
     * The maximum generation y-coordinate available to the carver.
     * @return the maximum generation y-coordinate
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    int maxGenY();

    /**
     * Marks an absolute world position as carved when it lies in the target chunk.
     * @param x the absolute x-coordinate
     * @param y the absolute y-coordinate
     * @param z the absolute z-coordinate
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    void carve(int x, int y, int z);

    /**
     * Marks the positions inside an ellipsoid as carved.
     * @param x the absolute x-coordinate of the ellipsoid center
     * @param y the absolute y-coordinate of the ellipsoid center
     * @param z the absolute z-coordinate of the ellipsoid center
     * @param horizontalRadius the horizontal radius of the ellipsoid
     * @param verticalRadius the vertical radius of the ellipsoid
     * @param checker the callback deciding whether an otherwise eligible position is skipped
     * @return whether at least one position was marked as carved
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    boolean carveEllipsoid(
        double x,
        double y,
        double z,
        double horizontalRadius,
        double verticalRadius,
        CarveSkipChecker checker
    );

    /**
     * Marks the positions inside an ellipsoid as carved using the default ellipsoid boundary check.
     * @param x the absolute x-coordinate of the ellipsoid center
     * @param y the absolute y-coordinate of the ellipsoid center
     * @param z the absolute z-coordinate of the ellipsoid center
     * @param horizontalRadius the horizontal radius of the ellipsoid
     * @param verticalRadius the vertical radius of the ellipsoid
     * @return whether at least one position was marked as carved
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    default boolean carveEllipsoid(
        double x,
        double y,
        double z,
        double horizontalRadius,
        double verticalRadius
    ) {
        return carveEllipsoid(
            x,
            y,
            z,
            horizontalRadius,
            verticalRadius,
            (context, xd, yd, zd, worldY) -> xd * xd + yd * yd + zd * zd >= 1.0D
        );
    }

    /**
     * Checks whether a carving path can still reach the current target chunk.
     * @param x the current relative x-coordinate
     * @param z the current relative z-coordinate
     * @param currentStep the current path step
     * @param totalSteps the total number of path steps
     * @param thickness the current carving thickness
     * @return whether the path can still reach the target chunk
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    boolean canReach(double x, double z, int currentStep, int totalSteps, float thickness);

    /**
     * Decides whether a position inside an ellipsoid should remain uncarved.
     *
     * @since 4.0.0
     * @version 4.0.0
     * @author Jsinco
     */
    @AsOf("4.0.0")
    @FunctionalInterface
    interface CarveSkipChecker {

        /**
         * Checks whether an otherwise eligible position should remain uncarved.
         * @param context the active carving context
         * @param xd the normalized x-distance from the ellipsoid center
         * @param yd the normalized y-distance from the ellipsoid center
         * @param zd the normalized z-distance from the ellipsoid center
         * @param y the absolute y-coordinate of the position
         * @return true when the position should remain uncarved
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        boolean shouldSkip(CarvingContext<?> context, double xd, double yd, double zd, int y);
    }
}
