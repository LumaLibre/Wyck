package dev.wyck.wrapper.level.noise;

import dev.wyck.annotations.WireFactory;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@ApiStatus.Internal
public class NoiseGeneratorSettingsFactoryImpl implements NoiseGeneratorSettings.Factory {

    @Override
    public NoiseGeneratorSettings create(NoiseGeneratorSettings.Data data) {
        return new NoiseGeneratorSettingsImpl(data);
    }
}