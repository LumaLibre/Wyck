package dev.wyck.worldgen.surface.rule;

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
        net.minecraft.world.level.levelgen.material.rule.MaterialRule[] sources = new net.minecraft.world.level.levelgen.material.rule.MaterialRule[this.rules.size()];
        for (int i = 0; i < this.rules.size(); i++) {
            sources[i] = this.rules.get(i).asHandle();
        }
        return net.minecraft.world.level.levelgen.material.MaterialRules.sequence(sources);
    }
}
