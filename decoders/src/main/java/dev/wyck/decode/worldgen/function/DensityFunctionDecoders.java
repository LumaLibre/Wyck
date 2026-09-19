package dev.wyck.decode.worldgen.function;

import dev.wyck.decode.Decoders;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.function.DensityFunction;
import dev.wyck.worldgen.function.misc.EndIslands;
import dev.wyck.worldgen.function.misc.FindTopSurface;
import dev.wyck.worldgen.function.misc.Marker;
import dev.wyck.worldgen.function.misc.RangeChoice;
import dev.wyck.worldgen.function.misc.ReferencedDensityFunction;
import dev.wyck.worldgen.function.misc.YClampedGradient;
import dev.wyck.worldgen.function.noise.NoiseFunction;
import dev.wyck.worldgen.function.noise.ShiftedFunction;
import dev.wyck.worldgen.function.simple.BlendAlpha;
import dev.wyck.worldgen.function.simple.BlendOffset;
import dev.wyck.worldgen.function.simple.ConstantSimpleFunction;
import dev.wyck.worldgen.function.simple.TwoArgumentSimpleFunction;
import dev.wyck.worldgen.function.transformer.ClampedTransformer;
import dev.wyck.worldgen.function.transformer.MappedTransformer;
import dev.wyck.worldgen.synth.NoiseParameters;
import dev.wyck.wrapper.decode.DecoderRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.densityfunction.DensityFunctions;
import net.minecraft.world.level.levelgen.densityfunction.generator.ConstantFunction;
import net.minecraft.world.level.levelgen.densityfunction.generator.EndIslandFunction;
import net.minecraft.world.level.levelgen.densityfunction.generator.GradientFunction;
import net.minecraft.world.level.levelgen.densityfunction.generator.ShiftNoiseFunction;
import net.minecraft.world.level.levelgen.densityfunction.op.BinaryFunction;
import net.minecraft.world.level.levelgen.densityfunction.op.BlendDensityFunction;
import net.minecraft.world.level.levelgen.densityfunction.op.CacheFunction;
import net.minecraft.world.level.levelgen.densityfunction.op.ClampFunction;
import net.minecraft.world.level.levelgen.densityfunction.op.FindTopSurfaceFunction;
import net.minecraft.world.level.levelgen.densityfunction.op.InterpolatedFunction;
import net.minecraft.world.level.levelgen.densityfunction.op.RangeChoiceFunction;
import net.minecraft.world.level.levelgen.densityfunction.op.UnaryFunction;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class DensityFunctionDecoders extends DecoderRegistry<DensityFunction, Object> {

    public static final ResourceKey REFERENCE = ResourceKey.wyck("reference");

    public DensityFunctionDecoders() {
        register(REFERENCE, this::reference);
        register("constant", function -> ConstantSimpleFunction.of(((ConstantFunction) function).value()));
        register("blend_alpha", _ -> BlendAlpha.INSTANCE);
        register("blend_offset", _ -> BlendOffset.INSTANCE);
        register("clamp", function -> {
            ClampFunction clamp = (ClampFunction) function;
            return ClampedTransformer.of(
                DensityFunction.decode(clamp.input()), clamp.min(), clamp.max()
            );
        });
        register("noise", function -> {
            net.minecraft.world.level.levelgen.densityfunction.generator.NoiseFunction noise =
                (net.minecraft.world.level.levelgen.densityfunction.generator.NoiseFunction) function;
            return noise(noise);
        });
        register("range_choice", function -> {
            RangeChoiceFunction range = (RangeChoiceFunction) function;
            return RangeChoice.of(
                DensityFunction.decode(range.input()),
                range.minInclusive(), range.maxExclusive(),
                DensityFunction.decode(range.whenInRange()), DensityFunction.decode(range.whenOutOfRange())
            );
        });
        register("gradient", function -> {
            GradientFunction gradient = (GradientFunction) function;
            if (gradient.axis() != net.minecraft.core.Direction.Axis.Y
                || gradient.tiling() != net.minecraft.world.level.levelgen.densityfunction.TilingMode.CLAMP_TO_EDGE) {
                throw new IllegalArgumentException("Wyck only wraps clamped Y-axis gradients: " + gradient);
            }
            return YClampedGradient.of(
                gradient.fromCoordinate(), gradient.toCoordinate(),
                gradient.fromValue(), gradient.toValue()
            );
        });
        register("find_top_surface", function -> {
            FindTopSurfaceFunction find = (FindTopSurfaceFunction) function;
            return FindTopSurface.of(
                DensityFunction.decode(find.density()), DensityFunction.decode(find.upperBound()),
                find.lowerBound(), find.cellHeight()
            );
        });
        register("end_outer_islands", _ -> EndIslands.of());
        register("cache", function -> Marker.of(
            Marker.Type.CACHE, DensityFunction.decode(((CacheFunction) function).input())
        ));
        register("blend_density", function -> Marker.of(
            Marker.Type.BLEND_DENSITY, DensityFunction.decode(((BlendDensityFunction) function).input())
        ));
        register("interpolated", function -> {
            InterpolatedFunction interpolated = (InterpolatedFunction) function;
            return Marker.of(
                null, Marker.Type.INTERPOLATED, DensityFunction.decode(interpolated.input()),
                interpolated.cellSizeXz(), interpolated.cellSizeY()
            );
        });

        for (MappedTransformer.Transform transform : MappedTransformer.Transform.values()) {
            register(unaryKey(transform), function -> MappedTransformer.of(
                DensityFunction.decode(((UnaryFunction) function).input()), transform
            ));
        }
        for (TwoArgumentSimpleFunction.Operation operation : TwoArgumentSimpleFunction.Operation.values()) {
            register(operation.name().toLowerCase(java.util.Locale.ROOT), function -> {
                BinaryFunction binary = (BinaryFunction) function;
                return TwoArgumentSimpleFunction.of(
                    operation, DensityFunction.decode(binary.left()), DensityFunction.decode(binary.right())
                );
            });
        }
        register("shift", function -> shifted((ShiftNoiseFunction) function, ShiftedFunction.Kind.SHIFT));
        register("shift_a", function -> shifted((ShiftNoiseFunction) function, ShiftedFunction.Kind.SHIFT_A));
        register("shift_b", function -> shifted((ShiftNoiseFunction) function, ShiftedFunction.Kind.SHIFT_B));
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Object normalize(Object minecraftObject) {
        Object current = minecraftObject;
        if (current instanceof Holder<?> holder) {
            if (holder instanceof Holder.Reference<?>) {
                return new DensityFunctions.HolderHolder(
                    (Holder<net.minecraft.world.level.levelgen.densityfunction.DensityFunction>) holder
                );
            }
            current = holder.value();
        }
        while (current instanceof DensityFunctions.HolderHolder(Holder<net.minecraft.world.level.levelgen.densityfunction.DensityFunction> function) && !(function instanceof Holder.Reference<?>)) {
            current = function.value();
        }
        return current;
    }

    @Override
    protected ResourceKey discriminate(Object minecraftObject) {
        if (minecraftObject instanceof DensityFunctions.HolderHolder) {
            return REFERENCE;
        }
        net.minecraft.world.level.levelgen.densityfunction.DensityFunction function =
            (net.minecraft.world.level.levelgen.densityfunction.DensityFunction) minecraftObject;
        return Decoders.registryKey(
            BuiltInRegistries.DENSITY_FUNCTION_TYPE, function.codec()
        );
    }

    private DensityFunction reference(Object minecraftObject) {
        Holder<net.minecraft.world.level.levelgen.densityfunction.DensityFunction> holder =
            ((DensityFunctions.HolderHolder) minecraftObject).function();
        return ReferencedDensityFunction.of(Decoders.key(
            ((Holder.Reference<net.minecraft.world.level.levelgen.densityfunction.DensityFunction>) holder).key().identifier()
        ));
    }

    private DensityFunction noise(net.minecraft.world.level.levelgen.densityfunction.generator.NoiseFunction noise) {
        return dev.wyck.worldgen.function.noise.NoiseFunction.of(
            null,
            NoiseParameters.decode(noise.noise()),
            noise.xzScale(), noise.yScale(),
            DensityFunction.decode(noise.shiftX()),
            DensityFunction.decode(noise.shiftY()),
            DensityFunction.decode(noise.shiftZ())
        );
    }

    private DensityFunction shifted(ShiftNoiseFunction function, ShiftedFunction.Kind kind) {
        return ShiftedFunction.of(NoiseParameters.decode(function.offsetNoise()), kind);
    }

    private static String unaryKey(MappedTransformer.Transform transform) {
        return transform.name().toLowerCase(java.util.Locale.ROOT);
    }
}
