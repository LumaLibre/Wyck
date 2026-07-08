package dev.wyck.wrapper.worldgen.feature.configurations.geode;

import dev.wyck.keys.ResourceKey;
import dev.wyck.util.WorldgenConversions;
import dev.wyck.wrapper.worldgen.stateproviders.BlockStateProvider;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NullMarked
@ApiStatus.Internal
public record GeodeBlockSettingsImpl(
    @Override BlockStateProvider fillingProvider,
    @Override BlockStateProvider innerLayerProvider,
    @Override BlockStateProvider alternateInnerLayerProvider,
    @Override BlockStateProvider middleLayerProvider,
    @Override BlockStateProvider outerLayerProvider,
    @Override List<BlockData> innerPlacements,
    @Override Set<Material> cannotReplace,
    @Override Set<Material> invalidBlocks,
    @Override @Nullable ResourceKey legacy$cannotReplace,
    @Override @Nullable ResourceKey legacy$invalidBlocks
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
            WorldgenConversions.toBlockHolderSet(cannotReplace),
            WorldgenConversions.toBlockHolderSet(invalidBlocks)
        );
    }
}