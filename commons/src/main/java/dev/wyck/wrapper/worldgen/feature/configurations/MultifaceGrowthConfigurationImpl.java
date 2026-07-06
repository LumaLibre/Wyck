package dev.wyck.wrapper.worldgen.feature.configurations;

import dev.wyck.wrapper.worldgen.WorldgenConversions;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.Set;

@NullMarked
@ApiStatus.Internal
public record MultifaceGrowthConfigurationImpl(
    @Override Material placeBlock,
    @Override int searchRange,
    @Override boolean canPlaceOnFloor,
    @Override boolean canPlaceOnCeiling,
    @Override boolean canPlaceOnWall,
    @Override float chanceOfSpreading,
    @Override Set<Material> canBePlacedOn
) implements MultifaceGrowthConfiguration {
    @Override
    public Object toMinecraft() {
        List<net.minecraft.world.level.block.Block> resolved = WorldgenConversions.toBlockList(List.of(placeBlock));
        net.minecraft.world.level.block.Block block = resolved.getFirst();
        com.google.common.base.Preconditions.checkArgument(
            block instanceof net.minecraft.world.level.block.MultifaceSpreadeableBlock,
            "placeBlock " + placeBlock + " must be a multiface spreadeable block"
        );
        net.minecraft.world.level.block.MultifaceSpreadeableBlock multiface = (net.minecraft.world.level.block.MultifaceSpreadeableBlock) block;

        net.minecraft.core.HolderSet<net.minecraft.world.level.block.Block> placeableOn = WorldgenConversions.toBlockHolderSet(canBePlacedOn);

        return new net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration(
            multiface,
            searchRange,
            canPlaceOnFloor,
            canPlaceOnCeiling,
            canPlaceOnWall,
            chanceOfSpreading,
            placeableOn
        );
    }
}