package dev.wyck.wrapper.worldgen.feature.configurations;

import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.worldgen.BlockPredicate;
import dev.wyck.wrapper.worldgen.placement.PlacedFeature;
import dev.wyck.wrapper.worldgen.stateproviders.BlockStateProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record RootSystemConfigurationImpl(
    @Override PlacedFeature treeFeature,
    @Override int requiredVerticalSpaceForTree,
    @Override int rootRadius,
    @Override ResourceKey rootReplaceable,
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

        net.minecraft.resources.Identifier location = rootReplaceable.asHandle();
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