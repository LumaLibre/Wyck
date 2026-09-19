package dev.wyck.worldgen.function.noise;

import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.function.DensityFunction;
import dev.wyck.worldgen.function.simple.ZeroSimpleFunction;
import dev.wyck.worldgen.synth.NoiseParameters;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class NoiseFunctionImpl extends NoiseParameterFunctionImpl implements NoiseFunction {

    protected final double xzScale;
    protected final double yScale;
    private final DensityFunction shiftX;
    private final DensityFunction shiftY;
    private final DensityFunction shiftZ;


    public NoiseFunctionImpl(Optional<ResourceKey> resourceKey, NoiseParameters noiseParameters, double xzScale, double yScale, DensityFunction shiftX, DensityFunction shiftY, DensityFunction shiftZ) {
        super(resourceKey, noiseParameters);
        this.xzScale = xzScale;
        this.yScale = yScale;
        this.shiftX = shiftX;
        this.shiftY = shiftY;
        this.shiftZ = shiftZ;
    }

    protected NoiseFunctionImpl(Optional<ResourceKey> resourceKey, NoiseParameters noiseParameters, double xzScale, double yScale) {
        this(resourceKey, noiseParameters, xzScale, yScale, ZeroSimpleFunction.INSTANCE, ZeroSimpleFunction.INSTANCE, ZeroSimpleFunction.INSTANCE);
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
    public DensityFunction shiftX() {
        return shiftX;
    }

    @Override
    public DensityFunction shiftY() {
        return shiftY;
    }

    @Override
    public DensityFunction shiftZ() {
        return shiftZ;
    }

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.densityfunction.generator.NoiseFunction(
            this.noiseData(),
            this.xzScale,
            this.yScale,
            shiftX.asHandle(),
            shiftY.asHandle(),
            shiftZ.asHandle()
        );
    }
}
