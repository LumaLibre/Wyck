package dev.wyck.worldgen.surface.condition;

import dev.wyck.worldgen.surface.condition.HoleConditionSource;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record HoleConditionSourceImpl() implements HoleConditionSource {
    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.SurfaceRules.hole();
    }
}
