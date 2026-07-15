package dev.wyck.worldgen.surface.condition;

import dev.wyck.worldgen.surface.condition.TemperatureConditionSource;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record TemperatureConditionSourceImpl() implements TemperatureConditionSource {
    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.SurfaceRules.temperature();
    }
}
