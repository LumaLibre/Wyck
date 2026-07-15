package dev.wyck.worldgen.function.noise;

import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.synth.NoiseParameters;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public final class ShiftedFunctionImpl extends NoiseParameterFunctionImpl implements ShiftedFunction {

    private final Kind kind;

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public ShiftedFunctionImpl(Optional<ResourceKey> resourceKey, NoiseParameters noiseParameters, Kind kind) {
        super(resourceKey, noiseParameters);
        this.kind = kind;
    }

    @Override
    public Kind kind() {
        return this.kind;
    }

    @Override
    public Object toMinecraft() {
        net.minecraft.core.Holder<net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters> noiseData = net.minecraft.core.Holder.direct(this.noiseParameters.asHandle());
        return switch (kind) {
            case SHIFT -> net.minecraft.world.level.levelgen.DensityFunctions.shift(noiseData);
            case SHIFT_A -> net.minecraft.world.level.levelgen.DensityFunctions.shiftA(noiseData);
            case SHIFT_B -> net.minecraft.world.level.levelgen.DensityFunctions.shiftB(noiseData);
        };
    }
}
