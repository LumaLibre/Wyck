package me.outspending.biomesapi.wrapper.level.noise;

import me.outspending.biomesapi.annotations.WireFactory;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@ApiStatus.Internal
public class LevelNoiseGeneratorSettingsFactoryImpl implements LevelNoiseGeneratorSettings.Factory {

    @Override
    public LevelNoiseGeneratorSettings create(LevelNoiseGeneratorSettings.Data data) {
        return new LevelNoiseGeneratorSettingsImpl(data);
    }
}