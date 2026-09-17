package dev.wyck.worldgen.feature.configurations;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record UnderwaterMagmaFeatureImpl(
    @Override int floorSearchRange,
    @Override int placementRadiusAroundFloor,
    @Override float placementProbabilityPerValidPosition
) implements UnderwaterMagmaFeature {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.UnderwaterMagmaFeature(
            floorSearchRange,
            placementRadiusAroundFloor,
            placementProbabilityPerValidPosition
        );
    }
}
