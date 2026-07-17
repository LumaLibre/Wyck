package dev.wyck.worldgen.feature.configurations;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record UnderwaterMagmaConfigurationImpl(
    @Override int floorSearchRange,
    @Override int placementRadiusAroundFloor,
    @Override float placementProbabilityPerValidPosition
) implements UnderwaterMagmaConfiguration {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.configurations.UnderwaterMagmaConfiguration(
            floorSearchRange,
            placementRadiusAroundFloor,
            placementProbabilityPerValidPosition
        );
    }
}
