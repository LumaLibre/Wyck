package dev.wyck.biome;

import dev.wyck.biome.ClimateSettings;
import dev.wyck.biome.TemperatureModifier;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record ClimateSettingsImpl(
    @Override boolean hasPrecipitation,
    @Override float temperature,
    @Override TemperatureModifier temperatureModifier,
    @Override float downfall
) implements ClimateSettings {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.biome.Biome.ClimateSettings(
            this.hasPrecipitation,
            this.temperature,
            this.temperatureModifier.toNms(net.minecraft.world.level.biome.Biome.TemperatureModifier.class),
            this.downfall
        );
    }
}
