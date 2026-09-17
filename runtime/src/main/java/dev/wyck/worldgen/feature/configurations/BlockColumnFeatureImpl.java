package dev.wyck.worldgen.feature.configurations;

import dev.wyck.util.WorldgenConversions;
import dev.wyck.worldgen.blockpredicates.BlockPredicate;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@ApiStatus.Internal
public record BlockColumnFeatureImpl(
    @Override List<BlockColumnFeature.Layer> layers,
    @Override BlockFace direction,
    @Override BlockPredicate allowedPlacement,
    @Override boolean prioritizeTip
) implements BlockColumnFeature {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.BlockColumnFeature(
            layers.stream().map(this::layerHandle).toList(),
            WorldgenConversions.toNmsDirection(direction),
            allowedPlacement.asHandle(),
            prioritizeTip
        );
    }

    private net.minecraft.world.level.levelgen.feature.BlockColumnFeature.Layer layerHandle(BlockColumnFeature.Layer layer) {
        return new net.minecraft.world.level.levelgen.feature.BlockColumnFeature.Layer(
            layer.height().asHandle(),
            layer.state().asHandle()
        );
    }
}
