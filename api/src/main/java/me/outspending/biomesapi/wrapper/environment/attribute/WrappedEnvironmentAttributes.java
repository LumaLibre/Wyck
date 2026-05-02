package me.outspending.biomesapi.wrapper.environment.attribute;

import me.outspending.biomesapi.annotations.AsOf;
import org.jetbrains.annotations.ApiStatus;

/**
 * A collection of wrapped environment attributes mirroring vanilla EnvironmentAttributes.
 * Each constant is resolved lazily through the registered EnvironmentAttributeFactory.
 *
 * @see WrappedEnvironmentAttributeMap
 * @version 1.1.0
 * @since 1.1.0
 * @author Jsinco
 */
@AsOf("1.1.0")
@ApiStatus.Experimental
public final class WrappedEnvironmentAttributes {

    // Visual
    /** Changes the fog color in the biome. */
    public static final WrappedEnvironmentAttributeSupplier<Integer, Integer> FOG_COLOR = supplier("fog_color");
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> FOG_START_DISTANCE = supplier("fog_start_distance");
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> FOG_END_DISTANCE = supplier("fog_end_distance");
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> SKY_FOG_END_DISTANCE = supplier("sky_fog_end_distance");
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> CLOUD_FOG_END_DISTANCE = supplier("cloud_fog_end_distance");
    /** Changes the player's fog color when swimming */
    public static final WrappedEnvironmentAttributeSupplier<Integer, Integer> WATER_FOG_COLOR = supplier("water_fog_color");
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> WATER_FOG_START_DISTANCE = supplier("water_fog_start_distance");
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> WATER_FOG_END_DISTANCE = supplier("water_fog_end_distance");
    /** Changes the sky color */
    public static final WrappedEnvironmentAttributeSupplier<Integer, Integer> SKY_COLOR = supplier("sky_color");
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Integer, Integer> SUNRISE_SUNSET_COLOR = supplier("sunrise_sunset_color");
    /** Changes the clouds colors */
    public static final WrappedEnvironmentAttributeSupplier<Integer, Integer> CLOUD_COLOR = supplier("cloud_color");
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> CLOUD_HEIGHT = supplier("cloud_height");
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> SUN_ANGLE = supplier("sun_angle");
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> MOON_ANGLE = supplier("moon_angle");
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> STAR_ANGLE = supplier("star_angle");
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> STAR_BRIGHTNESS = supplier("star_brightness");
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Integer, Integer> SKY_LIGHT_COLOR = supplier("sky_light_color");
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> SKY_LIGHT_FACTOR = supplier("sky_light_factor");
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> MUSIC_VOLUME = supplier("music_volume");

    // Audio
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> FIREFLY_BUSH_SOUNDS = supplier("firefly_bush_sounds");

    // Gameplay
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> SKY_LIGHT_LEVEL = supplier("sky_light_level");
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> CAN_START_RAID = supplier("can_start_raid");
    /** Spawns particles when placing water */
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> WATER_EVAPORATES = supplier("water_evaporates");
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> RESPAWN_ANCHOR_WORKS = supplier("respawn_anchor_works");
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> NETHER_PORTAL_SPAWNS_PIGLINS = supplier("nether_portal_spawns_piglins");
    /** Appears to do nothing currently */
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> FAST_LAVA = supplier("fast_lava");
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> INCREASED_FIRE_BURNOUT = supplier("increased_fire_burnout");
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> TURTLE_EGG_HATCH_CHANCE = supplier("turtle_egg_hatch_chance");
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> PIGLINS_ZOMBIFY = supplier("piglins_zombify");
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> SNOW_GOLEM_MELTS = supplier("snow_golem_melts");
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> CREAKING_ACTIVE = supplier("creaking_active");
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> SURFACE_SLIME_SPAWN_CHANCE = supplier("surface_slime_spawn_chance");
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> CAT_WAKING_UP_GIFT_CHANCE = supplier("cat_waking_up_gift_chance");
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> BEES_STAY_IN_HIVE = supplier("bees_stay_in_hive");
    /** Appears to do nothing currently */
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> MONSTERS_BURN = supplier("monsters_burn");
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> CAN_PILLAGER_PATROL_SPAWN = supplier("can_pillager_patrol_spawn");


    private WrappedEnvironmentAttributes() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    private static <T, K> WrappedEnvironmentAttributeSupplier<T, K> supplier(String key) {
        return new WrappedEnvironmentAttributeSupplier<>(() -> {
            EnvironmentAttributeHandle<T> handle = EnvironmentAttributeFactory.WIRE.get().byKey(key);
            return WrappedEnvironmentAttribute.of(handle);
        });
    }

}