package me.outspending.biomesapi.wrapper.worldgen.stateproviders;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
public class NoiseParametersFactoryImpl implements NoiseParameters.Factory {
    @Override
    public NoiseParameters create(int firstOctave, List<Double> amplitudes) {
        return new NoiseParametersImpl(firstOctave, amplitudes);
    }

    @Override
    public NoiseParameters fromMinecraft(Object nms) {
        NormalNoise.NoiseParameters parameters = (NormalNoise.NoiseParameters) nms;
        return new NoiseParametersImpl(parameters.firstOctave(), parameters.amplitudes());
    }
}
