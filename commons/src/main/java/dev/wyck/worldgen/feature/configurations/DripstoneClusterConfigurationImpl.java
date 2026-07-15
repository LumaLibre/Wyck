package dev.wyck.worldgen.feature.configurations;

import dev.wyck.worldgen.feature.configurations.DripstoneClusterConfiguration;
import dev.wyck.worldgen.valueproviders.FloatProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record DripstoneClusterConfigurationImpl(
    @Override int floorToCeilingSearchRange,
    @Override IntProvider height,
    @Override IntProvider radius,
    @Override int maxStalagmiteStalactiteHeightDiff,
    @Override int heightDeviation,
    @Override IntProvider dripstoneBlockLayerThickness,
    @Override FloatProvider density,
    @Override FloatProvider wetness,
    @Override float chanceOfDripstoneColumnAtMaxDistanceFromCenter,
    @Override int maxDistanceFromEdgeAffectingChanceOfDripstoneColumn,
    @Override int maxDistanceFromCenterAffectingHeightBias
) implements DripstoneClusterConfiguration {
    @Override
    public Object toMinecraft() {
        throw new UnsupportedOperationException("Doesn't exist in this Minecraft version.");
    }
}