package me.outspending.biomesapi.wrapper.worldgen;

import com.mojang.serialization.Codec;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.internal.KeyedEnumTranslator;
import me.outspending.biomesapi.wrapper.internal.NmsEnumTranslatable;
import org.jspecify.annotations.NullMarked;

/**
 * Wraps Minecraft's Heightmap.Types, the heightmap a placement samples against.
 *
 * @version 2.3.0
 * @since 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public enum HeightmapType implements NmsEnumTranslatable<HeightmapType> {

    WORLD_SURFACE_WG("WORLD_SURFACE_WG"),
    WORLD_SURFACE("WORLD_SURFACE"),
    OCEAN_FLOOR_WG("OCEAN_FLOOR_WG"),
    OCEAN_FLOOR("OCEAN_FLOOR"),
    MOTION_BLOCKING("MOTION_BLOCKING"),
    MOTION_BLOCKING_NO_LEAVES("MOTION_BLOCKING_NO_LEAVES");

    public static final KeyedEnumTranslator<HeightmapType> TRANSLATOR = KeyedEnumTranslator.byKey(HeightmapType::getKey, HeightmapType.values());
    public static final Codec<HeightmapType> CODEC = TRANSLATOR.codec();

    private final String key;

    @AsOf("2.3.0")
    HeightmapType(String key) {
        this.key = key;
    }

    /**
     * The vanilla serialized name for this heightmap type.
     * @return the vanilla key for this enum value
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public String getKey() {
        return this.key;
    }

    @Override
    @AsOf("2.3.0")
    public KeyedEnumTranslator<HeightmapType> translator() {
        return TRANSLATOR;
    }
}