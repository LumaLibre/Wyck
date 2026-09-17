package dev.wyck.worldgen.feature.configurations;

import dev.wyck.worldgen.valueproviders.IntProvider;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record ReplaceBlobsFeatureImpl(
    @Override BlockData targetState,
    @Override BlockData replaceState,
    @Override IntProvider radius
) implements ReplaceBlobsFeature {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.ReplaceBlobsFeature(
            ((CraftBlockData) targetState).getState(),
            ((CraftBlockData) replaceState).getState(),
            radius.asHandle()
        );
    }
}
