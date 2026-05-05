package me.outspending.biomesapi.wrapper.environment.attribute;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.Activity;
import me.outspending.biomesapi.wrapper.environment.BedRule;
import me.outspending.biomesapi.wrapper.environment.MoonPhase;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleCatalog;
import me.outspending.biomesapi.wrapper.environment.particle.WrappedAmbientParticle;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A registry of wrapped environment attribute suppliers,
 * which provide access to the various attributes that can be defined for biomes.
 *
 * @since 1.1.0
 * @version 2.1.0
 * @author Jsinco
 */
@AsOf("2.1.0")
public final class WrappedEnvironmentAttributes {

    /** Color of the fog when underwater (also affected by time of day, weather, and potion effects) */
    public static final IntColorSupplier FOG_COLOR = colorSupplier("visual/fog_color");

    /** Distance in blocks from where the fog starts to have an effect. If negative, fog already exists at 0 distance. */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> FOG_START_DISTANCE = supplier("visual/fog_start_distance");

    /** Distance in blocks when the fog affecting the clouds reaches maximum density.
     * (also affected by the cloud distance option) */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> FOG_END_DISTANCE = supplier("visual/fog_end_distance");

    /** Distance in blocks when the fog affecting the sky reaches maximum density. (also affected by the render distance) */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> SKY_FOG_END_DISTANCE = supplier("visual/sky_fog_end_distance");

    /** Distance in blocks when the fog affecting the clouds reaches maximum density.
     * (also affected by the cloud distance option) */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> CLOUD_FOG_END_DISTANCE = supplier("visual/cloud_fog_end_distance");

    /** Color of the fog when underwater (also affected by time of day, weather, and potion effects) */
    public static final IntColorSupplier WATER_FOG_COLOR = colorSupplier("visual/water_fog_color");

    /** Distance in blocks from where the underwater fog starts to have an effect.
     * If negative, fog already exists at 0 distance.
     * (also affected by how long the player has been underwater) */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> WATER_FOG_START_DISTANCE = supplier("visual/water_fog_start_distance");

    /** Distance in blocks when the underwater fog reaches maximum density
     * (also affected by how long the player has been underwater) */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> WATER_FOG_END_DISTANCE = supplier("visual/water_fog_end_distance");

    /** Color of the sky
     * (also affected by time of day and weather) */
    public static final IntColorSupplier SKY_COLOR = colorSupplier("visual/sky_color");

    /** Color and intensity of sunrise and sunset. Only used with the overworld skybox type. */
    public static final IntColorSupplier SUNRISE_SUNSET_COLOR = colorSupplier("visual/sunrise_sunset_color");

    /** Opacity of the clouds.
     * If fully transparent, Happy Ghast does not regenerate health faster when at cloud height. */
    public static final IntColorSupplier CLOUD_COLOR = colorSupplier("visual/cloud_color");

    /** Height of the clouds */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> CLOUD_HEIGHT = supplier("visual/cloud_height");

    /** Position of the sun in degrees from east to west. 0 is directly up. Only used with the overworld skybox type. */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> SUN_ANGLE = supplier("visual/sun_angle");

    /** Position of the moon in degrees from east to west. 0 is directly up. Only used with the overworld skybox type. */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> MOON_ANGLE = supplier("visual/moon_angle");

    /** Position of the sun in degrees from east to west. Only used with the overworld skybox type. */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> STAR_ANGLE = supplier("visual/star_angle");

    /** Phase of the moon. Only used with the overworld skybox type.
     * @deprecated No consumers at a biome level (dimensions only). */
    @Deprecated
    public static final WrappedEnvironmentAttributeSupplier<Object, MoonPhase> MOON_PHASE = supplierWith("visual/moon_phase", AttributeConverters.MOON_PHASE);

    /** Brightness of the stars. 0.5 is the default night start brightness. Only used with the overworld skybox type. */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> STAR_BRIGHTNESS = supplier("visual/star_brightness");

    /** Tint of the block light.
     * Block light color starts as dark gray at low light levels,
     * becomes tinted by this attribute at medium levels, and turns white at high levels.
     * By default, it provides the yellowish tint of torches.*/
    public static final IntColorSupplier BLOCK_LIGHT_TINT = colorSupplier("visual/block_light_tint");

    /** Color of the skylight.
     * Has no effect on blocks with a skylight of 0.
     * This value is passed to the lightmap.fsh shader as SkyLightColor */
    public static final IntColorSupplier SKY_LIGHT_COLOR = colorSupplier("visual/sky_light_color");

    /** Visual brightness of the skylight.
     * The skylight color is multiplied with this value and passed to the lightmap.fsh shader as SkyFactor */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> SKY_LIGHT_FACTOR = supplier("visual/sky_light_factor");

    /** Used similarly to ambient light color
     * When the night vision effect is active,
     * a per-component maximum of visual/night_vision_color and visual/ambient_light_color is used as ambient color.
     * Night Vision is not tinted by default. */
    public static final IntColorSupplier NIGHT_VISION_COLOR = colorSupplier("visual/night_vision_color");

    /** Defines both the ambient light tint and brightness
     This light is applied to the world at 0 light levels.
     Block and skylight are added on top of it. */
    public static final IntColorSupplier AMBIENT_LIGHT_COLOR = colorSupplier("visual/ambient_light_color");

    /** The particle generated by downward pointed dripstone when no liquid is above.
     * @deprecated No consumers at a biome level (dimensions only). */
    @Deprecated
    public static final WrappedEnvironmentAttributeSupplier<Object, WrappedAmbientParticle<?>> DEFAULT_DRIPSTONE_PARTICLE = supplierWith("visual/default_dripstone_particle", AttributeConverters.DRIPSTONE_PARTICLE);

    /** Particles that spawn randomly around the camera where probability is the chance to spawn a particle in an empty space */
    public static final WrappedEnvironmentAttributeSupplier<Object, ParticleCatalog> AMBIENT_PARTICLES = supplierWith("visual/ambient_particles", AttributeConverters.AMBIENT_PARTICLES);

    /** Volume of the music. Music volume will fade over time to this value. */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> MUSIC_VOLUME = supplier("audio/music_volume");

    /** Whether a firefly bush plays ambient sounds when not under an opaque block. */
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> FIREFLY_BUSH_SOUNDS = supplier("audio/firefly_bush_sounds");

    /** The internal skylight level (inversely known as the "darkening" skylight level), not the actual skylight level.
     * This affects mechanics such as monster spawning, daylight detector power, and ice melting.
     * The visual effect is controlled by visual/sky_light_factor.
     * @deprecated Not positional or spatially interpolated (dimensions only). */
    @Deprecated
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> SKY_LIGHT_LEVEL = supplier("gameplay/sky_light_level");

    /** Whether a player with Raid Omen can start a raid.
     * @deprecated No consumers at a biome level (dimensions only). */
    @Deprecated
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> CAN_START_RAID = supplier("gameplay/can_start_raid");

    /** Whether water can be placed from a bucket, or by melting ice, or whether a wet sponge will dry out.
     * @deprecated No consumers at a biome level, but the client will recognize this attribute and spawn particles above placed water.*/
    @Deprecated
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> WATER_EVAPORATES = supplier("gameplay/water_evaporates");

    /** Controls behavior of interacting with beds.
     * @deprecated No consumers at a biome level (dimensions only). */
    @Deprecated
    public static final WrappedEnvironmentAttributeSupplier<Object, BedRule> BED_RULE = supplierWith("gameplay/bed_rule", AttributeConverters.BED_RULE);

    /** Whether a respawn anchor can be used to set spawn. If set to false, a respawn anchor explodes once used.
     * @deprecated No consumers at a biome level (dimensions only). */
    @Deprecated
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> RESPAWN_ANCHOR_WORKS = supplier("gameplay/respawn_anchor_works");

    /** Whether a nether portal can spawn zombified piglins.
     * @deprecated No consumers at a biome level (dimensions only). */
    @Deprecated
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> NETHER_PORTAL_SPAWNS_PIGLIN = supplier("gameplay/nether_portal_spawns_piglin");
    /** Whether lava should spread faster and further, and have a stronger pushing force on entities.
     * @deprecated Not positional or spatially interpolated (dimensions only). */
    @Deprecated
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> FAST_LAVA = supplier("gameplay/fast_lava");

    /** Whether fire should burn out more quickly. */
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> INCREASED_FIRE_BURNOUT = supplier("gameplay/increased_fire_burnout");

    /** Chance for a turtle egg to progress to its next hatching state on a random tick.
     * @deprecated No consumers at a biome level (timelines only). */
    @Deprecated
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> TURTLE_EGG_HATCH_CHANCE = supplier("gameplay/turtle_egg_hatch_chance");

    /** Whether piglins and hoglins zombify.
     * @deprecated No consumers at a biome level (dimensions only). */
    @Deprecated
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> PIGLINS_ZOMBIFY = supplier("gameplay/piglins_zombify");

    /** Whether snow golems should take damage passively. */
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> SNOW_GOLEM_MELTS = supplier("gameplay/snow_golem_melts");

    /** When true, a creaking heart will become active. When false, it will become inactive. */
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> CREAKING_ACTIVE = supplier("gameplay/creaking_active");

    /** Chance of additional slime spawn on the surface in biomes in the #allows_surface_slime_spawns tag. */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> SURFACE_SLIME_SPAWN_CHANCE = supplier("gameplay/surface_slime_spawn_chance");

    /** Chance of a cat dropping a gift when the player wakes up.
     * @deprecated No consumers at a biome level (timelines only). */
    @Deprecated
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> CAT_WAKING_UP_GIFT_CHANCE = supplier("gameplay/cat_waking_up_gift_chance");

    /** When true, bees will pathfind to beehives and not exit (except when the Hive is broken or next to fire).
     * @deprecated No consumers at a biome level (timelines/weather only). */
    @Deprecated
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> BEES_STAY_IN_HIVE = supplier("gameplay/bees_stay_in_hive");

    /** Whether monsters burn when exposed to the sky.
     * @deprecated No consumers at a biome level (dimensions/timelines only). */
    @Deprecated
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> MONSTERS_BURN = supplier("gameplay/monsters_burn");

    /** Whether patrols can spawn when the global skylight is greater than 11. */
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> CAN_PILLAGER_PATROL_SPAWN = supplier("gameplay/can_pillager_patrol_spawn");

    /** Controls the default AI activity of villagers.
     * Core, hide, idle, meet, panic, pre_raid, raid, rest, or work.
     * Other activities are allowed causing the villager to do nothing.
     * @deprecated No consumers at a biome level (timelines only). */
    @Deprecated
    public static final WrappedEnvironmentAttributeSupplier<Object, Activity> VILLAGER_ACTIVITY = supplierWith("gameplay/villager_activity", AttributeConverters.ACTIVITY);

    /** Controls the default AI activity of baby villagers.
     * Core, hide, idle, meet, panic, pre_raid, raid, rest, or work.
     * Other activities are allowed causing the villager to do nothing.
     * @deprecated No consumers at a biome level (timelines only). */
    @Deprecated
    public static final WrappedEnvironmentAttributeSupplier<Object, Activity> BABY_VILLAGER_ACTIVITY = supplierWith("gameplay/baby_villager_activity", AttributeConverters.ACTIVITY);

    /** The background music to play.
     * @apiNote The value type ({@code BackgroundMusic}) is an NMS-only
     * type without an API wrapper yet - pass an instance obtained directly from NMS. */
    @ApiStatus.Experimental
    public static final WrappedEnvironmentAttributeSupplier<Object, Object> BACKGROUND_MUSIC = supplier("audio/background_music");

    /** Controls ambient sounds that are played around the camera.
     * @apiNote The value type ({@code AmbientSounds}) is an NMS-only
     * type without an API wrapper yet - pass an instance obtained directly from NMS. */
    @ApiStatus.Experimental
    public static final WrappedEnvironmentAttributeSupplier<Object, Object> AMBIENT_SOUNDS = supplier("audio/ambient_sounds");

    /**
     * How eyeblossoms should behave.
     * True: eyeblossoms will open
     * False: eyeblossoms will close
     * Default: eyeblossom will stay in their current state
     * @apiNote The value type ({@code TriState})
     * is an NMS-only type without an API wrapper yet - pass an instance obtained directly from NMS.
     * @deprecated No consumers at a biome level (timelines only). */
    @Deprecated
    @ApiStatus.Experimental
    public static final WrappedEnvironmentAttributeSupplier<Object, Object> EYEBLOSSOM_OPEN = supplier("gameplay/eyeblossom_open");


    @ApiStatus.Internal
    private static Map<String, WrappedEnvironmentAttributeSupplier<?, ?>> BY_ID;
    @ApiStatus.Internal
    private static volatile Map<String, WrappedEnvironmentAttributeSupplier<?, ?>> BY_NAME;

    /**
     * Returns the supplier registered under the given NMS attribute id (e.g. {@code "visual/fog_color"}),
     * or null if none exists.
     */
    @AsOf("2.1.0")
    public static @Nullable WrappedEnvironmentAttributeSupplier<?, ?> byId(@NotNull String id) {
        return byIdMap().get(id);
    }

    /**
     * Returns the supplier whose constant name matches (e.g. {@code "FOG_COLOR"}), or null if none exists.
     */
    @AsOf("2.1.0")
    public static @Nullable WrappedEnvironmentAttributeSupplier<?, ?> byName(@NotNull String name) {
        Map<String, WrappedEnvironmentAttributeSupplier<?, ?>> map = BY_NAME;
        if (map == null) {
            synchronized (WrappedEnvironmentAttributes.class) {
                map = BY_NAME;
                if (map == null) {
                    map = buildNameIndex();
                    BY_NAME = map;
                }
            }
        }
        return map.get(name);
    }

    /**
     * All registered suppliers (excluding Unmapped). Iteration order matches declaration order.
     */
    @AsOf("2.1.0")
    public static @NotNull Collection<WrappedEnvironmentAttributeSupplier<?, ?>> values() {
        return Collections.unmodifiableCollection(byIdMap().values());
    }

    /**
     * All registered ids (excluding Unmapped). Iteration order matches declaration order.
     */
    @AsOf("2.1.0")
    public static @NotNull Collection<String> ids() {
        return Collections.unmodifiableCollection(byIdMap().keySet());
    }


    private static <T, K> WrappedEnvironmentAttributeSupplier<T, K> supplier(String key) {
        WrappedEnvironmentAttributeSupplier<T, K> s = new WrappedEnvironmentAttributeSupplier<>(() -> {
            EnvironmentAttributeHandle<T> handle = EnvironmentAttributeFactory.WIRE.get().byKey(key);
            return WrappedEnvironmentAttribute.of(handle);
        });
        byIdMap().put(key, s);
        return s;
    }

    private static <K> WrappedEnvironmentAttributeSupplier<Object, K> supplierWith(String key, WrappedEnvironmentAttribute.Converter<Object, K> converter) {
        WrappedEnvironmentAttributeSupplier<Object, K> s = new WrappedEnvironmentAttributeSupplier<>(() -> {
            EnvironmentAttributeHandle<Object> handle = EnvironmentAttributeFactory.WIRE.get().byKey(key);
            return WrappedEnvironmentAttribute.of(handle, converter);
        });
        byIdMap().put(key, s);
        return s;
    }

    private static IntColorSupplier colorSupplier(String key) {
        IntColorSupplier s = new IntColorSupplier(() -> {
            EnvironmentAttributeHandle<Integer> handle = EnvironmentAttributeFactory.WIRE.get().byKey(key);
            return WrappedEnvironmentAttribute.of(handle);
        });
        byIdMap().put(key, s);
        return s;
    }

    private static Map<String, WrappedEnvironmentAttributeSupplier<?, ?>> byIdMap() {
        Map<String, WrappedEnvironmentAttributeSupplier<?, ?>> map = BY_ID;
        if (map == null) {
            map = new LinkedHashMap<>();
            BY_ID = map;
        }
        return map;
    }

    private static Map<String, WrappedEnvironmentAttributeSupplier<?, ?>> buildNameIndex() {
        Map<String, WrappedEnvironmentAttributeSupplier<?, ?>> map = new LinkedHashMap<>();
        collectStaticFields(map);
        return Collections.unmodifiableMap(map);
    }

    private static void collectStaticFields(Map<String, WrappedEnvironmentAttributeSupplier<?, ?>> out) {
        for (Field field : WrappedEnvironmentAttributes.class.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) continue;
            if (!WrappedEnvironmentAttributeSupplier.class.isAssignableFrom(field.getType())) continue;
            try {
                field.setAccessible(true);
                Object value = field.get(null);
                if (value instanceof WrappedEnvironmentAttributeSupplier<?, ?> supplier) {
                    out.put(field.getName(), supplier);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private WrappedEnvironmentAttributes() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}