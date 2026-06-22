package me.outspending.biomesapi.wrapper.level.noise.settings;

import me.outspending.biomesapi.annotations.WireFactory;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@ApiStatus.Internal
public class LevelNoiseSettingsFactoryImpl implements LevelNoiseSettings.Factory {

    @Override
    public LevelNoiseSettings create(int minY, int height, int sizeHorizontal, int sizeVertical) {
        return new LevelNoiseSettingsImpl(minY, height, sizeHorizontal, sizeVertical);
    }
}