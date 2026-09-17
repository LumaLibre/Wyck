package dev.wyck.worldgen.feature.configurations;

import dev.wyck.worldgen.valueproviders.IntProvider;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record DeltaFeatureImpl(
    @Override BlockData contents,
    @Override BlockData rim,
    @Override IntProvider size,
    @Override IntProvider rimSize
) implements DeltaFeature {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.DeltaFeature(
            ((CraftBlockData) contents).getState(),
            ((CraftBlockData) rim).getState(),
            size.asHandle(),
            rimSize.asHandle()
        );
    }
}