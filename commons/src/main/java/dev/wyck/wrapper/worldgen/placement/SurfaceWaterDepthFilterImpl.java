package dev.wyck.wrapper.worldgen.placement;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record SurfaceWaterDepthFilterImpl(@Override int maxWaterDepth) implements SurfaceWaterDepthFilter {
    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.placement.SurfaceWaterDepthFilter.forMaxDepth(maxWaterDepth);
    }
}