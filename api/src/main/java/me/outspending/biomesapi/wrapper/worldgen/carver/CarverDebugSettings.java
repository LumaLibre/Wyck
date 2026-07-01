package me.outspending.biomesapi.wrapper.worldgen.carver;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.serialization.Codecs;
import me.outspending.biomesapi.wrapper.internal.NmsDecoder;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
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

    public static final Codec<CarverDebugSettings> CODEC = RecordCodecBuilder.create(i -> i.group(
        Codec.BOOL.fieldOf("debug_mode").forGetter(CarverDebugSettings::debugMode),
        Codecs.MATERIAL_CODEC.fieldOf("air_state").forGetter(CarverDebugSettings::airState),
        Codecs.MATERIAL_CODEC.fieldOf("water_state").forGetter(CarverDebugSettings::waterState),
        Codecs.MATERIAL_CODEC.fieldOf("lava_state").forGetter(CarverDebugSettings::lavaState),
        Codecs.MATERIAL_CODEC.fieldOf("barrier_state").forGetter(CarverDebugSettings::barrierState)
    ).apply(i, CarverDebugSettings::new));


    @ApiStatus.Internal
    private static final WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.worldgen.carver.CarverDebugSettingsFactoryImpl");

    @ApiStatus.Internal
    protected interface Factory extends NmsDecoder<CarverDebugSettings> {
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

    @AsOf("2.4.0")
    static CarverDebugSettings fromMinecraft(Object nms) {
        return WIRE.get().fromMinecraft(nms);
    }
}