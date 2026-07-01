package me.outspending.biomesapi.wrapper.level.noise.settings;

import me.outspending.biomesapi.annotations.WireFactory;
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

    @Override
    public NoiseSettings fromMinecraft(Object nms) {
        net.minecraft.world.level.levelgen.NoiseSettings settings = (net.minecraft.world.level.levelgen.NoiseSettings) nms;
        return NoiseSettings.of(settings.minY(), settings.height(), settings.noiseSizeHorizontal(), settings.noiseSizeVertical());
    }
}