package me.outspending.biomesapi.wrapper.worldgen.stateproviders;

import me.outspending.biomesapi.annotations.AsOf;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

/**
 * Wraps NormalNoise.NoiseParameters, a first octave plus amplitudes.
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
@ApiStatus.Internal
public record NoiseParametersImpl(int firstOctave, List<Double> amplitudes) implements NoiseParameters {

    @AsOf("2.3.0")
    public NoiseParametersImpl {
        amplitudes = List.copyOf(amplitudes);
    }

    @Override
    @AsOf("2.3.0")
    public Object toMinecraft() {
        return new NormalNoise.NoiseParameters(this.firstOctave, new DoubleArrayList(this.amplitudes));
    }
}