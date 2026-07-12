package dev.wyck.wrapper.worldgen.surface.rule;

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
