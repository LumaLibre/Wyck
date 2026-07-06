package dev.wyck.wrapper.worldgen.feature.configurations.geode;

import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.worldgen.stateproviders.BlockStateProvider;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

@NullMarked
@ApiStatus.Internal
public record GeodeBlockSettingsImpl(
    @Override BlockStateProvider fillingProvider,
    @Override BlockStateProvider innerLayerProvider,
    @Override BlockStateProvider alternateInnerLayerProvider,
    @Override BlockStateProvider middleLayerProvider,
    @Override BlockStateProvider outerLayerProvider,
    @Override List<BlockData> innerPlacements,
    @Override ResourceKey cannotReplace,
    @Override ResourceKey invalidBlocks
) implements GeodeBlockSettings {
    @Override
    public Object toMinecraft() {
        List<net.minecraft.world.level.block.state.BlockState> placements = new ArrayList<>(innerPlacements.size());
        for (BlockData data : innerPlacements) {
            placements.add(((CraftBlockData) data).getState());
        }

        return new net.minecraft.world.level.levelgen.GeodeBlockSettings(
            fillingProvider.asHandle(),
            innerLayerProvider.asHandle(),
            alternateInnerLayerProvider.asHandle(),
            middleLayerProvider.asHandle(),
            outerLayerProvider.asHandle(),
            placements,
            toBlockTag(cannotReplace),
            toBlockTag(invalidBlocks)
        );
    }

    private static net.minecraft.tags.TagKey<net.minecraft.world.level.block.Block> toBlockTag(ResourceKey key) {
        net.minecraft.resources.Identifier location = key.asHandle();
        return net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK, location);
    }
}