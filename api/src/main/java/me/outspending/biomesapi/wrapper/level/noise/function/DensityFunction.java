package me.outspending.biomesapi.wrapper.level.noise.function;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.keys.AutoKeyed;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.registry.worldgen.DensityFunctionRegistry;
import me.outspending.biomesapi.serialization.ConstantRepresentable;
import me.outspending.biomesapi.serialization.StringRepresentable;
import me.outspending.biomesapi.wrapper.internal.NmsDecoder;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Map;

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
public sealed interface DensityFunction extends NmsHandle, AutoKeyed, StringRepresentable permits DensityFunction.Keyed, DensityFunction.BlendAlpha, DensityFunction.BlendDensity, DensityFunction.BlendOffset, DensityFunction.Cache, DensityFunction.Clamp, DensityFunction.Constant, DensityFunction.EndIslands, DensityFunction.FindTopSurface, DensityFunction.MappedNoise, DensityFunction.Noise, DensityFunction.Operation, DensityFunction.RangeChoice, DensityFunction.Reference, DensityFunction.Shift, DensityFunction.ShiftedNoise2d, DensityFunction.Transform, DensityFunction.YClampedGradient, DensityFunction.Zero {

    Codec<DensityFunction> CODEC = Codec.recursive("DensityFunction", self -> {
        Map<String, MapCodec<? extends DensityFunction>> byType = Map.ofEntries(
            Map.entry("reference", Reference.MAP_CODEC),
            Map.entry("constant", Constant.MAP_CODEC),
            Map.entry("zero", Zero.MAP_CODEC),
            Map.entry("operation", Operation.mapCodec(self)),
            Map.entry("transform", Transform.mapCodec(self)),
            Map.entry("clamp", Clamp.mapCodec(self)),
            Map.entry("cache", Cache.mapCodec(self)),
            Map.entry("noise", Noise.MAP_CODEC),
            Map.entry("mapped_noise", MappedNoise.MAP_CODEC),
            Map.entry("shift", Shift.MAP_CODEC),
            Map.entry("shifted_noise_2d", ShiftedNoise2d.mapCodec(self)),
            Map.entry("range_choice", RangeChoice.mapCodec(self)),
            Map.entry("y_clamped_gradient", YClampedGradient.MAP_CODEC),
            Map.entry("end_islands", EndIslands.MAP_CODEC),
            Map.entry("blend_density", BlendDensity.mapCodec(self)),
            Map.entry("blend_alpha", BlendAlpha.MAP_CODEC),
            Map.entry("blend_offset", BlendOffset.MAP_CODEC),
            Map.entry("find_top_surface", FindTopSurface.mapCodec(self))
        );
        return Codec.STRING.dispatch("type", function -> {
            String name = function.type();
            Preconditions.checkState(byType.containsKey(name),
                "density function %s getKey()=\"%s\" not in dispatch map %s",
                function.getClass().getSimpleName(), name, byType.keySet());
            return name;
        }, byType::get);
    });

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.level.noise.function.DensityFunctionFactoryImpl");

    @ApiStatus.Internal
    interface Factory extends NmsDecoder<DensityFunction> {
        Object toNms(DensityFunction function);
    }

    /**
     * The registry key of this density function, or null if it is an unregistered authored composition.
     * @return the key, or null
     * @since 2.4.0
     */
    @Override
    default @Nullable ResourceKey key() {
        return null;
    }

    /**
     * Wraps this density function with a key so it reports {@link #key()} and can be registered.
     * @param key the key to assign
     * @return a keyed view of this density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default DensityFunction withKey(ResourceKey key) {
        return new Keyed(key, this);
    }

    /**
     * Registers this density function with the given key.
     * @param key the key to register with
     * @return this density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default DensityFunction register(ResourceKey key) {
        DensityFunctionRegistry.registry().register(key, this);
        return this;
    }

    @AsOf("2.4.0")
    default DensityFunction register() {
        ResourceKey key = this.key();
        Preconditions.checkState(key != null, "density function must have a key to be registered");
        return register(key);
    }

    @Override
    @AsOf("2.4.0")
    default Object toMinecraft() {
        return WIRE.get().toNms(this);
    }

    @AsOf("2.4.0")
    static DensityFunction fromMinecraft(Object nms) {
        return WIRE.get().fromMinecraft(nms);
    }

    @AsOf("2.4.0")
    enum Op implements ConstantRepresentable {
        ADD("add"),
        MUL("mul"),
        MIN("min"),
        MAX("max");

        public static final Codec<Op> CODEC = ConstantRepresentable.codec(Op.values());

        private final String key;

        Op(String key) {
            this.key = key;
        }

        @Override
        public String getKey() {
            return key;
        }
    }

    @AsOf("2.4.0")
    enum TransformKind implements ConstantRepresentable {
        ABS("abs"),
        SQUARE("square"),
        CUBE("cube"),
        HALF_NEGATIVE("half_negative"),
        QUARTER_NEGATIVE("quarter_negative"),
        INVERT("invert"),
        SQUEEZE("squeeze");

        public static final Codec<TransformKind> CODEC = ConstantRepresentable.codec(TransformKind.values());

        private final String key;

        TransformKind(String key) {
            this.key = key;
        }

        @Override
        public String getKey() {
            return key;
        }
    }

    @AsOf("2.4.0")
    enum CacheKind implements ConstantRepresentable {
        INTERPOLATED("interpolated"),
        FLAT_CACHE("flat_cache"),
        CACHE_2D("cache_2d"),
        CACHE_ONCE("cache_once"),
        CACHE_ALL_IN_CELL("cache_all_in_cell");

        public static final Codec<CacheKind> CODEC = ConstantRepresentable.codec(CacheKind.values());

        private final String key;

        CacheKind(String key) {
            this.key = key;
        }
        @Override
        public String getKey() {
            return key;
        }
    }

    @AsOf("2.4.0")
    enum ShiftKind implements ConstantRepresentable {
        SHIFT("shift"),
        SHIFT_A("shift_a"),
        SHIFT_B("shift_b");

        public static final Codec<ShiftKind> CODEC = ConstantRepresentable.codec(ShiftKind.values());

        private final String key;

        ShiftKind(String key) {
            this.key = key;
        }

        @Override
        public String getKey() {
            return key;
        }
    }


    @AsOf("2.4.0")
    static DensityFunction reference(ResourceKey key) {
        return new Reference(key);
    }

    @AsOf("2.4.0")
    static DensityFunction constant(double value) {
        return new Constant(value);
    }

    @AsOf("2.4.0")
    static DensityFunction zero() {
        return new Zero();
    }

    @AsOf("2.4.0")
    static DensityFunction add(DensityFunction first, DensityFunction second) {
        return new Operation(Op.ADD, first, second);
    }

    @AsOf("2.4.0")
    static DensityFunction mul(DensityFunction first, DensityFunction second) {
        return new Operation(Op.MUL, first, second);
    }

    @AsOf("2.4.0")
    static DensityFunction min(DensityFunction first, DensityFunction second) {
        return new Operation(Op.MIN, first, second);
    }

    @AsOf("2.4.0")
    static DensityFunction max(DensityFunction first, DensityFunction second) {
        return new Operation(Op.MAX, first, second);
    }

    @AsOf("2.4.0")
    static DensityFunction noise(ResourceKey noiseParameters) {
        return new Noise(noiseParameters, 1.0, 1.0);
    }

    @AsOf("2.4.0")
    static DensityFunction noise(ResourceKey noiseParameters, double xzScale, double yScale) {
        return new Noise(noiseParameters, xzScale, yScale);
    }

    @AsOf("2.4.0")
    static DensityFunction mappedNoise(ResourceKey noiseParameters, double minTarget, double maxTarget) {
        return new MappedNoise(noiseParameters, 1.0, 1.0, minTarget, maxTarget);
    }

    @AsOf("2.4.0")
    static DensityFunction mappedNoise(ResourceKey noiseParameters, double xzScale, double yScale, double minTarget, double maxTarget) {
        return new MappedNoise(noiseParameters, xzScale, yScale, minTarget, maxTarget);
    }

    @AsOf("2.4.0")
    static DensityFunction shift(ResourceKey noiseParameters) {
        return new Shift(ShiftKind.SHIFT, noiseParameters);
    }

    @AsOf("2.4.0")
    static DensityFunction shiftA(ResourceKey noiseParameters) {
        return new Shift(ShiftKind.SHIFT_A, noiseParameters);
    }

    @AsOf("2.4.0")
    static DensityFunction shiftB(ResourceKey noiseParameters) {
        return new Shift(ShiftKind.SHIFT_B, noiseParameters);
    }

    @AsOf("2.4.0")
    static DensityFunction shiftedNoise2d(DensityFunction shiftX, DensityFunction shiftZ, double xzScale, ResourceKey noiseParameters) {
        return new ShiftedNoise2d(shiftX, shiftZ, xzScale, noiseParameters);
    }

    @AsOf("2.4.0")
    static DensityFunction rangeChoice(DensityFunction input, double minInclusive, double maxExclusive, DensityFunction whenInRange, DensityFunction whenOutOfRange) {
        return new RangeChoice(input, minInclusive, maxExclusive, whenInRange, whenOutOfRange);
    }

    @AsOf("2.4.0")
    static DensityFunction yClampedGradient(int fromY, int toY, double fromValue, double toValue) {
        return new YClampedGradient(fromY, toY, fromValue, toValue);
    }

    @AsOf("2.4.0")
    static DensityFunction endIslands(long seed) {
        return new EndIslands(seed);
    }

    @AsOf("2.4.0")
    static DensityFunction blendDensity(DensityFunction input) {
        return new BlendDensity(input);
    }

    @AsOf("2.4.0")
    static DensityFunction blendAlpha() {
        return new BlendAlpha();
    }

    @AsOf("2.4.0")
    static DensityFunction blendOffset() {
        return new BlendOffset();
    }

    @AsOf("2.4.0")
    static DensityFunction findTopSurface(DensityFunction density, DensityFunction upperBound, int lowerBound, int cellHeight) {
        return new FindTopSurface(density, upperBound, lowerBound, cellHeight);
    }

    @AsOf("2.4.0")
    static DensityFunction interpolated(DensityFunction input) {
        return new Cache(CacheKind.INTERPOLATED, input);
    }

    @AsOf("2.4.0")
    static DensityFunction flatCache(DensityFunction input) {
        return new Cache(CacheKind.FLAT_CACHE, input);
    }

    @AsOf("2.4.0")
    static DensityFunction cache2d(DensityFunction input) {
        return new Cache(CacheKind.CACHE_2D, input);
    }

    @AsOf("2.4.0")
    static DensityFunction cacheOnce(DensityFunction input) {
        return new Cache(CacheKind.CACHE_ONCE, input);
    }

    @AsOf("2.4.0")
    static DensityFunction cacheAllInCell(DensityFunction input) {
        return new Cache(CacheKind.CACHE_ALL_IN_CELL, input);
    }

    ///

    @AsOf("2.4.0")
    default DensityFunction add(DensityFunction other) {
        return new Operation(Op.ADD, this, other);
    }

    @AsOf("2.4.0")
    default DensityFunction mul(DensityFunction other) {
        return new Operation(Op.MUL, this, other);
    }

    @AsOf("2.4.0")
    default DensityFunction min(DensityFunction other) {
        return new Operation(Op.MIN, this, other);
    }

    @AsOf("2.4.0")
    default DensityFunction max(DensityFunction other) {
        return new Operation(Op.MAX, this, other);
    }

    @AsOf("2.4.0")
    default DensityFunction shiftedNoise2dX(DensityFunction shiftZ, double xzScale, ResourceKey noiseParameters) {
        return new ShiftedNoise2d(this, shiftZ, xzScale, noiseParameters);
    }

    @AsOf("2.4.0")
    default DensityFunction shiftedNoise2dZ(DensityFunction shiftX, double xzScale, ResourceKey noiseParameters) {
        return new ShiftedNoise2d(shiftX, this, xzScale, noiseParameters);
    }

    @AsOf("2.4.0")
    default DensityFunction rangeChoice(double minInclusive, double maxExclusive, DensityFunction whenInRange, DensityFunction whenOutOfRange) {
        return new RangeChoice(this, minInclusive, maxExclusive, whenInRange, whenOutOfRange);
    }

    @AsOf("2.4.0")
    default DensityFunction blendDensity() {
        return new BlendDensity(this);
    }

    @AsOf("2.4.0")
    default DensityFunction findTopSurface(DensityFunction upperBound, int lowerBound, int cellHeight) {
        return new FindTopSurface(this, upperBound, lowerBound, cellHeight);
    }

    @AsOf("2.4.0")
    default DensityFunction clamp(double min, double max) {
        return new Clamp(this, min, max);
    }

    @AsOf("2.4.0")
    default DensityFunction abs() {
        return new Transform(TransformKind.ABS, this);
    }

    @AsOf("2.4.0")
    default DensityFunction square() {
        return new Transform(TransformKind.SQUARE, this);
    }

    @AsOf("2.4.0")
    default DensityFunction cube() {
        return new Transform(TransformKind.CUBE, this);
    }

    @AsOf("2.4.0")
    default DensityFunction halfNegative() {
        return new Transform(TransformKind.HALF_NEGATIVE, this);
    }

    @AsOf("2.4.0")
    default DensityFunction quarterNegative() {
        return new Transform(TransformKind.QUARTER_NEGATIVE, this);
    }

    @AsOf("2.4.0")
    default DensityFunction invert() {
        return new Transform(TransformKind.INVERT, this);
    }

    @AsOf("2.4.0")
    default DensityFunction squeeze() {
        return new Transform(TransformKind.SQUEEZE, this);
    }

    @AsOf("2.4.0")
    default DensityFunction interpolated() {
        return new Cache(CacheKind.INTERPOLATED, this);
    }

    @AsOf("2.4.0")
    default DensityFunction flatCache() {
        return new Cache(CacheKind.FLAT_CACHE, this);
    }

    @AsOf("2.4.0")
    default DensityFunction cache2d() {
        return new Cache(CacheKind.CACHE_2D, this);
    }

    @AsOf("2.4.0")
    default DensityFunction cacheOnce() {
        return new Cache(CacheKind.CACHE_ONCE, this);
    }

    @AsOf("2.4.0")
    default DensityFunction cacheAllInCell() {
        return new Cache(CacheKind.CACHE_ALL_IN_CELL, this);
    }

    @AsOf("2.4.0")
    record Keyed(@Override ResourceKey key, DensityFunction delegate) implements DensityFunction {
        @Override
        public Object toMinecraft() {
            return delegate.toMinecraft();
        }
    }

    @AsOf("2.4.0")
    record Reference(ResourceKey reference) implements DensityFunction {
        public static final MapCodec<Reference> MAP_CODEC =
            ResourceKey.CODEC.fieldOf("key").xmap(Reference::new, Reference::reference);
        @Override
        public ResourceKey key() {
            return this.reference;
        }
    }

    @AsOf("2.4.0")
    record Constant(double value) implements DensityFunction {
        public static final MapCodec<Constant> MAP_CODEC =
            Codec.DOUBLE.fieldOf("value").xmap(Constant::new, Constant::value);
    }

    @AsOf("2.4.0")
    record Zero() implements DensityFunction {
        public static final MapCodec<Zero> MAP_CODEC = MapCodec.unit(Zero::new);
    }

    @AsOf("2.4.0")
    record Operation(Op op, DensityFunction first, DensityFunction second) implements DensityFunction {
        public static MapCodec<Operation> mapCodec(Codec<DensityFunction> self) {
            return RecordCodecBuilder.mapCodec(i -> i.group(
                Op.CODEC.fieldOf("op").forGetter(Operation::op),
                self.fieldOf("first").forGetter(Operation::first),
                self.fieldOf("second").forGetter(Operation::second)
            ).apply(i, Operation::new));
        }
    }

    @AsOf("2.4.0")
    record Transform(TransformKind transform, DensityFunction input) implements DensityFunction {
        public static MapCodec<Transform> mapCodec(Codec<DensityFunction> self) {
            return RecordCodecBuilder.mapCodec(i -> i.group(
                TransformKind.CODEC.fieldOf("transform").forGetter(Transform::transform),
                self.fieldOf("input").forGetter(Transform::input)
            ).apply(i, Transform::new));
        }
    }

    @AsOf("2.4.0")
    record Clamp(DensityFunction input, double min, double max) implements DensityFunction {
        public static MapCodec<Clamp> mapCodec(Codec<DensityFunction> self) {
            return RecordCodecBuilder.mapCodec(i -> i.group(
                self.fieldOf("input").forGetter(Clamp::input),
                Codec.DOUBLE.fieldOf("min").forGetter(Clamp::min),
                Codec.DOUBLE.fieldOf("max").forGetter(Clamp::max)
            ).apply(i, Clamp::new));
        }
    }

    @AsOf("2.4.0")
    record Cache(CacheKind cache, DensityFunction input) implements DensityFunction {
        public static MapCodec<Cache> mapCodec(Codec<DensityFunction> self) {
            return RecordCodecBuilder.mapCodec(i -> i.group(
                CacheKind.CODEC.fieldOf("cache").forGetter(Cache::cache),
                self.fieldOf("input").forGetter(Cache::input)
            ).apply(i, Cache::new));
        }
    }

    @AsOf("2.4.0")
    record Noise(ResourceKey noiseParameters, double xzScale, double yScale) implements DensityFunction {
        public static final MapCodec<Noise> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            ResourceKey.CODEC.fieldOf("noise").forGetter(Noise::noiseParameters),
            Codec.DOUBLE.fieldOf("xz_scale").forGetter(Noise::xzScale),
            Codec.DOUBLE.fieldOf("y_scale").forGetter(Noise::yScale)
        ).apply(i, Noise::new));
    }

    @AsOf("2.4.0")
    record MappedNoise(ResourceKey noiseParameters, double xzScale, double yScale, double minTarget, double maxTarget) implements DensityFunction {
        public static final MapCodec<MappedNoise> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            ResourceKey.CODEC.fieldOf("noise").forGetter(MappedNoise::noiseParameters),
            Codec.DOUBLE.fieldOf("xz_scale").forGetter(MappedNoise::xzScale),
            Codec.DOUBLE.fieldOf("y_scale").forGetter(MappedNoise::yScale),
            Codec.DOUBLE.fieldOf("min_target").forGetter(MappedNoise::minTarget),
            Codec.DOUBLE.fieldOf("max_target").forGetter(MappedNoise::maxTarget)
        ).apply(i, MappedNoise::new));
    }

    @AsOf("2.4.0")
    record Shift(ShiftKind kind, ResourceKey noiseParameters) implements DensityFunction {
        public static final MapCodec<Shift> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            ShiftKind.CODEC.fieldOf("kind").forGetter(Shift::kind),
            ResourceKey.CODEC.fieldOf("noise").forGetter(Shift::noiseParameters)
        ).apply(i, Shift::new));
    }

    @AsOf("2.4.0")
    record ShiftedNoise2d(DensityFunction shiftX, DensityFunction shiftZ, double xzScale, ResourceKey noiseParameters) implements DensityFunction {
        public static MapCodec<ShiftedNoise2d> mapCodec(Codec<DensityFunction> self) {
            return RecordCodecBuilder.mapCodec(i -> i.group(
                self.fieldOf("shift_x").forGetter(ShiftedNoise2d::shiftX),
                self.fieldOf("shift_z").forGetter(ShiftedNoise2d::shiftZ),
                Codec.DOUBLE.fieldOf("xz_scale").forGetter(ShiftedNoise2d::xzScale),
                ResourceKey.CODEC.fieldOf("noise").forGetter(ShiftedNoise2d::noiseParameters)
            ).apply(i, ShiftedNoise2d::new));
        }
    }

    @AsOf("2.4.0")
    record RangeChoice(DensityFunction input, double minInclusive, double maxExclusive,
                       DensityFunction whenInRange, DensityFunction whenOutOfRange) implements DensityFunction {
        public static MapCodec<RangeChoice> mapCodec(Codec<DensityFunction> self) {
            return RecordCodecBuilder.mapCodec(i -> i.group(
                self.fieldOf("input").forGetter(RangeChoice::input),
                Codec.DOUBLE.fieldOf("min_inclusive").forGetter(RangeChoice::minInclusive),
                Codec.DOUBLE.fieldOf("max_exclusive").forGetter(RangeChoice::maxExclusive),
                self.fieldOf("when_in_range").forGetter(RangeChoice::whenInRange),
                self.fieldOf("when_out_of_range").forGetter(RangeChoice::whenOutOfRange)
            ).apply(i, RangeChoice::new));
        }
    }

    @AsOf("2.4.0")
    record YClampedGradient(int fromY, int toY, double fromValue, double toValue) implements DensityFunction {
        public static final MapCodec<YClampedGradient> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codec.INT.fieldOf("from_y").forGetter(YClampedGradient::fromY),
            Codec.INT.fieldOf("to_y").forGetter(YClampedGradient::toY),
            Codec.DOUBLE.fieldOf("from_value").forGetter(YClampedGradient::fromValue),
            Codec.DOUBLE.fieldOf("to_value").forGetter(YClampedGradient::toValue)
        ).apply(i, YClampedGradient::new));
    }

    @AsOf("2.4.0")
    record EndIslands(long seed) implements DensityFunction {
        public static final MapCodec<EndIslands> MAP_CODEC =
            Codec.LONG.fieldOf("seed").xmap(EndIslands::new, EndIslands::seed);
    }

    @AsOf("2.4.0")
    record BlendDensity(DensityFunction input) implements DensityFunction {
        public static MapCodec<BlendDensity> mapCodec(Codec<DensityFunction> self) {
            return self.fieldOf("input").xmap(BlendDensity::new, BlendDensity::input);
        }
    }

    @AsOf("2.4.0")
    record BlendAlpha() implements DensityFunction {
        public static final MapCodec<BlendAlpha> MAP_CODEC = MapCodec.unit(BlendAlpha::new);
    }

    @AsOf("2.4.0")
    record BlendOffset() implements DensityFunction {
        public static final MapCodec<BlendOffset> MAP_CODEC = MapCodec.unit(BlendOffset::new);
    }

    @AsOf("2.4.0")
    record FindTopSurface(DensityFunction density, DensityFunction upperBound, int lowerBound, int cellHeight) implements DensityFunction {
        public static MapCodec<FindTopSurface> mapCodec(Codec<DensityFunction> self) {
            return RecordCodecBuilder.mapCodec(i -> i.group(
                self.fieldOf("density").forGetter(FindTopSurface::density),
                self.fieldOf("upper_bound").forGetter(FindTopSurface::upperBound),
                Codec.INT.fieldOf("lower_bound").forGetter(FindTopSurface::lowerBound),
                Codec.INT.fieldOf("cell_height").forGetter(FindTopSurface::cellHeight)
            ).apply(i, FindTopSurface::new));
        }
    }
}
