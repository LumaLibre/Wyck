package dev.wyck.worldgen.feature.configurations;

import dev.wyck.tags.TagSet;
import dev.wyck.worldgen.blockpredicates.BlockPredicate;
import dev.wyck.worldgen.placement.PlacedFeature;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record RootSystemConfigurationImpl(
    @Override PlacedFeature treeFeature,
    @Override int requiredVerticalSpaceForTree,
    @Override int levelTestDistance,
    @Override int maxLevelDeviation,
    @Override int rootRadius,
    @Override TagSet<Material> rootReplaceable,
    @Override BlockStateProvider rootStateProvider,
    @Override int rootPlacementAttempts,
    @Override int rootColumnMaxHeight,
    @Override int hangingRootRadius,
    @Override int hangingRootsVerticalSpan,
    @Override BlockStateProvider hangingRootStateProvider,
    @Override int hangingRootPlacementAttempts,
    @Override int allowedVerticalWaterForTree,
    @Override BlockPredicate allowedTreePosition
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
            rootReplaceable.asHolderSet(),
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