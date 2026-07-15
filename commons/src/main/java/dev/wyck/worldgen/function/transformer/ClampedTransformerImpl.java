package dev.wyck.worldgen.function.transformer;

import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.function.DensityFunction;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public final class ClampedTransformerImpl extends PureTransformerImpl implements ClampedTransformer {

    private final double min;
    private final double max;

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public ClampedTransformerImpl(Optional<ResourceKey> resourceKey, DensityFunction input, double min, double max) {
        super(resourceKey, input);
        this.min = min;
        this.max = max;
    }

    @Override
    public double min() {
        return this.min;
    }

    @Override
    public double max() {
        return this.max;
    }

    @Override
    public Object toMinecraft() {
        net.minecraft.world.level.levelgen.DensityFunction unwrapped = this.input.asHandle();
        return unwrapped.clamp(min, max);
    }
}
