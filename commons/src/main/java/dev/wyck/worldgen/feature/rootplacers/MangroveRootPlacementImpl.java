package dev.wyck.worldgen.feature.rootplacers;

import dev.wyck.util.WorldgenConversions;
import dev.wyck.worldgen.feature.rootplacers.MangroveRootPlacement;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Set;

@NullMarked
@ApiStatus.Internal
public record MangroveRootPlacementImpl(
    @Override Set<Material> canGrowThrough,
    @Override Set<Material> muddyRootsIn,
    @Override BlockStateProvider muddyRootsProvider,
    @Override int maxRootWidth,
    @Override int maxRootLength,
    @Override float randomSkewChance
) implements MangroveRootPlacement {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.rootplacers.MangroveRootPlacement(
            WorldgenConversions.toBlockHolderSet(canGrowThrough),
            WorldgenConversions.toBlockHolderSet(muddyRootsIn),
            muddyRootsProvider.asHandle(),
            maxRootWidth,
            maxRootLength,
            randomSkewChance
        );
    }
}
