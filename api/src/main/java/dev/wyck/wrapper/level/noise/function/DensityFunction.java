package dev.wyck.wrapper.level.noise.function;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.WireProvider;
import dev.wyck.keys.AutoKeyed;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.internal.NmsHandle;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * A density function. Either a reference to a registered one or an authored composition of other
 * density functions.
 *
 * @version 2.4.0
 * @since 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
public interface DensityFunction extends NmsHandle, AutoKeyed {

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("dev.wyck.wrapper.level.noise.function.DensityFunctionFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        DensityFunction reference(ResourceKey key);

        DensityFunction constant(double value);

        DensityFunction zero();

        DensityFunction operation(Operation operation, DensityFunction first, DensityFunction second);

        DensityFunction transform(Transform transform, DensityFunction input);

        DensityFunction clamp(DensityFunction input, double min, double max);

        DensityFunction cache(Cache cache, DensityFunction input);

        DensityFunction noise(ResourceKey noiseParameters, double xzScale, double yScale);

        DensityFunction mappedNoise(ResourceKey noiseParameters, double xzScale, double yScale, double minTarget, double maxTarget);

        DensityFunction shift(ShiftKind kind, ResourceKey noiseParameters);

        DensityFunction shiftedNoise2d(DensityFunction shiftX, DensityFunction shiftZ, double xzScale, ResourceKey noiseParameters);

        DensityFunction rangeChoice(DensityFunction input, double minInclusive, double maxExclusive, DensityFunction whenInRange, DensityFunction whenOutOfRange);

        DensityFunction yClampedGradient(int fromY, int toY, double fromValue, double toValue);

        DensityFunction endIslands(long seed);

        DensityFunction blendDensity(DensityFunction input);

        DensityFunction blendAlpha();

        DensityFunction blendOffset();

        DensityFunction findTopSurface(DensityFunction density, DensityFunction upperBound, int lowerBound, int cellHeight);
    }

    /**
     * Two-argument arithmetic operations.
     * @since 2.4.0
     */
    enum Operation {
        ADD,
        MUL,
        MIN,
        MAX
    }

    /**
     * Single-argument value transforms.
     * @since 2.4.0
     */
    enum Transform {
        ABS,
        SQUARE,
        CUBE,
        HALF_NEGATIVE,
        QUARTER_NEGATIVE,
        INVERT,
        SQUEEZE
    }

    /**
     * Caching markers that wrap a function without changing its value.
     * @since 2.4.0
     */
    enum Cache {
        INTERPOLATED,
        FLAT_CACHE,
        CACHE_2D,
        CACHE_ONCE,
        CACHE_ALL_IN_CELL
    }

    /**
     * The three coordinate-shift samplers.
     * @since 2.4.0
     */
    enum ShiftKind {
        SHIFT,
        SHIFT_A,
        SHIFT_B
    }

    /**
     * Adds this density function to another density function.
     * @param other the density function to add
     * @return a new density function that is the sum of this density function and the other density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default DensityFunction add(DensityFunction other) {
        return WIRE.get().operation(Operation.ADD, this, other);
    }

    /**
     * Multiplies this density function by another density function.
     * @param other the density function to multiply by
     * @return a new density function that is the product of this density function and the other density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default DensityFunction mul(DensityFunction other) {
        return WIRE.get().operation(Operation.MUL, this, other);
    }

    /**
     * Returns the minimum of this density function and another density function.
     * @param other the density function to compare with
     * @return a new density function that is the minimum of this density function and the other density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default DensityFunction min(DensityFunction other) {
        return WIRE.get().operation(Operation.MIN, this, other);
    }

    /**
     * Returns the maximum of this density function and another density function.
     * @param other the density function to compare with
     * @return a new density function that is the maximum of this density function and the other density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default DensityFunction max(DensityFunction other) {
        return WIRE.get().operation(Operation.MAX, this, other);
    }

    /**
     * Returns the absolute value of this density function.
     * @param shiftZ 2D noise function to shift the Z axis of
     * @param xzScale scale factor for the X and Z axes
     * @param noiseParameters the noise parameters to use for the noise function
     * @return a new density function that is the absolute value of this density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default DensityFunction shiftedNoise2dX(DensityFunction shiftZ, double xzScale, ResourceKey noiseParameters) {
        return WIRE.get().shiftedNoise2d(this, shiftZ, xzScale, noiseParameters);
    }

    /**
     * Returns the absolute value of this density function.
     * @param shiftX 2D noise function to shift the X axis of
     * @param xzScale scale factor for the X and Z axes
     * @param noiseParameters the noise parameters to use for the noise function
     * @return a new density function that is the absolute value of this density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default DensityFunction shiftedNoise2dZ(DensityFunction shiftX, double xzScale, ResourceKey noiseParameters) {
        return WIRE.get().shiftedNoise2d(shiftX, this, xzScale, noiseParameters);
    }

    /**
     * Returns the absolute value of this density function.
     * @param minInclusive the minimum value of the input function
     * @param maxExclusive the maximum value of the input function
     * @param whenInRange the density function to return if the input function is within the range
     * @param whenOutOfRange the density function to return if the input function is outside the range
     * @return a new density function that is the absolute value of this density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default DensityFunction rangeChoice(double minInclusive, double maxExclusive, DensityFunction whenInRange, DensityFunction whenOutOfRange) {
        return WIRE.get().rangeChoice(this, minInclusive, maxExclusive, whenInRange, whenOutOfRange);
    }

    /**
     * Returns the absolute value of this density function.
     * @return a new density function that is the absolute value of this density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default DensityFunction blendDensity() {
        return WIRE.get().blendDensity(this);
    }

    /**
     * Returns the absolute value of this density function.
     * @param upperBound the upper bound of the surface
     * @param lowerBound the lower bound of the surface
     * @param cellHeight the height of the cell to find the surface in
     * @return a new density function that is the absolute value of this density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default DensityFunction findTopSurface(DensityFunction upperBound, int lowerBound, int cellHeight) {
        return WIRE.get().findTopSurface(this, upperBound, lowerBound, cellHeight);
    }

    /**
     * Creates a reference to a registered density function.
     *
     * @param key the key of an entry in the density-function registry
     * @return a reference to that density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static DensityFunction reference(ResourceKey key) {
        return WIRE.get().reference(key);
    }

    /**
     * Creates a constant density function.
     * @param value the constant value of the function
     * @return a constant density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static DensityFunction constant(double value) {
        return WIRE.get().constant(value);
    }

    /**
     * Creates a density function that always returns 0.
     * @return a density function that always returns 0
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static DensityFunction zero() {
        return WIRE.get().zero();
    }

    /**
     * Creates a new density function by adding two other density functions together.
     * @param first the first density function
     * @param second the second density function
     * @return a new density function that is the sum of the two input functions
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static DensityFunction add(DensityFunction first, DensityFunction second) {
        return WIRE.get().operation(Operation.ADD, first, second);
    }

    /**
     * Creates a new density function by multiplying two other density functions together.
     * @param first the first density function
     * @param second the second density function
     * @return a new density function that is the product of the two input functions
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static DensityFunction mul(DensityFunction first, DensityFunction second) {
        return WIRE.get().operation(Operation.MUL, first, second);
    }

    /**
     * Creates a new density function that is the minimum of two other density functions.
     * @param first the first density function
     * @param second the second density function
     * @return a new density function that is the minimum of the two input functions
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static DensityFunction min(DensityFunction first, DensityFunction second) {
        return WIRE.get().operation(Operation.MIN, first, second);
    }

    /**
     * Creates a new density function that is the maximum of two other density functions.
     * @param first the first density function
     * @param second the second density function
     * @return a new density function that is the maximum of the two input functions
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static DensityFunction max(DensityFunction first, DensityFunction second) {
        return WIRE.get().operation(Operation.MAX, first, second);
    }

    /**
     * Creates a new density function that is the absolute value of the input function.
     * @param noiseParameters the noise parameters to use for the noise function
     * @return a new density function that is the absolute value of the input function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static DensityFunction noise(ResourceKey noiseParameters) {
        return WIRE.get().noise(noiseParameters, 1.0, 1.0);
    }

    /**
     * Creates a new density function that is the absolute value of the input function.
     * @param noiseParameters the noise parameters to use for the noise function
     * @param xzScale the scale factor for the x and z axes
     * @param yScale the scale factor for the y-axis
     * @return a new density function that is the absolute value of the input function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static DensityFunction noise(ResourceKey noiseParameters, double xzScale, double yScale) {
        return WIRE.get().noise(noiseParameters, xzScale, yScale);
    }

    /**
     * Creates a new density function that is the absolute value of the input function.
     * @param noiseParameters the noise parameters to use for the noise function
     * @param minTarget the minimum value of the output function
     * @param maxTarget the maximum value of the output function
     * @return a new density function that is the absolute value of the input function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static DensityFunction mappedNoise(ResourceKey noiseParameters, double minTarget, double maxTarget) {
        return WIRE.get().mappedNoise(noiseParameters, 1.0, 1.0, minTarget, maxTarget);
    }

    /**
     * Creates a new density function that is the absolute value of the input function.
     * @param noiseParameters the noise parameters to use for the noise function
     * @param xzScale the scale factor for the x and z axes
     * @param yScale the scale factor for the y-axis
     * @param minTarget the minimum value of the output function
     * @param maxTarget the maximum value of the output function
     * @return a new density function that is the absolute value of the input function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static DensityFunction mappedNoise(ResourceKey noiseParameters, double xzScale, double yScale, double minTarget, double maxTarget) {
        return WIRE.get().mappedNoise(noiseParameters, xzScale, yScale, minTarget, maxTarget);
    }

    /**
     * Creates a new density function that is the absolute value of the input function.
     * @param noiseParameters the noise parameters to use for the noise function
     * @return a new density function that is the absolute value of the input function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static DensityFunction shift(ResourceKey noiseParameters) {
        return WIRE.get().shift(ShiftKind.SHIFT, noiseParameters);
    }

    /**
     * Creates a new density function that is the absolute value of the input function.
     * @param noiseParameters the noise parameters to use for the noise function
     * @return a new density function that is the absolute value of the input function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static DensityFunction shiftA(ResourceKey noiseParameters) {
        return WIRE.get().shift(ShiftKind.SHIFT_A, noiseParameters);
    }

    /**
     * Creates a new density function that is the absolute value of the input function.
     * @param noiseParameters the noise parameters to use for the noise function
     * @return a new density function that is the absolute value of the input function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static DensityFunction shiftB(ResourceKey noiseParameters) {
        return WIRE.get().shift(ShiftKind.SHIFT_B, noiseParameters);
    }

    /**
     * Creates a new density function that is the absolute value of the input function.
     * @param shiftX 2D noise function to shift the X axis of
     * @param shiftZ 2D noise function to shift the Z axis of
     * @param xzScale scale factor for the X and Z axes
     * @param noiseParameters the noise parameters to use for the noise function
     * @return a new density function that is the absolute value of the input function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static DensityFunction shiftedNoise2d(DensityFunction shiftX, DensityFunction shiftZ, double xzScale, ResourceKey noiseParameters) {
        return WIRE.get().shiftedNoise2d(shiftX, shiftZ, xzScale, noiseParameters);
    }

    /**
     * Creates a new density function that is the absolute value of the input function.
     * @param input the density function to choose from
     * @param minInclusive the minimum value of the input function
     * @param maxExclusive the maximum value of the input function
     * @param whenInRange the density function to return if the input function is within the range
     * @param whenOutOfRange the density function to return if the input function is outside the range
     * @return a new density function that is the absolute value of the input function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static DensityFunction rangeChoice(DensityFunction input, double minInclusive, double maxExclusive, DensityFunction whenInRange, DensityFunction whenOutOfRange) {
        return WIRE.get().rangeChoice(input, minInclusive, maxExclusive, whenInRange, whenOutOfRange);
    }

    /**
     * Creates a new density function that is the absolute value of the input function.
     * @param fromY the minimum Y value of the gradient
     * @param toY the maximum Y value of the gradient
     * @param fromValue the value of the gradient at the minimum Y value
     * @param toValue the value of the gradient at the maximum Y value
     * @return a new density function that is the absolute value of the input function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static DensityFunction yClampedGradient(int fromY, int toY, double fromValue, double toValue) {
        return WIRE.get().yClampedGradient(fromY, toY, fromValue, toValue);
    }

    /**
     * Creates a new density function that is the absolute value of the input function.
     * @param seed the seed to use for the end-islands function
     * @return a new density function that is the absolute value of the input function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static DensityFunction endIslands(long seed) {
        return WIRE.get().endIslands(seed);
    }

    /**
     * Creates a new density function that is the absolute value of the input function.
     * @param input the density function to blend
     * @return a new density function that is the absolute value of the input function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static DensityFunction blendDensity(DensityFunction input) {
        return WIRE.get().blendDensity(input);
    }

    /**
     * Creates a new density function that is the absolute value of the input function.
     * @return a new density function that is the absolute value of the input function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static DensityFunction blendAlpha() {
        return WIRE.get().blendAlpha();
    }

    /**
     * Creates a new density function that is the absolute value of the input function.
     * @return a new density function that is the absolute value of the input function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static DensityFunction blendOffset() {
        return WIRE.get().blendOffset();
    }

    /**
     * Creates a new density function that is the absolute value of the input function.
     * @param density the density function to find the top surface of
     * @param upperBound the upper bound of the surface
     * @param lowerBound the lower bound of the surface
     * @param cellHeight the height of the cell to find the surface in
     * @return a new density function that is the absolute value of the input function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static DensityFunction findTopSurface(DensityFunction density, DensityFunction upperBound, int lowerBound, int cellHeight) {
        return WIRE.get().findTopSurface(density, upperBound, lowerBound, cellHeight);
    }

    /**
     * Creates a new density function that is the absolute value of the input function.
     * @param min the minimum value of the function
     * @param max the maximum value of the function
     * @return a new density function that is the absolute value of the input function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default DensityFunction clamp(double min, double max) {
        return WIRE.get().clamp(this, min, max);
    }

    /**
     * Creates a new density function that is the absolute value of the input function.
     * @return a new density function that is the absolute value of the input function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default DensityFunction abs() {
        return WIRE.get().transform(Transform.ABS, this);
    }

    /**
     * Creates a new density function that is the absolute value of the input function.
     * @return a new density function that is the absolute value of the input function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default DensityFunction square() {
        return WIRE.get().transform(Transform.SQUARE, this);
    }

    /**
     * Creates a new density function that is the absolute value of the input function.
     * @return a new density function that is the absolute value of the input function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default DensityFunction cube() {
        return WIRE.get().transform(Transform.CUBE, this);
    }

    /**
     * Creates a new density function that is the absolute value of the input function.
     * @return a new density function that is the absolute value of the input function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default DensityFunction halfNegative() {
        return WIRE.get().transform(Transform.HALF_NEGATIVE, this);
    }

    /**
     * Creates a new density function that is the absolute value of the input function.
     * @return a new density function that is the absolute value of the input function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default DensityFunction quarterNegative() {
        return WIRE.get().transform(Transform.QUARTER_NEGATIVE, this);
    }

    /**
     * Creates a new density function that is the absolute value of the input function.
     * @return a new density function that is the absolute value of the input function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default DensityFunction invert() {
        return WIRE.get().transform(Transform.INVERT, this);
    }

    /**
     * Creates a new density function that is the absolute value of the input function.
     * @return a new density function that is the absolute value of the input function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default DensityFunction squeeze() {
        return WIRE.get().transform(Transform.SQUEEZE, this);
    }

    /**
     * Creates a new density function that is the absolute value of the input function.
     * @return a new density function that is the absolute value of the input function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default DensityFunction interpolated() {
        return WIRE.get().cache(Cache.INTERPOLATED, this);
    }

    /**
     * Creates a new density function that is the absolute value of the input function.
     * @return a new density function that is the absolute value of the input function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default DensityFunction flatCache() {
        return WIRE.get().cache(Cache.FLAT_CACHE, this);
    }

    /**
     * Creates a new density function that is the absolute value of the input function.
     * @return a new density function that is the absolute value of the input function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default DensityFunction cache2d() {
        return WIRE.get().cache(Cache.CACHE_2D, this);
    }

    /**
     * Creates a new density function that is the absolute value of the input function.
     * @return a new density function that is the absolute value of the input function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default DensityFunction cacheOnce() {
        return WIRE.get().cache(Cache.CACHE_ONCE, this);
    }

    /**
     * Creates a new density function that is the absolute value of the input function.
     * @return a new density function that is the absolute value of the input function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default DensityFunction cacheAllInCell() {
        return WIRE.get().cache(Cache.CACHE_ALL_IN_CELL, this);
    }
}