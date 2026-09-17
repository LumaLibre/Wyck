package dev.wyck.worldgen.surface.condition;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record NotConditionSourceImpl(
    @Override ConditionSource target
) implements NotConditionSource {
    @Override
    public Object toMinecraft() {
        net.minecraft.world.level.levelgen.material.condition.MaterialCondition nmsTarget = this.target.asHandle();
        return net.minecraft.world.level.levelgen.material.MaterialRules.not(nmsTarget);
    }
}
