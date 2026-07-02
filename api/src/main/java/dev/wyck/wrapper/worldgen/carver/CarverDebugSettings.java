package dev.wyck.wrapper.worldgen.carver;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.WireProvider;
import dev.wyck.wrapper.internal.NmsHandle;
import org.bukkit.Material;
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
    Material airState,
    Material waterState,
    Material lavaState,
    Material barrierState
) implements NmsHandle {

    @ApiStatus.Internal
    private static final WireProvider<Factory> WIRE = WireProvider.create("dev.wyck.wrapper.worldgen.carver.CarverDebugSettingsFactoryImpl");

    @ApiStatus.Internal
    protected interface Factory {
        Object toNms(CarverDebugSettings settings);
    }

    @AsOf("2.3.0")
    public static CarverDebugSettings of(boolean debugMode, Material airState, Material waterState, Material lavaState, Material barrierState) {
        return new CarverDebugSettings(debugMode, airState, waterState, lavaState, barrierState);
    }

    @AsOf("2.3.0")
    public static CarverDebugSettings of(Material airState, Material waterState, Material lavaState, Material barrierState) {
        return new CarverDebugSettings(false, airState, waterState, lavaState, barrierState);
    }

    @AsOf("2.3.0")
    public static CarverDebugSettings defaultSettings() {
        return new CarverDebugSettings(
            false,
            Material.ACACIA_BUTTON,
            Material.CANDLE,
            Material.ORANGE_STAINED_GLASS,
            Material.GLASS
        );
    }

    @Override
    @AsOf("2.3.0")
    public Object toMinecraft() {
        return WIRE.get().toNms(this);
    }
}