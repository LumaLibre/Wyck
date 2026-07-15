package dev.wyck.worldgen.feature.configurations;

import dev.wyck.util.WorldgenConversions;
import dev.wyck.worldgen.valueproviders.FloatProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
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
        return new net.minecraft.world.level.levelgen.feature.configurations.SpeleothemClusterConfiguration(
            ((CraftBlockData) baseBlock).getState(),
            ((CraftBlockData) pointedBlock).getState(),
            WorldgenConversions.toBlockHolderSet(replaceableBlocks),
            floorToCeilingSearchRange,
            height.asHandle(),
            radius.asHandle(),
            maxStalagmiteStalactiteHeightDiff,
            heightDeviation,
            speleothemBlockLayerThickness.asHandle(),
            density.asHandle(),
            wetness.asHandle(),
            chanceOfSpeleothemAtMaxDistanceFromCenter,
            maxDistanceFromEdgeAffectingChanceOfSpeleothem,
            maxDistanceFromCenterAffectingHeightBias
        );
    }
}