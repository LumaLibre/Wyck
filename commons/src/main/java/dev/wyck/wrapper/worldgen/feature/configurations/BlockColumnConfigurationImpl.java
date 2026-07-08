package dev.wyck.wrapper.worldgen.feature.configurations;

import dev.wyck.wrapper.worldgen.blockpredicates.BlockPredicate;
import dev.wyck.util.WorldgenConversions;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@ApiStatus.Internal
public record BlockColumnConfigurationImpl(
    @Override List<BlockColumnConfiguration.Layer> layers,
    @Override BlockFace direction,
    @Override BlockPredicate allowedPlacement,
    @Override boolean prioritizeTip
) implements BlockColumnConfiguration {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration(
            layers.stream().map(this::layerHandle).toList(),
            WorldgenConversions.toNmsDirection(direction),
            allowedPlacement.asHandle(),
            prioritizeTip
        );
    }

    private net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration.Layer layerHandle(BlockColumnConfiguration.Layer layer) {
        return new net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration.Layer(
            layer.height().asHandle(),
            layer.state().asHandle()
        );
    }
}
