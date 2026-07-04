package dev.wyck.wrapper.level.noise.settings;

import dev.wyck.annotations.WireFactory;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@ApiStatus.Internal
public class NoiseSettingsFactoryImpl implements NoiseSettings.Factory {

    @Override
    public NoiseSettings create(int minY, int height, int sizeHorizontal, int sizeVertical) {
        return new NoiseSettingsImpl(minY, height, sizeHorizontal, sizeVertical);
    }
}