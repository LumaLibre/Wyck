package dev.wyck.worldgen.feature.rootplacers;

import dev.wyck.annotations.AsOf;
import dev.wyck.worldgen.feature.rootplacers.AboveRootPlacement;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("3.0.0")
public record AboveRootPlacementImpl(
    @Override BlockStateProvider aboveRootProvider,
    @Override float aboveRootPlacementChance
) implements AboveRootPlacement {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.rootplacers.AboveRootPlacement(
            aboveRootProvider.asHandle(),
            aboveRootPlacementChance
        );
    }
}
