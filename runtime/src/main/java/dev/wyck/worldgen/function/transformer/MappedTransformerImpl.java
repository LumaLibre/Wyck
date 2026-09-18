package dev.wyck.worldgen.function.transformer;

import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.function.DensityFunction;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public final class MappedTransformerImpl extends PureTransformerImpl implements MappedTransformer {

    private final MappedTransformer.Transform transformation;

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public MappedTransformerImpl(Optional<ResourceKey> resourceKey, DensityFunction input, MappedTransformer.Transform transformation) {
        super(resourceKey, input);
        this.transformation = transformation;
    }

    @Override
    public MappedTransformer.Transform transformation() {
        return transformation;
    }

    @Override
    public Object toMinecraft() {
        net.minecraft.world.level.levelgen.densityfunction.DensityFunction unwrapped = this.input.asHandle();
        return switch (transformation) {
            case ABS -> unwrapped.abs();
            case SQUARE -> unwrapped.square();
            case CUBE -> unwrapped.cube();
            case SQRT -> unwrapped.sqrt();
            case HALF_NEGATIVE -> unwrapped.halfNegative();
            case QUARTER_NEGATIVE -> unwrapped.quarterNegative();
            case RECIPROCAL -> unwrapped.reciprocal();
            case NEGATE -> unwrapped.negate();
            case SQUEEZE -> unwrapped.squeeze();
            case LOG -> unwrapped.log();
            case SIGN -> unwrapped.sign();
        };
    }

}
