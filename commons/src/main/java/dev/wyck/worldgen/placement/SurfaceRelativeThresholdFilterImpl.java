package dev.wyck.worldgen.placement;

import dev.wyck.worldgen.HeightmapType;
import dev.wyck.worldgen.placement.SurfaceRelativeThresholdFilter;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record SurfaceRelativeThresholdFilterImpl(
    @Override HeightmapType heightmap,
    @Override int minInclusive,
    @Override int maxInclusive
) implements SurfaceRelativeThresholdFilter {
    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.placement.SurfaceRelativeThresholdFilter.of(
            heightmap.toNms(net.minecraft.world.level.levelgen.Heightmap.Types.class),
            minInclusive,
            maxInclusive
        );
    }
}