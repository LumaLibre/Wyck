package dev.wyck.v26_1.worldgen.feature.configurations;

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
        return new net.minecraft.world.level.levelgen.feature.configurations.DripstoneClusterConfiguration(
            floorToCeilingSearchRange,
            height.asHandle(),
            radius.asHandle(),
            maxStalagmiteStalactiteHeightDiff,
            heightDeviation,
            dripstoneBlockLayerThickness.asHandle(),
            density.asHandle(),
            wetness.asHandle(),
            chanceOfDripstoneColumnAtMaxDistanceFromCenter,
            maxDistanceFromEdgeAffectingChanceOfDripstoneColumn,
            maxDistanceFromCenterAffectingHeightBias
        );
    }
}