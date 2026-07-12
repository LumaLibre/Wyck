package dev.wyck.wrapper.worldgen.surface.condition;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record StoneDepthConditionSourceImpl(
    @Override int offset,
    @Override boolean addSurfaceDepth,
    @Override int secondaryDepthRange,
    @Override CaveSurface surfaceType
) implements StoneDepthConditionSource {
    @Override
    public Object toMinecraft() {
        net.minecraft.world.level.levelgen.placement.CaveSurface nmsSurface = this.surfaceType.toNms("net.minecraft.world.level.levelgen.placement.CaveSurface");
        return net.minecraft.world.level.levelgen.SurfaceRules.stoneDepthCheck(this.offset, this.addSurfaceDepth, this.secondaryDepthRange, nmsSurface);
    }
}
