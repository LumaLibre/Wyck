package dev.wyck.wrapper.worldgen.synth;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@ApiStatus.Internal
public record NoiseParametersImpl(@Override int firstOctave, @Override List<Double> amplitudes) implements NoiseParameters {
    public NoiseParametersImpl {
        amplitudes = List.copyOf(amplitudes);
    }

    @Override
    public Object toMinecraft() {
        return new NormalNoise.NoiseParameters(this.firstOctave, new DoubleArrayList(this.amplitudes));
    }
}