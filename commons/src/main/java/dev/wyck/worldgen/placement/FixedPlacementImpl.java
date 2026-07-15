package dev.wyck.worldgen.placement;

import dev.wyck.util.WorldgenConversions;
import dev.wyck.worldgen.placement.FixedPlacement;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@ApiStatus.Internal
public record FixedPlacementImpl(@Override List<BlockVector> positions) implements FixedPlacement {
    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.placement.FixedPlacement.of(
            positions.stream().map(WorldgenConversions::toBlockPos).toArray(net.minecraft.core.BlockPos[]::new)
        );
    }
}
