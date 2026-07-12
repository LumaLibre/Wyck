package dev.wyck.wrapper.worldgen.surface.condition;

import dev.wyck.wrapper.worldgen.valueproviders.VerticalAnchor;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record YAboveConditionSourceImpl(
    @Override VerticalAnchor anchor,
    @Override int surfaceDepthMultiplier,
    @Override boolean addStoneDepth
) implements YAboveConditionSource {
    @Override
    public Object toMinecraft() {
        net.minecraft.world.level.levelgen.VerticalAnchor nmsAnchor = this.anchor.asHandle();
        if (this.addStoneDepth) {
            return net.minecraft.world.level.levelgen.SurfaceRules.yStartCheck(nmsAnchor, this.surfaceDepthMultiplier);
        }
        return net.minecraft.world.level.levelgen.SurfaceRules.yBlockCheck(nmsAnchor, this.surfaceDepthMultiplier);
    }
}
