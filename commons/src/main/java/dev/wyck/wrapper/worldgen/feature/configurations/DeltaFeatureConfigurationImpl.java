package dev.wyck.wrapper.worldgen.feature.configurations;

import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record DeltaFeatureConfigurationImpl(
    @Override BlockData contents,
    @Override BlockData rim,
    @Override IntProvider size,
    @Override IntProvider rimSize
) implements DeltaFeatureConfiguration {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.configurations.DeltaFeatureConfiguration(
            ((CraftBlockData) contents).getState(),
            ((CraftBlockData) rim).getState(),
            size.asHandle(),
            rimSize.asHandle()
        );
    }
}