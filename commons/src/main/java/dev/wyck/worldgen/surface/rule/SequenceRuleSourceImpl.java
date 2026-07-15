package dev.wyck.worldgen.surface.rule;

import dev.wyck.worldgen.surface.rule.RuleSource;
import dev.wyck.worldgen.surface.rule.SequenceRuleSource;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@ApiStatus.Internal
public record SequenceRuleSourceImpl(
    @Override List<RuleSource> rules
) implements SequenceRuleSource {
    @Override
    public Object toMinecraft() {
        net.minecraft.world.level.levelgen.SurfaceRules.RuleSource[] sources = new net.minecraft.world.level.levelgen.SurfaceRules.RuleSource[this.rules.size()];
        for (int i = 0; i < this.rules.size(); i++) {
            sources[i] = this.rules.get(i).asHandle();
        }
        return net.minecraft.world.level.levelgen.SurfaceRules.sequence(sources);
    }
}
