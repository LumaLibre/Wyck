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
        net.minecraft.world.level.levelgen.DensityFunction unwrapped = this.input.asHandle();
        return switch (transformation) {
            case ABS -> unwrapped.abs();
            case SQUARE -> unwrapped.square();
            case CUBE -> unwrapped.cube();
            case HALF_NEGATIVE -> unwrapped.halfNegative();
            case QUARTER_NEGATIVE -> unwrapped.quarterNegative();
            case INVERT -> unwrapped.invert();
            case SQUEEZE -> unwrapped.squeeze();
        };
    }

}
