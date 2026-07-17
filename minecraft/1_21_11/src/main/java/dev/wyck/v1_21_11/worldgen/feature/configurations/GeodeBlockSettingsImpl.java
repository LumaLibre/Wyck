package dev.wyck.v1_21_11.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.tags.TagSet;
import dev.wyck.worldgen.feature.configurations.geode.GeodeBlockSettings;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import org.bukkit.Material;
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
    @Override TagSet<Material> cannotReplace,
    @Override TagSet<Material> invalidBlocks
) implements GeodeBlockSettings {
    @Override
    public Object toMinecraft() {
        Preconditions.checkState(cannotReplace.isTag(), "cannotReplace must be a tag in this version of Minecraft");
        Preconditions.checkState(invalidBlocks.isTag(), "invalidBlocks must be a tag in this version of Minecraft");
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
            cannotReplace.asTagKey(),
            invalidBlocks.asTagKey()
        );
    }
}