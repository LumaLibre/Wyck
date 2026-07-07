package dev.wyck.v1_21_11.wrapper.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.worldgen.BlockPredicate;
import dev.wyck.wrapper.worldgen.feature.configurations.RootSystemConfiguration;
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
        Preconditions.checkNotNull(legacy$rootReplaceable, "legacy$rootReplaceable");
        net.minecraft.core.Holder<net.minecraft.world.level.levelgen.placement.PlacedFeature> feature = treeFeature.asHandle();

        net.minecraft.resources.Identifier location = legacy$rootReplaceable.asHandle();
        net.minecraft.tags.TagKey<net.minecraft.world.level.block.Block> replaceable =
            net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK, location);

        return new net.minecraft.world.level.levelgen.feature.configurations.RootSystemConfiguration(
            feature,
            requiredVerticalSpaceForTree,
            rootRadius,
            replaceable,
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