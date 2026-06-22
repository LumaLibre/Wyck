package me.outspending.biomesapi.wrapper.level.noise.settings;

import net.minecraft.world.level.levelgen.NoiseSettings;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record LevelNoiseSettingsImpl(int minY, int height, int sizeHorizontal, int sizeVertical) implements LevelNoiseSettings {

    @Override
    public Object toMinecraft() {
        return NoiseSettings.create(this.minY, this.height, this.sizeHorizontal, this.sizeVertical);
    }
}