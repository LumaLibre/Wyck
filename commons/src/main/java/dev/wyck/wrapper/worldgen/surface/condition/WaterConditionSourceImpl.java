package dev.wyck.wrapper.worldgen.surface.condition;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record WaterConditionSourceImpl(
    @Override int offset,
    @Override int surfaceDepthMultiplier,
    @Override boolean addStoneDepth
) implements WaterConditionSource {
    @Override
    public Object toMinecraft() {
        if (this.addStoneDepth) {
            return net.minecraft.world.level.levelgen.SurfaceRules.waterStartCheck(this.offset, this.surfaceDepthMultiplier);
        }
        return net.minecraft.world.level.levelgen.SurfaceRules.waterBlockCheck(this.offset, this.surfaceDepthMultiplier);
    }
}
