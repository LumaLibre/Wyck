package dev.wyck.wrapper.worldgen.feature.treedecorators;

import dev.wyck.util.WorldgenConversions;
import dev.wyck.wrapper.worldgen.stateproviders.BlockStateProvider;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@ApiStatus.Internal
public record AttachedToLogsDecoratorImpl(
    @Override float probability,
    @Override BlockStateProvider blockProvider,
    @Override List<BlockFace> directions
) implements AttachedToLogsDecorator {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.treedecorators.AttachedToLogsDecorator(
            probability,
            blockProvider.asHandle(),
            directions.stream().map(WorldgenConversions::toNmsDirection).toList()
        );
    }
}