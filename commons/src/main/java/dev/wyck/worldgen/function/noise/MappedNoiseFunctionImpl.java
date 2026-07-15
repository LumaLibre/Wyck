package dev.wyck.worldgen.function.noise;

import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.synth.NoiseParameters;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public final class MappedNoiseFunctionImpl extends NoiseFunctionImpl implements MappedNoiseFunction {

    private final double minTarget;
    private final double maxTarget;

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public MappedNoiseFunctionImpl(Optional<ResourceKey> resourceKey, NoiseParameters noiseParameters, double xzScale, double yScale, double minTarget, double maxTarget) {
        super(resourceKey, noiseParameters, xzScale, yScale);
        this.minTarget = minTarget;
        this.maxTarget = maxTarget;
    }

    @Override
    public double minTarget() {
        return this.minTarget;
    }

    @Override
    public double maxTarget() {
        return this.maxTarget;
    }

    @Override
    public Object toMinecraft() {
        net.minecraft.core.Holder<net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters> holderParams = net.minecraft.core.Holder.direct(this.noiseParameters.asHandle());
        return net.minecraft.world.level.levelgen.DensityFunctions.mappedNoise(holderParams, this.xzScale, this.yScale, this.minTarget, this.maxTarget);
    }
}
