package dev.wyck.wrapper.worldgen.placement;

import dev.wyck.wrapper.worldgen.HeightmapType;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record HeightmapPlacementImpl(@Override HeightmapType heightmap) implements HeightmapPlacement {
    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.placement.HeightmapPlacement.onHeightmap(
            heightmap.toNms(net.minecraft.world.level.levelgen.Heightmap.Types.class)
        );
    }
}
