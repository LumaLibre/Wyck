package dev.wyck.wrapper.worldgen.placement;

import dev.wyck.wrapper.worldgen.blockpredicates.BlockPredicate;
import dev.wyck.util.WorldgenConversions;
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
