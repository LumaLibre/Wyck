package dev.wyck.worldgen.surface.rule;

import dev.wyck.worldgen.surface.condition.ConditionSource;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record ConditionRuleSourceImpl(
    @Override ConditionSource condition,
    @Override RuleSource then
) implements ConditionRuleSource {
    @Override
    public Object toMinecraft() {
        net.minecraft.world.level.levelgen.SurfaceRules.ConditionSource nmsCondition = this.condition.asHandle();
        net.minecraft.world.level.levelgen.SurfaceRules.RuleSource nmsThen = this.then.asHandle();
        return net.minecraft.world.level.levelgen.SurfaceRules.ifTrue(nmsCondition, nmsThen);
    }
}
