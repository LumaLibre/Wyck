package dev.wyck.worldgen;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.Generated;
import dev.wyck.wrapper.KeyedEnumTranslator;
import dev.wyck.wrapper.WrappedEnumerator;
import java.lang.Override;
import java.lang.String;
import org.jspecify.annotations.NullMarked;

/**
 * Auto-generated. Do not modify!
 * Run ./gradlew generateSources to regenerate.
 * <p>
 * Wraps Minecraft's Heightmap.Types.
 * </p>
 *
 *
 * @since 3.0.0
 * @version 3.4.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("3.0.0")
@Generated("2026-09-08T15:00:53.387479400Z")
public enum HeightmapType implements WrappedEnumerator<HeightmapType> {
    WORLD_SURFACE_WG("WORLD_SURFACE_WG"),

    WORLD_SURFACE("WORLD_SURFACE"),

    OCEAN_FLOOR_WG("OCEAN_FLOOR_WG"),

    OCEAN_FLOOR("OCEAN_FLOOR"),

    MOTION_BLOCKING("MOTION_BLOCKING"),

    MOTION_BLOCKING_NO_LEAVES("MOTION_BLOCKING_NO_LEAVES");

    public static final KeyedEnumTranslator<HeightmapType> TRANSLATOR = KeyedEnumTranslator.byKey(HeightmapType::getKey, HeightmapType.values());

    private final String key;

    @AsOf("2.3.0")
    HeightmapType(String key) {
        this.key = key;
    }

    @AsOf("3.0.0")
    @Override
    public KeyedEnumTranslator<HeightmapType> translator() {
        return TRANSLATOR;
    }

    /**
     *  The vanilla name for this Types
     *  @return the vanilla key for this enum value
     *  @since 3.0.0
     */
    @AsOf("3.0.0")
    public String getKey() {
        return this.key;
    }
}
