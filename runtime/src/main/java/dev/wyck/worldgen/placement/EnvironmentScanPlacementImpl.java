package dev.wyck.worldgen.placement;

import dev.wyck.util.WorldgenConversions;
import dev.wyck.worldgen.blockpredicates.BlockPredicate;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record EnvironmentScanPlacementImpl(
    @Override BlockFace directionOfSearch,
    @Override BlockPredicate targetCondition,
    @Override BlockPredicate allowedSearchCondition,
    @Override int maxSteps
) implements EnvironmentScanPlacement {
    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement.scanningFor(
            WorldgenConversions.toNmsDirection(directionOfSearch),
            targetCondition.asHandle(),
            allowedSearchCondition.asHandle(),
            maxSteps
        );
    }
}
