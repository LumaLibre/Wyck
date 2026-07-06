package dev.wyck.wrapper.worldgen.feature.treedecorators;

import dev.wyck.wrapper.worldgen.WorldgenConversions;
import dev.wyck.wrapper.worldgen.stateproviders.BlockStateProvider;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@ApiStatus.Internal
public record AttachedToLeavesDecoratorImpl(
    @Override float probability,
    @Override int exclusionRadiusXZ,
    @Override int exclusionRadiusY,
    @Override BlockStateProvider blockProvider,
    @Override int requiredEmptyBlocks,
    @Override List<BlockFace> directions
) implements AttachedToLeavesDecorator {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.treedecorators.AttachedToLeavesDecorator(
            probability,
            exclusionRadiusXZ,
            exclusionRadiusY,
            blockProvider.asHandle(),
            requiredEmptyBlocks,
            directions.stream().map(WorldgenConversions::toNmsDirection).toList()
        );
    }
}
