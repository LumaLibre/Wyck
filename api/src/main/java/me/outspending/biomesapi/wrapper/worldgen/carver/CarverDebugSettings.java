package me.outspending.biomesapi.wrapper.worldgen.carver;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.wrapper.NmsHandle;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Wraps Minecraft's CarverDebugSettings, the block states a carver paints
 * in air, water, lava, and barrier positions when debug mode is enabled.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public record CarverDebugSettings(
        boolean debugMode,
        BlockData airState,
        BlockData waterState,
        BlockData lavaState,
        BlockData barrierState
) implements NmsHandle {

    @ApiStatus.Internal
    private static final WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.worldgen.carver.CarverDebugSettingsFactoryImpl");

    @ApiStatus.Internal
    protected interface Factory {
        Object toNms(CarverDebugSettings settings);
    }

    @AsOf("2.3.0")
    public static CarverDebugSettings of(boolean debugMode, BlockData airState, BlockData waterState, BlockData lavaState, BlockData barrierState) {
        return new CarverDebugSettings(debugMode, airState, waterState, lavaState, barrierState);
    }

    @AsOf("2.3.0")
    public static CarverDebugSettings of(BlockData airState, BlockData waterState, BlockData lavaState, BlockData barrierState) {
        return new CarverDebugSettings(false, airState, waterState, lavaState, barrierState);
    }

    @AsOf("2.3.0")
    public static CarverDebugSettings defaultSettings() {
        return new CarverDebugSettings(
                false,
                Material.ACACIA_BUTTON.createBlockData(),
                Material.CANDLE.createBlockData(),
                Material.ORANGE_STAINED_GLASS.createBlockData(),
                Material.GLASS.createBlockData()
        );
    }

    @Override
    @AsOf("2.3.0")
    public Object toMinecraft() {
        return WIRE.get().toNms(this);
    }
}