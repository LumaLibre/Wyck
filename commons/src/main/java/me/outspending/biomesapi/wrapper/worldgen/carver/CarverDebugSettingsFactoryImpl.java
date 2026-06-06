package me.outspending.biomesapi.wrapper.worldgen.carver;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
public final class CarverDebugSettingsFactoryImpl implements CarverDebugSettings.Factory {

    @Override
    public @NotNull Object toNms(@NotNull CarverDebugSettings settings) {
        BlockState air = toNmsState(settings.airState());
        BlockState water = toNmsState(settings.waterState());
        BlockState lava = toNmsState(settings.lavaState());
        BlockState barrier = toNmsState(settings.barrierState());
        return net.minecraft.world.level.levelgen.carver.CarverDebugSettings.of(settings.debugMode(), air, water, lava, barrier);
    }

    private BlockState toNmsState(@NotNull BlockData data) {
        return ((CraftBlockData) data).getState();
    }
}