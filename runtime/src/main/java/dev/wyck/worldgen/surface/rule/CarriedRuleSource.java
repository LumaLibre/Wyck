package dev.wyck.worldgen.surface.rule;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record CarriedRuleSource(Object handle) implements RuleSource {

    @Override
    public Object toMinecraft() {
        return this.handle;
    }
}
