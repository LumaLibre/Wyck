package me.outspending.biomesapi.wrapper.level.noise.function;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.keys.AutoKeyed;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
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
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.level.noise.function.DensityFunctionFactoryImpl");

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

    @AsOf("2.4.0")
    static DensityFunction constant(double value) {
        return WIRE.get().constant(value);
    }

    @AsOf("2.4.0")
    static DensityFunction zero() {
        return WIRE.get().zero();
    }

    @AsOf("2.4.0")
    static DensityFunction add(DensityFunction first, DensityFunction second) {
        return WIRE.get().operation(Operation.ADD, first, second);
    }

    @AsOf("2.4.0")
    static DensityFunction mul(DensityFunction first, DensityFunction second) {
        return WIRE.get().operation(Operation.MUL, first, second);
    }

    @AsOf("2.4.0")
    static DensityFunction min(DensityFunction first, DensityFunction second) {
        return WIRE.get().operation(Operation.MIN, first, second);
    }

    @AsOf("2.4.0")
    static DensityFunction max(DensityFunction first, DensityFunction second) {
        return WIRE.get().operation(Operation.MAX, first, second);
    }

    @AsOf("2.4.0")
    static DensityFunction noise(ResourceKey noiseParameters) {
        return WIRE.get().noise(noiseParameters, 1.0, 1.0);
    }

    @AsOf("2.4.0")
    static DensityFunction noise(ResourceKey noiseParameters, double xzScale, double yScale) {
        return WIRE.get().noise(noiseParameters, xzScale, yScale);
    }

    @AsOf("2.4.0")
    static DensityFunction mappedNoise(ResourceKey noiseParameters, double minTarget, double maxTarget) {
        return WIRE.get().mappedNoise(noiseParameters, 1.0, 1.0, minTarget, maxTarget);
    }

    @AsOf("2.4.0")
    static DensityFunction mappedNoise(ResourceKey noiseParameters, double xzScale, double yScale, double minTarget, double maxTarget) {
        return WIRE.get().mappedNoise(noiseParameters, xzScale, yScale, minTarget, maxTarget);
    }

    @AsOf("2.4.0")
    static DensityFunction shift(ResourceKey noiseParameters) {
        return WIRE.get().shift(ShiftKind.SHIFT, noiseParameters);
    }

    @AsOf("2.4.0")
    static DensityFunction shiftA(ResourceKey noiseParameters) {
        return WIRE.get().shift(ShiftKind.SHIFT_A, noiseParameters);
    }

    @AsOf("2.4.0")
    static DensityFunction shiftB(ResourceKey noiseParameters) {
        return WIRE.get().shift(ShiftKind.SHIFT_B, noiseParameters);
    }

    @AsOf("2.4.0")
    static DensityFunction shiftedNoise2d(DensityFunction shiftX, DensityFunction shiftZ, double xzScale, ResourceKey noiseParameters) {
        return WIRE.get().shiftedNoise2d(shiftX, shiftZ, xzScale, noiseParameters);
    }

    @AsOf("2.4.0")
    static DensityFunction rangeChoice(DensityFunction input, double minInclusive, double maxExclusive, DensityFunction whenInRange, DensityFunction whenOutOfRange) {
        return WIRE.get().rangeChoice(input, minInclusive, maxExclusive, whenInRange, whenOutOfRange);
    }

    @AsOf("2.4.0")
    static DensityFunction yClampedGradient(int fromY, int toY, double fromValue, double toValue) {
        return WIRE.get().yClampedGradient(fromY, toY, fromValue, toValue);
    }

    @AsOf("2.4.0")
    static DensityFunction endIslands(long seed) {
        return WIRE.get().endIslands(seed);
    }

    @AsOf("2.4.0")
    static DensityFunction blendDensity(DensityFunction input) {
        return WIRE.get().blendDensity(input);
    }

    @AsOf("2.4.0")
    static DensityFunction blendAlpha() {
        return WIRE.get().blendAlpha();
    }

    @AsOf("2.4.0")
    static DensityFunction blendOffset() {
        return WIRE.get().blendOffset();
    }

    @AsOf("2.4.0")
    static DensityFunction findTopSurface(DensityFunction density, DensityFunction upperBound, int lowerBound, int cellHeight) {
        return WIRE.get().findTopSurface(density, upperBound, lowerBound, cellHeight);
    }

    @AsOf("2.4.0")
    default DensityFunction clamp(double min, double max) {
        return WIRE.get().clamp(this, min, max);
    }

    @AsOf("2.4.0")
    default DensityFunction abs() {
        return WIRE.get().transform(Transform.ABS, this);
    }

    @AsOf("2.4.0")
    default DensityFunction square() {
        return WIRE.get().transform(Transform.SQUARE, this);
    }

    @AsOf("2.4.0")
    default DensityFunction cube() {
        return WIRE.get().transform(Transform.CUBE, this);
    }

    @AsOf("2.4.0")
    default DensityFunction halfNegative() {
        return WIRE.get().transform(Transform.HALF_NEGATIVE, this);
    }

    @AsOf("2.4.0")
    default DensityFunction quarterNegative() {
        return WIRE.get().transform(Transform.QUARTER_NEGATIVE, this);
    }

    @AsOf("2.4.0")
    default DensityFunction invert() {
        return WIRE.get().transform(Transform.INVERT, this);
    }

    @AsOf("2.4.0")
    default DensityFunction squeeze() {
        return WIRE.get().transform(Transform.SQUEEZE, this);
    }

    @AsOf("2.4.0")
    default DensityFunction interpolated() {
        return WIRE.get().cache(Cache.INTERPOLATED, this);
    }

    @AsOf("2.4.0")
    default DensityFunction flatCache() {
        return WIRE.get().cache(Cache.FLAT_CACHE, this);
    }

    @AsOf("2.4.0")
    default DensityFunction cache2d() {
        return WIRE.get().cache(Cache.CACHE_2D, this);
    }

    @AsOf("2.4.0")
    default DensityFunction cacheOnce() {
        return WIRE.get().cache(Cache.CACHE_ONCE, this);
    }

    @AsOf("2.4.0")
    default DensityFunction cacheAllInCell() {
        return WIRE.get().cache(Cache.CACHE_ALL_IN_CELL, this);
    }
}