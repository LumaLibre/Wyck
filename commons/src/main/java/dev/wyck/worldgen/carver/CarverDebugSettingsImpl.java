package dev.wyck.worldgen.carver;

import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record CarverDebugSettingsImpl(
    @Override boolean debugMode,
    @Override BlockData airState,
    @Override BlockData waterState,
    @Override BlockData lavaState,
    @Override BlockData barrierState
) implements CarverDebugSettings {
    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.carver.CarverDebugSettings.of(
            debugMode,
            ((CraftBlockData) airState).getState(),
            ((CraftBlockData) waterState).getState(),
            ((CraftBlockData) lavaState).getState(),
            ((CraftBlockData) barrierState).getState()
        );
    }
}
