//105ce4876e36a3b7bc55e20b2f1a5a61
package dev.wyck.worldgen;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.Generated;
import dev.wyck.wrapper.KeyedEnumTranslator;
import dev.wyck.wrapper.WrappedEnumerator;
import org.jspecify.annotations.NullMarked;

/**
 * Auto-generated. Do not modify!
 * Run ./gradlew generateSources to regenerate.
 * <p>
 * Wraps Minecraft's Heightmap.Types.
 * </p>
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("3.0.0")
@Generated("2026-07-15T18:48:22.496744Z")
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

    /**
     * The vanilla name for this Types value.
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
