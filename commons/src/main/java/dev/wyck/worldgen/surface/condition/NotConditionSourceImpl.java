package dev.wyck.worldgen.surface.condition;

import dev.wyck.worldgen.surface.condition.ConditionSource;
import dev.wyck.worldgen.surface.condition.NotConditionSource;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record NotConditionSourceImpl(
    @Override ConditionSource target
) implements NotConditionSource {
    @Override
    public Object toMinecraft() {
        net.minecraft.world.level.levelgen.SurfaceRules.ConditionSource nmsTarget = this.target.asHandle();
        return net.minecraft.world.level.levelgen.SurfaceRules.not(nmsTarget);
    }
}
