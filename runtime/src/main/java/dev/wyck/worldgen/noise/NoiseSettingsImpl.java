package dev.wyck.worldgen.noise;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record NoiseSettingsImpl(int minY, int height) implements NoiseSettings {
    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.NoiseSettings.create(minY, height);
    }
}
