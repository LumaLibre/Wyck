package dev.wyck.worldgen.surface.condition;

import dev.wyck.worldgen.surface.condition.SteepConditionSource;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record SteepConditionSourceImpl() implements SteepConditionSource {
    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.SurfaceRules.steep();
    }
}
