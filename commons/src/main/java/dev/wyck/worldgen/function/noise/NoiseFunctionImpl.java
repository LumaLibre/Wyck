package dev.wyck.worldgen.function.noise;

import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.synth.NoiseParameters;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public class NoiseFunctionImpl extends NoiseParameterFunctionImpl implements NoiseFunction {

    protected final double xzScale;
    protected final double yScale;

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public NoiseFunctionImpl(Optional<ResourceKey> resourceKey, NoiseParameters noiseParameters, double xzScale, double yScale) {
        super(resourceKey, noiseParameters);
        this.xzScale = xzScale;
        this.yScale = yScale;
    }

    @Override
    public double xzScale() {
        return xzScale;
    }

    @Override
    public double yScale() {
        return yScale;
    }

    @Override
    public Object toMinecraft() {
        net.minecraft.core.Holder<net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters> noiseData = net.minecraft.core.Holder.direct(this.noiseParameters.asHandle());
        return net.minecraft.world.level.levelgen.DensityFunctions.noise(noiseData, this.xzScale, this.yScale);
    }
}
