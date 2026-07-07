package dev.wyck.v1_21_11.wrapper.worldgen.feature.configurations;

import dev.wyck.wrapper.worldgen.feature.configurations.SpeleothemConfiguration;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
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
        return new net.minecraft.world.level.levelgen.feature.configurations.PointedDripstoneConfiguration(
            chanceOfTallerGeneration,
            chanceOfDirectionalSpread,
            chanceOfSpreadRadius2,
            chanceOfSpreadRadius3
        );
    }
}