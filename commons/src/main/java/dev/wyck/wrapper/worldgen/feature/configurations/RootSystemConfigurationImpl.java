package dev.wyck.wrapper.worldgen.feature.configurations;

import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.worldgen.blockpredicates.BlockPredicate;
import dev.wyck.util.WorldgenConversions;
import dev.wyck.wrapper.worldgen.placement.PlacedFeature;
import dev.wyck.wrapper.worldgen.stateproviders.BlockStateProvider;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Set;

@NullMarked
@ApiStatus.Internal
public record RootSystemConfigurationImpl(
    @Override PlacedFeature treeFeature,
    @Override int requiredVerticalSpaceForTree,
    @Override int levelTestDistance,
    @Override int maxLevelDeviation,
    @Override int rootRadius,
    @Override Set<Material> rootReplaceable,
    @Override BlockStateProvider rootStateProvider,
    @Override int rootPlacementAttempts,
    @Override int rootColumnMaxHeight,
    @Override int hangingRootRadius,
    @Override int hangingRootsVerticalSpan,
    @Override BlockStateProvider hangingRootStateProvider,
    @Override int hangingRootPlacementAttempts,
    @Override int allowedVerticalWaterForTree,
    @Override BlockPredicate allowedTreePosition,
    @Override @Nullable ResourceKey legacy$rootReplaceable
) implements RootSystemConfiguration {
    @Override
    public Object toMinecraft() {
        net.minecraft.core.Holder<net.minecraft.world.level.levelgen.placement.PlacedFeature> feature = treeFeature.asHandle();

        return new net.minecraft.world.level.levelgen.feature.configurations.RootSystemConfiguration(
            feature,
            requiredVerticalSpaceForTree,
            levelTestDistance,
            maxLevelDeviation,
            rootRadius,
            WorldgenConversions.toBlockHolderSet(rootReplaceable),
            rootStateProvider.asHandle(),
            rootPlacementAttempts,
            rootColumnMaxHeight,
            hangingRootRadius,
            hangingRootsVerticalSpan,
            hangingRootStateProvider.asHandle(),
            hangingRootPlacementAttempts,
            allowedVerticalWaterForTree,
            allowedTreePosition.asHandle()
        );
    }
}