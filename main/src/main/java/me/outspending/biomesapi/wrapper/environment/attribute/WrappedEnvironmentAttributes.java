package me.outspending.biomesapi.wrapper.environment.attribute;

import me.outspending.biomesapi.api.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.Activity;
import me.outspending.biomesapi.wrapper.environment.BedRule;
import me.outspending.biomesapi.wrapper.environment.MoonPhase;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleCatalog;
import me.outspending.biomesapi.wrapper.environment.particle.WrappedAmbientParticle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.attribute.AmbientParticle;
import net.minecraft.world.attribute.AmbientSounds;
import net.minecraft.world.attribute.BackgroundMusic;
import net.minecraft.world.attribute.EnvironmentAttributes;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

/**
 * A collection of wrapped environment attributes as they appear in Minecraft's {@link EnvironmentAttributes}.
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
    public static final WrappedEnvironmentAttributeSupplier<Integer, Integer> FOG_COLOR = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.FOG_COLOR);
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> FOG_START_DISTANCE = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.FOG_START_DISTANCE);
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> FOG_END_DISTANCE = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.FOG_END_DISTANCE);
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> SKY_FOG_END_DISTANCE = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.SKY_FOG_END_DISTANCE);
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> CLOUD_FOG_END_DISTANCE = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.CLOUD_FOG_END_DISTANCE);
    /** Changes the player's fog color when swimming */
    public static final WrappedEnvironmentAttributeSupplier<Integer, Integer> WATER_FOG_COLOR = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.WATER_FOG_COLOR);
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> WATER_FOG_START_DISTANCE = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.WATER_FOG_START_DISTANCE);
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> WATER_FOG_END_DISTANCE = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.WATER_FOG_END_DISTANCE);
    /** Changes the sky color */
    public static final WrappedEnvironmentAttributeSupplier<Integer, Integer> SKY_COLOR = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.SKY_COLOR);
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Integer, Integer> SUNRISE_SUNSET_COLOR = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.SUNRISE_SUNSET_COLOR);
    /** Changes the clouds colors */
    public static final WrappedEnvironmentAttributeSupplier<Integer, Integer> CLOUD_COLOR = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.CLOUD_COLOR);
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> CLOUD_HEIGHT = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.CLOUD_HEIGHT);
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> SUN_ANGLE = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.SUN_ANGLE);
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> MOON_ANGLE = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.MOON_ANGLE);
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> STAR_ANGLE = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.STAR_ANGLE);
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<net.minecraft.world.level.MoonPhase, MoonPhase> MOON_PHASE = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.MOON_PHASE, MoonPhase::getDelegatePhase);
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> STAR_BRIGHTNESS = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.STAR_BRIGHTNESS);
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Integer, Integer> SKY_LIGHT_COLOR = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.SKY_LIGHT_COLOR);
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> SKY_LIGHT_FACTOR = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.SKY_LIGHT_FACTOR);
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<ParticleOptions, WrappedAmbientParticle<?>> DEFAULT_DRIPSTONE_PARTICLE = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.DEFAULT_DRIPSTONE_PARTICLE, WrappedAmbientParticle::toMinecraftParticle);
    /** Adds visual particles to the biome */
    public static final WrappedEnvironmentAttributeSupplier<List<AmbientParticle>, ParticleCatalog> AMBIENT_PARTICLES = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.AMBIENT_PARTICLES, ParticleCatalog::delegateParticles);
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> MUSIC_VOLUME = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.MUSIC_VOLUME);

    // Audio
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> FIREFLY_BUSH_SOUNDS = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.FIREFLY_BUSH_SOUNDS);

    // Gameplay
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> SKY_LIGHT_LEVEL = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.SKY_LIGHT_LEVEL);
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> CAN_START_RAID = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.CAN_START_RAID);

    /** Spawns particles when placing water */
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> WATER_EVAPORATES = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.WATER_EVAPORATES);
    /** Appears to do nothing currently */
    public static final WrappedEnvironmentAttributeSupplier<net.minecraft.world.attribute.BedRule, BedRule> BED_RULE = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.BED_RULE, BedRule::delegateBedRule);
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> RESPAWN_ANCHOR_WORKS = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.RESPAWN_ANCHOR_WORKS);
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> NETHER_PORTAL_SPAWNS_PIGLINS = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.NETHER_PORTAL_SPAWNS_PIGLINS);
    /** Appears to do nothing currently */
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> FAST_LAVA = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.FAST_LAVA);
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> INCREASED_FIRE_BURNOUT = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.INCREASED_FIRE_BURNOUT);
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> TURTLE_EGG_HATCH_CHANCE = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.TURTLE_EGG_HATCH_CHANCE);
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> PIGLINS_ZOMBIFY = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.PIGLINS_ZOMBIFY);
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> SNOW_GOLEM_MELTS = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.SNOW_GOLEM_MELTS);
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> CREAKING_ACTIVE = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.CREAKING_ACTIVE);
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> SURFACE_SLIME_SPAWN_CHANCE = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.SURFACE_SLIME_SPAWN_CHANCE);
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Float, Float> CAT_WAKING_UP_GIFT_CHANCE = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.CAT_WAKING_UP_GIFT_CHANCE);
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> BEES_STAY_IN_HIVE = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.BEES_STAY_IN_HIVE);
    /** Appears to do nothing currently */
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> MONSTERS_BURN = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.MONSTERS_BURN);
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<Boolean, Boolean> CAN_PILLAGER_PATROL_SPAWN = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.CAN_PILLAGER_PATROL_SPAWN);
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<net.minecraft.world.entity.schedule.Activity, Activity> VILLAGER_ACTIVITY = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.VILLAGER_ACTIVITY, Activity::getDelegateActivity);
    /** Untested */
    public static final WrappedEnvironmentAttributeSupplier<net.minecraft.world.entity.schedule.Activity, Activity> BABY_VILLAGER_ACTIVITY = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.BABY_VILLAGER_ACTIVITY, Activity::getDelegateActivity);

    private WrappedEnvironmentAttributes() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * A collection of unmapped wrapped environment attributes as they appear in Minecraft's {@link EnvironmentAttributes}.
     */
    @AsOf("1.1.0")
    @ApiStatus.Experimental
    public static class Unmapped {
        // Audio
        /** Untested */
        public static final WrappedEnvironmentAttributeSupplier<BackgroundMusic, BackgroundMusic> BACKGROUND_MUSIC = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.BACKGROUND_MUSIC);
        /** Untested */
        public static final WrappedEnvironmentAttributeSupplier<AmbientSounds, AmbientSounds> AMBIENT_SOUNDS = WrappedEnvironmentAttribute.ofSupplier(EnvironmentAttributes.AMBIENT_SOUNDS);

        private Unmapped() {
            throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
        }
    }
}
