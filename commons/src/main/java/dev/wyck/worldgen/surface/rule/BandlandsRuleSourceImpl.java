package dev.wyck.worldgen.surface.rule;

import dev.wyck.worldgen.surface.rule.BandlandsRuleSource;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record BandlandsRuleSourceImpl() implements BandlandsRuleSource {
    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.SurfaceRules.bandlands();
    }
}
