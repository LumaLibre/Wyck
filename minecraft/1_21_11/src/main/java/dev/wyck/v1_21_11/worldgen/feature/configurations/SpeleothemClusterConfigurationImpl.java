package dev.wyck.v1_21_11.worldgen.feature.configurations;

import dev.wyck.worldgen.feature.configurations.SpeleothemClusterConfiguration;
import dev.wyck.worldgen.valueproviders.FloatProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Set;

@NullMarked
@ApiStatus.Internal
public record SpeleothemClusterConfigurationImpl(
    @Override BlockData baseBlock,
    @Override BlockData pointedBlock,
    @Override Set<Material> replaceableBlocks,
    @Override int floorToCeilingSearchRange,
    @Override IntProvider height,
    @Override IntProvider radius,
    @Override int maxStalagmiteStalactiteHeightDiff,
    @Override int heightDeviation,
    @Override IntProvider speleothemBlockLayerThickness,
    @Override FloatProvider density,
    @Override FloatProvider wetness,
    @Override float chanceOfSpeleothemAtMaxDistanceFromCenter,
    @Override int maxDistanceFromEdgeAffectingChanceOfSpeleothem,
    @Override int maxDistanceFromCenterAffectingHeightBias
) implements SpeleothemClusterConfiguration {
    @Override
    public Object toMinecraft() {
        throw new UnsupportedOperationException("Doesn't exist in this Minecraft version.");
    }
}