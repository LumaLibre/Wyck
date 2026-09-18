package dev.wyck.decode.worldgen.synth;

import dev.wyck.decode.Decoders;
import dev.wyck.decode.FastReflection;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.synth.NoiseParameters;
import dev.wyck.wrapper.decode.DecoderRegistry;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@ApiStatus.Internal
public final class NoiseParametersDecoders extends DecoderRegistry<NoiseParameters, Object> {

    public static final ResourceKey REFERENCE = ResourceKey.wyck("reference");
    public static final ResourceKey COMPOSED = ResourceKey.wyck("composed");

    public NoiseParametersDecoders() {
        register(REFERENCE, minecraftObject -> {
            Holder.Reference<?> holder = (Holder.Reference<?>) minecraftObject;
            return NoiseParameters.reference(Decoders.key(holder.key()));
        });
        register(COMPOSED, minecraftObject -> {
            NormalNoise noise = minecraftObject instanceof Holder<?> holder
                ? (NormalNoise) holder.value()
                : (NormalNoise) minecraftObject;
            Object parameters = FastReflection.read(noise, "parameters");
            int baseOctave = FastReflection.read(parameters, "baseOctave");
            int octaveCount = FastReflection.read(parameters, "octaveCount");
            it.unimi.dsi.fastutil.doubles.DoubleList modifiers = FastReflection.read(parameters, "amplitudeModifiers");
            List<Double> amplitudes = modifiers.isEmpty()
                ? java.util.Collections.nCopies(octaveCount, 1.0)
                : List.copyOf(modifiers);
            return NoiseParameters.of(baseOctave, amplitudes);
        });
    }

    @Override
    protected Object normalize(Object minecraftObject) {
        return minecraftObject;
    }

    @Override
    protected ResourceKey discriminate(Object minecraftObject) {
        return minecraftObject instanceof Holder.Reference<?> ? REFERENCE : COMPOSED;
    }
}
