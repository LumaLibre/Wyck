package me.outspending.biomesapi.wrapper.level.noise.function;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.registry.bootstrap.util.BootstrapSafeMinecraftRegistries;
import me.outspending.biomesapi.util.internal.InternalReflectUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@ApiStatus.Internal
public final class DensityFunctionFactoryImpl implements DensityFunction.Factory {

    @Override
    public Object toNms(DensityFunction function) {
        return switch (function) {
            case DensityFunction.Keyed k -> toNms(k.delegate());
            case DensityFunction.Reference r -> new DensityFunctions.HolderHolder(densityHolder(r.reference()));
            case DensityFunction.Constant c -> DensityFunctions.constant(c.value());
            case DensityFunction.Zero ignored -> DensityFunctions.zero();
            case DensityFunction.Operation o -> {
                net.minecraft.world.level.levelgen.DensityFunction a = unwrap(o.first());
                net.minecraft.world.level.levelgen.DensityFunction b = unwrap(o.second());
                yield switch (o.op()) {
                    case ADD -> DensityFunctions.add(a, b);
                    case MUL -> DensityFunctions.mul(a, b);
                    case MIN -> DensityFunctions.min(a, b);
                    case MAX -> DensityFunctions.max(a, b);
                };
            }
            case DensityFunction.Transform t -> {
                net.minecraft.world.level.levelgen.DensityFunction in = unwrap(t.input());
                yield switch (t.transform()) {
                    case ABS -> in.abs();
                    case SQUARE -> in.square();
                    case CUBE -> in.cube();
                    case HALF_NEGATIVE -> in.halfNegative();
                    case QUARTER_NEGATIVE -> in.quarterNegative();
                    case INVERT -> in.invert();
                    case SQUEEZE -> in.squeeze();
                };
            }
            case DensityFunction.Clamp c -> unwrap(c.input()).clamp(c.min(), c.max());
            case DensityFunction.Cache c -> {
                net.minecraft.world.level.levelgen.DensityFunction in = unwrap(c.input());
                yield switch (c.cache()) {
                    case INTERPOLATED -> DensityFunctions.interpolated(in);
                    case FLAT_CACHE -> DensityFunctions.flatCache(in);
                    case CACHE_2D -> DensityFunctions.cache2d(in);
                    case CACHE_ONCE -> DensityFunctions.cacheOnce(in);
                    case CACHE_ALL_IN_CELL -> DensityFunctions.cacheAllInCell(in);
                };
            }
            case DensityFunction.Noise n -> DensityFunctions.noise(noiseHolder(n.noiseParameters()), n.xzScale(), n.yScale());
            case DensityFunction.MappedNoise n -> DensityFunctions.mappedNoise(noiseHolder(n.noiseParameters()), n.xzScale(), n.yScale(), n.minTarget(), n.maxTarget());
            case DensityFunction.Shift s -> {
                Holder<NormalNoise.NoiseParameters> holder = noiseHolder(s.noiseParameters());
                yield switch (s.kind()) {
                    case SHIFT -> DensityFunctions.shift(holder);
                    case SHIFT_A -> DensityFunctions.shiftA(holder);
                    case SHIFT_B -> DensityFunctions.shiftB(holder);
                };
            }
            case DensityFunction.ShiftedNoise2d s -> DensityFunctions.shiftedNoise2d(unwrap(s.shiftX()), unwrap(s.shiftZ()), s.xzScale(), noiseHolder(s.noiseParameters()));
            case DensityFunction.RangeChoice r -> DensityFunctions.rangeChoice(
                unwrap(r.input()), r.minInclusive(), r.maxExclusive(), unwrap(r.whenInRange()), unwrap(r.whenOutOfRange()));
            case DensityFunction.YClampedGradient g -> DensityFunctions.yClampedGradient(g.fromY(), g.toY(), g.fromValue(), g.toValue());
            case DensityFunction.EndIslands e -> DensityFunctions.endIslands(e.seed());
            case DensityFunction.BlendDensity b -> DensityFunctions.blendDensity(unwrap(b.input()));
            case DensityFunction.BlendAlpha ignored -> DensityFunctions.blendAlpha();
            case DensityFunction.BlendOffset ignored -> DensityFunctions.blendOffset();
            case DensityFunction.FindTopSurface f -> DensityFunctions.findTopSurface(unwrap(f.density()), unwrap(f.upperBound()), f.lowerBound(), f.cellHeight());
        };
    }

    @Override
    public DensityFunction fromMinecraft(Object nms) {
        net.minecraft.world.level.levelgen.DensityFunction function = (net.minecraft.world.level.levelgen.DensityFunction) nms;

        if (function instanceof DensityFunctions.HolderHolder(Holder<net.minecraft.world.level.levelgen.DensityFunction> holderFunction)) {
            if (holderFunction.unwrapKey().isPresent()) {
                return new DensityFunction.Reference(keyOf(holderFunction));
            }
            return fromMinecraft(holderFunction.value());
        }

        Identifier typeId = BuiltInRegistries.DENSITY_FUNCTION_TYPE.getKey(function.codec().codec());
        Preconditions.checkNotNull(typeId, "unregistered density function type: %s", function.getClass());

        return switch (typeId.getPath()) {
            case "constant" -> DensityFunction.constant(function.minValue());

            case "add" -> DensityFunction.add(fromMinecraft(InternalReflectUtil.invokeMethod(function, "argument1")), fromMinecraft(InternalReflectUtil.invokeMethod(function, "argument2")));
            case "mul" -> DensityFunction.mul(fromMinecraft(InternalReflectUtil.invokeMethod(function, "argument1")), fromMinecraft(InternalReflectUtil.invokeMethod(function, "argument2")));
            case "min" -> DensityFunction.min(fromMinecraft(InternalReflectUtil.invokeMethod(function, "argument1")), fromMinecraft(InternalReflectUtil.invokeMethod(function, "argument2")));
            case "max" -> DensityFunction.max(fromMinecraft(InternalReflectUtil.invokeMethod(function, "argument1")), fromMinecraft(InternalReflectUtil.invokeMethod(function, "argument2")));

            case "abs" -> fromMinecraft(InternalReflectUtil.getFieldValue(function, "input")).abs();
            case "square" -> fromMinecraft(InternalReflectUtil.getFieldValue(function, "input")).square();
            case "cube" -> fromMinecraft(InternalReflectUtil.getFieldValue(function, "input")).cube();
            case "half_negative" -> fromMinecraft(InternalReflectUtil.getFieldValue(function, "input")).halfNegative();
            case "quarter_negative" -> fromMinecraft(InternalReflectUtil.getFieldValue(function, "input")).quarterNegative();
            case "invert" -> fromMinecraft(InternalReflectUtil.getFieldValue(function, "input")).invert();
            case "squeeze" -> fromMinecraft(InternalReflectUtil.getFieldValue(function, "input")).squeeze();

            case "clamp" -> fromMinecraft(InternalReflectUtil.getFieldValue(function, "input")).clamp(function.minValue(), function.maxValue());

            case "interpolated" -> DensityFunction.interpolated(fromMinecraft(((DensityFunctions.MarkerOrMarked) function).wrapped()));
            case "flat_cache" -> DensityFunction.flatCache(fromMinecraft(((DensityFunctions.MarkerOrMarked) function).wrapped()));
            case "cache_2d" -> DensityFunction.cache2d(fromMinecraft(((DensityFunctions.MarkerOrMarked) function).wrapped()));
            case "cache_once" -> DensityFunction.cacheOnce(fromMinecraft(((DensityFunctions.MarkerOrMarked) function).wrapped()));
            case "cache_all_in_cell" -> DensityFunction.cacheAllInCell(fromMinecraft(((DensityFunctions.MarkerOrMarked) function).wrapped()));

            case "noise" -> DensityFunction.noise(
                noiseKey(InternalReflectUtil.getFieldValue(function, "noise")),
                InternalReflectUtil.<Double>getFieldValue(function, "xzScale"),
                InternalReflectUtil.<Double>getFieldValue(function, "yScale"));

            case "shift" -> DensityFunction.shift(noiseKey(InternalReflectUtil.getFieldValue(function, "offsetNoise")));
            case "shift_a" -> DensityFunction.shiftA(noiseKey(InternalReflectUtil.getFieldValue(function, "offsetNoise")));
            case "shift_b" -> DensityFunction.shiftB(noiseKey(InternalReflectUtil.getFieldValue(function, "offsetNoise")));

            case "shifted_noise" -> DensityFunction.shiftedNoise2d(
                fromMinecraft(InternalReflectUtil.getFieldValue(function, "shiftX")),
                fromMinecraft(InternalReflectUtil.getFieldValue(function, "shiftZ")),
                InternalReflectUtil.<Double>getFieldValue(function, "xzScale"),
                noiseKey(InternalReflectUtil.getFieldValue(function, "noise")));

            case "range_choice" -> DensityFunction.rangeChoice(
                fromMinecraft(InternalReflectUtil.getFieldValue(function, "input")),
                InternalReflectUtil.<Double>getFieldValue(function, "minInclusive"),
                InternalReflectUtil.<Double>getFieldValue(function, "maxExclusive"),
                fromMinecraft(InternalReflectUtil.getFieldValue(function, "whenInRange")),
                fromMinecraft(InternalReflectUtil.getFieldValue(function, "whenOutOfRange")));

            case "y_clamped_gradient" -> DensityFunction.yClampedGradient(
                InternalReflectUtil.<Integer>getFieldValue(function, "fromY"),
                InternalReflectUtil.<Integer>getFieldValue(function, "toY"),
                InternalReflectUtil.<Double>getFieldValue(function, "fromValue"),
                InternalReflectUtil.<Double>getFieldValue(function, "toValue"));

            case "end_islands" -> DensityFunction.endIslands(0L);

            case "blend_density" -> DensityFunction.blendDensity(fromMinecraft(InternalReflectUtil.getFieldValue(function, "input")));
            case "blend_alpha" -> DensityFunction.blendAlpha();
            case "blend_offset" -> DensityFunction.blendOffset();

            case "find_top_surface" -> new DensityFunction.FindTopSurface(
                fromMinecraft(InternalReflectUtil.getFieldValue(function, "density")),
                fromMinecraft(InternalReflectUtil.getFieldValue(function, "upperBound")),
                InternalReflectUtil.<Integer>getFieldValue(function, "lowerBound"),
                InternalReflectUtil.<Integer>getFieldValue(function, "cellHeight"));

            default -> throw new UnsupportedOperationException("no wrapper representation for density function type: " + typeId);
        };
    }

    static net.minecraft.world.level.levelgen.DensityFunction unwrap(DensityFunction function) {
        return (net.minecraft.world.level.levelgen.DensityFunction) function.toMinecraft();
    }

    static Holder<NormalNoise.NoiseParameters> noiseHolder(ResourceKey key) {
        Identifier id = (Identifier) key.resourceLocation();
        Registry<NormalNoise.NoiseParameters> registry = BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.NOISE);
        return registry.getOrThrow(net.minecraft.resources.ResourceKey.create(Registries.NOISE, id));
    }

    static Holder<net.minecraft.world.level.levelgen.DensityFunction> densityHolder(ResourceKey key) {
        Identifier id = (Identifier) key.resourceLocation();
        Registry<net.minecraft.world.level.levelgen.DensityFunction> registry = BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.DENSITY_FUNCTION);
        return registry.getOrThrow(net.minecraft.resources.ResourceKey.create(Registries.DENSITY_FUNCTION, id));
    }

    static ResourceKey noiseKey(net.minecraft.world.level.levelgen.DensityFunction.NoiseHolder holder) {
        return keyOf(holder.noiseData());
    }

    static ResourceKey keyOf(Holder<?> holder) {
        Identifier id = holder.unwrapKey().orElseThrow().identifier();
        return ResourceKey.of(id.getNamespace(), id.getPath());
    }
}