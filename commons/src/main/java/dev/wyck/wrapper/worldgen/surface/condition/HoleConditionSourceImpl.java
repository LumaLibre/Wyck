package dev.wyck.wrapper.worldgen.surface.condition;

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
