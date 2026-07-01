package me.outspending.biomesapi.wrapper.level.noise.settings;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record NoiseSettingsImpl(int minY, int height, int sizeHorizontal, int sizeVertical) implements NoiseSettings {

    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.NoiseSettings.create(this.minY, this.height, this.sizeHorizontal, this.sizeVertical);
    }
}