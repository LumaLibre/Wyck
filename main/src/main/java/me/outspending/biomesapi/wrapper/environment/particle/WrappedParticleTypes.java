package me.outspending.biomesapi.wrapper.environment.particle;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.particle.options.BlockParticle;
import me.outspending.biomesapi.wrapper.environment.particle.options.ColorParticle;
import me.outspending.biomesapi.wrapper.environment.particle.options.DustParticle;
import me.outspending.biomesapi.wrapper.environment.particle.options.DustTransitionParticle;
import me.outspending.biomesapi.wrapper.environment.particle.options.ItemParticle;
import me.outspending.biomesapi.wrapper.environment.particle.options.PowerParticle;
import me.outspending.biomesapi.wrapper.environment.particle.options.SculkChargeParticle;
import me.outspending.biomesapi.wrapper.environment.particle.options.SpellParticle;
import me.outspending.biomesapi.wrapper.environment.particle.options.TrailParticle;
import me.outspending.biomesapi.wrapper.environment.particle.options.VibrationParticle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An enum that wraps around Minecraft's ParticleTypes, providing additional metadata and functionality.
 * Each enum constant corresponds to a specific particle type in Minecraft.
 *
 * @see WrappedAmbientParticle
 * @see ParticleCatalog
 * @see ParticleData
 * @since 1.1.0
 * @author Jsinco
 */
@AsOf("1.1.0")
public enum WrappedParticleTypes {

    ANGRY_VILLAGER(ParticleTypes.ANGRY_VILLAGER),
    BLOCK(ParticleTypes.BLOCK, BlockParticle.class),
    BLOCK_MARKER(ParticleTypes.BLOCK_MARKER, BlockParticle.class),
    BUBBLE(ParticleTypes.BUBBLE),
    CLOUD(ParticleTypes.CLOUD),
    COPPER_FIRE_FLAME(ParticleTypes.COPPER_FIRE_FLAME),
    CRIT(ParticleTypes.CRIT),
    DAMAGE_INDICATOR(ParticleTypes.DAMAGE_INDICATOR),
    DRAGON_BREATH(ParticleTypes.DRAGON_BREATH, PowerParticle.class),
    DRIPPING_LAVA(ParticleTypes.DRIPPING_LAVA),
    FALLING_LAVA(ParticleTypes.FALLING_LAVA),
    LANDING_LAVA(ParticleTypes.LANDING_LAVA),
    DRIPPING_WATER(ParticleTypes.DRIPPING_WATER),
    FALLING_WATER(ParticleTypes.FALLING_WATER),
    DUST(ParticleTypes.DUST, DustParticle.class),
    DUST_COLOR_TRANSITION(ParticleTypes.DUST_COLOR_TRANSITION, DustTransitionParticle.class),
    EFFECT(ParticleTypes.EFFECT, SpellParticle.class),
    ELDER_GUARDIAN(ParticleTypes.ELDER_GUARDIAN),
    ENCHANTED_HIT(ParticleTypes.ENCHANTED_HIT),
    ENCHANT(ParticleTypes.ENCHANT),
    END_ROD(ParticleTypes.END_ROD),
    ENTITY_EFFECT(ParticleTypes.ENTITY_EFFECT, ColorParticle.class),
    EXPLOSION_EMITTER(ParticleTypes.EXPLOSION_EMITTER),
    EXPLOSION(ParticleTypes.EXPLOSION),
    GUST(ParticleTypes.GUST),
    SMALL_GUST(ParticleTypes.SMALL_GUST),
    GUST_EMITTER_LARGE(ParticleTypes.GUST_EMITTER_LARGE),
    GUST_EMITTER_SMALL(ParticleTypes.GUST_EMITTER_SMALL),
    SONIC_BOOM(ParticleTypes.SONIC_BOOM),
    FALLING_DUST(ParticleTypes.FALLING_DUST, BlockParticle.class),
    FIREWORK(ParticleTypes.FIREWORK),
    FISHING(ParticleTypes.FISHING),
    FLAME(ParticleTypes.FLAME),
    INFESTED(ParticleTypes.INFESTED),
    CHERRY_LEAVES(ParticleTypes.CHERRY_LEAVES),
    PALE_OAK_LEAVES(ParticleTypes.PALE_OAK_LEAVES),
    TINTED_LEAVES(ParticleTypes.TINTED_LEAVES, ColorParticle.class),
    SCULK_SOUL(ParticleTypes.SCULK_SOUL),
    SCULK_CHARGE(ParticleTypes.SCULK_CHARGE, SculkChargeParticle.class),
    SCULK_CHARGE_POP(ParticleTypes.SCULK_CHARGE_POP),
    SOUL_FIRE_FLAME(ParticleTypes.SOUL_FIRE_FLAME),
    SOUL(ParticleTypes.SOUL),
    FLASH(ParticleTypes.FLASH, ColorParticle.class),
    HAPPY_VILLAGER(ParticleTypes.HAPPY_VILLAGER),
    COMPOSTER(ParticleTypes.COMPOSTER),
    HEART(ParticleTypes.HEART),
    INSTANT_EFFECT(ParticleTypes.INSTANT_EFFECT, SpellParticle.class),
    ITEM(ParticleTypes.ITEM, ItemParticle.class),
    VIBRATION(ParticleTypes.VIBRATION, VibrationParticle.class),
    TRAIL(ParticleTypes.TRAIL, TrailParticle.class),
    ITEM_SLIME(ParticleTypes.ITEM_SLIME),
    ITEM_COBWEB(ParticleTypes.ITEM_COBWEB),
    ITEM_SNOWBALL(ParticleTypes.ITEM_SNOWBALL),
    LARGE_SMOKE(ParticleTypes.LARGE_SMOKE),
    LAVA(ParticleTypes.LAVA),
    MYCELIUM(ParticleTypes.MYCELIUM),
    NOTE(ParticleTypes.NOTE),
    POOF(ParticleTypes.POOF),
    PORTAL(ParticleTypes.PORTAL),
    RAIN(ParticleTypes.RAIN),
    SMOKE(ParticleTypes.SMOKE),
    WHITE_SMOKE(ParticleTypes.WHITE_SMOKE),
    SNEEZE(ParticleTypes.SNEEZE),
    SPIT(ParticleTypes.SPIT),
    SQUID_INK(ParticleTypes.SQUID_INK),
    SWEEP_ATTACK(ParticleTypes.SWEEP_ATTACK),
    TOTEM_OF_UNDYING(ParticleTypes.TOTEM_OF_UNDYING),
    UNDERWATER(ParticleTypes.UNDERWATER),
    SPLASH(ParticleTypes.SPLASH),
    WITCH(ParticleTypes.WITCH),
    BUBBLE_POP(ParticleTypes.BUBBLE_POP),
    CURRENT_DOWN(ParticleTypes.CURRENT_DOWN),
    BUBBLE_COLUMN_UP(ParticleTypes.BUBBLE_COLUMN_UP),
    NAUTILUS(ParticleTypes.NAUTILUS),
    DOLPHIN(ParticleTypes.DOLPHIN),
    CAMPFIRE_COSY_SMOKE(ParticleTypes.CAMPFIRE_COSY_SMOKE),
    CAMPFIRE_SIGNAL_SMOKE(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE),
    DRIPPING_HONEY(ParticleTypes.DRIPPING_HONEY),
    FALLING_HONEY(ParticleTypes.FALLING_HONEY),
    LANDING_HONEY(ParticleTypes.LANDING_HONEY),
    FALLING_NECTAR(ParticleTypes.FALLING_NECTAR),
    FALLING_SPORE_BLOSSOM(ParticleTypes.FALLING_SPORE_BLOSSOM),
    ASH(ParticleTypes.ASH),
    CRIMSON_SPORE(ParticleTypes.CRIMSON_SPORE),
    WARPED_SPORE(ParticleTypes.WARPED_SPORE),
    SPORE_BLOSSOM_AIR(ParticleTypes.SPORE_BLOSSOM_AIR),
    DRIPPING_OBSIDIAN_TEAR(ParticleTypes.DRIPPING_OBSIDIAN_TEAR),
    FALLING_OBSIDIAN_TEAR(ParticleTypes.FALLING_OBSIDIAN_TEAR),
    LANDING_OBSIDIAN_TEAR(ParticleTypes.LANDING_OBSIDIAN_TEAR),
    REVERSE_PORTAL(ParticleTypes.REVERSE_PORTAL),
    WHITE_ASH(ParticleTypes.WHITE_ASH),
    SMALL_FLAME(ParticleTypes.SMALL_FLAME),
    SNOWFLAKE(ParticleTypes.SNOWFLAKE),
    DRIPPING_DRIPSTONE_LAVA(ParticleTypes.DRIPPING_DRIPSTONE_LAVA),
    FALLING_DRIPSTONE_LAVA(ParticleTypes.FALLING_DRIPSTONE_LAVA),
    DRIPPING_DRIPSTONE_WATER(ParticleTypes.DRIPPING_DRIPSTONE_WATER),
    FALLING_DRIPSTONE_WATER(ParticleTypes.FALLING_DRIPSTONE_WATER),
    GLOW_SQUID_INK(ParticleTypes.GLOW_SQUID_INK),
    GLOW(ParticleTypes.GLOW),
    WAX_ON(ParticleTypes.WAX_ON),
    WAX_OFF(ParticleTypes.WAX_OFF),
    ELECTRIC_SPARK(ParticleTypes.ELECTRIC_SPARK),
    SCRAPE(ParticleTypes.SCRAPE),
    SHRIEK(ParticleTypes.SHRIEK),
    EGG_CRACK(ParticleTypes.EGG_CRACK),
    DUST_PLUME(ParticleTypes.DUST_PLUME),
    TRIAL_SPAWNER_DETECTED_PLAYER(ParticleTypes.TRIAL_SPAWNER_DETECTED_PLAYER),
    TRIAL_SPAWNER_DETECTED_PLAYER_OMINOUS(ParticleTypes.TRIAL_SPAWNER_DETECTED_PLAYER_OMINOUS),
    VAULT_CONNECTION(ParticleTypes.VAULT_CONNECTION),
    DUST_PILLAR(ParticleTypes.DUST_PILLAR, BlockParticle.class),
    OMINOUS_SPAWNING(ParticleTypes.OMINOUS_SPAWNING),
    RAID_OMEN(ParticleTypes.RAID_OMEN),
    TRIAL_OMEN(ParticleTypes.TRIAL_OMEN),
    BLOCK_CRUMBLE(ParticleTypes.BLOCK_CRUMBLE, BlockParticle.class),
    FIREFLY(ParticleTypes.FIREFLY);

    private final ParticleType<?> particleType;
    private final @Nullable Class<? extends ParticleData<?>> particleDataClass;

    @AsOf("1.1.0")
    WrappedParticleTypes(ParticleType<?> particleType) {
        this.particleType = particleType;
        this.particleDataClass = null;
    }

    @AsOf("1.1.0")
    WrappedParticleTypes(ParticleType<?> particleType, Class<? extends ParticleData<?>> particleDataClass) {
        this.particleType = particleType;
        this.particleDataClass = particleDataClass;
    }

    /**
     * Gets the raw Minecraft ParticleType.
     * @return The Minecraft ParticleType.
     */
    @AsOf("1.1.0")
    public ParticleType<?> getParticleTypeRaw() {
        return particleType;
    }

    /**
     * Gets the Minecraft ParticleType with the appropriate generic type.
     * @return The Minecraft ParticleType.
     * @param <T> The type of ParticleOptions.
     */
    @AsOf("1.1.0")
    public <T extends ParticleOptions> ParticleType<@NotNull T> getParticleType() {
        return (ParticleType<T>) particleType;
    }

    /**
     * Gets the ParticleData class associated with this particle type, or null if it is a simple particle.
     * @return The ParticleData class, or null.
     */
    @AsOf("1.1.0")
    public @Nullable Class<? extends ParticleData<?>> getParticleDataClass() {
        return particleDataClass;
    }

    /**
     * Checks if this particle type is simple (i.e., does not require additional data).
     * @return True if the particle type is simple, false otherwise.
     */
    @AsOf("1.1.0")
    public boolean isSimple() {
        return particleDataClass == null;
    }

}
