package dev.wyck.worldgen.feature.configurations;

import dev.wyck.worldgen.valueproviders.IntProvider;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record ReplaceSphereConfigurationImpl(
    @Override BlockData targetState,
    @Override BlockData replaceState,
    @Override IntProvider radius
) implements ReplaceSphereConfiguration {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.configurations.ReplaceSphereConfiguration(
            ((CraftBlockData) targetState).getState(),
            ((CraftBlockData) replaceState).getState(),
            radius.asHandle()
        );
    }
}