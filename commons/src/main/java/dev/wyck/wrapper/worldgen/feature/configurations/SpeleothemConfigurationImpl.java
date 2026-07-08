package dev.wyck.wrapper.worldgen.feature.configurations;

import dev.wyck.util.WorldgenConversions;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Set;

@NullMarked
@ApiStatus.Internal
public record SpeleothemConfigurationImpl(
    @Override BlockData baseBlock,
    @Override BlockData pointedBlock,
    @Override Set<Material> replaceableBlocks,
    @Override float chanceOfTallerGeneration,
    @Override float chanceOfDirectionalSpread,
    @Override float chanceOfSpreadRadius2,
    @Override float chanceOfSpreadRadius3
) implements SpeleothemConfiguration {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.configurations.SpeleothemConfiguration(
            ((CraftBlockData) baseBlock).getState(),
            ((CraftBlockData) pointedBlock).getState(),
            WorldgenConversions.toBlockHolderSet(replaceableBlocks),
            chanceOfTallerGeneration,
            chanceOfDirectionalSpread,
            chanceOfSpreadRadius2,
            chanceOfSpreadRadius3
        );
    }
}