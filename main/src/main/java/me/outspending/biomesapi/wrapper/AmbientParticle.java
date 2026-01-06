package me.outspending.biomesapi.wrapper;

import me.outspending.biomesapi.annotations.AsOf;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the different types of ambient particles that can be used in the game.
 * This is an enumeration of all the possible ambient particles, each associated with a specific SimpleParticleType.
 * The @Getter annotation from the Lombok library is used to automatically generate a getter method for the 'particle' field.
 *
 * @version 1.0.1
 * @since 0.0.1
 * @author Outspending
 */
@AsOf("1.0.1")
public enum AmbientParticle {

    ANGRY_VILLAGER(ParticleTypes.ANGRY_VILLAGER),
    ASH(ParticleTypes.ASH),
    BUBBLE(ParticleTypes.BUBBLE),
    BUBBLE_COLUMN_UP(ParticleTypes.BUBBLE_COLUMN_UP),
    BUBBLE_POP(ParticleTypes.BUBBLE_POP),
    CAMPFIRE_COSY_SMOKE(ParticleTypes.CAMPFIRE_COSY_SMOKE),
    CAMPFIRE_SIGNAL_SMOKE(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE),
    CHERRY_LEAVES(ParticleTypes.CHERRY_LEAVES),
    CLOUD(ParticleTypes.CLOUD),
    COMPOSTER(ParticleTypes.COMPOSTER),
    COPPER_FIRE_FLAME(ParticleTypes.COPPER_FIRE_FLAME),
    CRIMSON_SPORE(ParticleTypes.CRIMSON_SPORE),
    CRIT(ParticleTypes.CRIT),
    CURRENT_DOWN(ParticleTypes.CURRENT_DOWN),
    DAMAGE_INDICATOR(ParticleTypes.DAMAGE_INDICATOR),
    DOLPHIN(ParticleTypes.DOLPHIN),
    DRIPPING_DRIPSTONE_LAVA(ParticleTypes.DRIPPING_DRIPSTONE_LAVA),
    DRIPPING_DRIPSTONE_WATER(ParticleTypes.DRIPPING_DRIPSTONE_WATER),
    DRIPPING_HONEY(ParticleTypes.DRIPPING_HONEY),
    DRIPPING_LAVA(ParticleTypes.DRIPPING_LAVA),
    DRIPPING_OBSIDIAN_TEAR(ParticleTypes.DRIPPING_OBSIDIAN_TEAR),
    DRIPPING_WATER(ParticleTypes.DRIPPING_WATER),
    DUST_PLUME(ParticleTypes.DUST_PLUME),
    EGG_CRACK(ParticleTypes.EGG_CRACK),
    ELDER_GUARDIAN(ParticleTypes.ELDER_GUARDIAN),
    ELECTRIC_SPARK(ParticleTypes.ELECTRIC_SPARK),
    ENCHANT(ParticleTypes.ENCHANT),
    ENCHANTED_HIT(ParticleTypes.ENCHANTED_HIT),
    END_ROD(ParticleTypes.END_ROD),
    EXPLOSION(ParticleTypes.EXPLOSION),
    EXPLOSION_EMITTER(ParticleTypes.EXPLOSION_EMITTER),
    FALLING_DRIPSTONE_LAVA(ParticleTypes.FALLING_DRIPSTONE_LAVA),
    FALLING_DRIPSTONE_WATER(ParticleTypes.FALLING_DRIPSTONE_WATER),
    FALLING_HONEY(ParticleTypes.FALLING_HONEY),
    FALLING_LAVA(ParticleTypes.FALLING_LAVA),
    FALLING_NECTAR(ParticleTypes.FALLING_NECTAR),
    FALLING_OBSIDIAN_TEAR(ParticleTypes.FALLING_OBSIDIAN_TEAR),
    FALLING_SPORE_BLOSSOM(ParticleTypes.FALLING_SPORE_BLOSSOM),
    FALLING_WATER(ParticleTypes.FALLING_WATER),
    FIREFLY(ParticleTypes.FIREFLY),
    FIREWORK(ParticleTypes.FIREWORK),
    FISHING(ParticleTypes.FISHING),
    FLAME(ParticleTypes.FLAME),
    GLOW(ParticleTypes.GLOW),
    GLOW_SQUID_INK(ParticleTypes.GLOW_SQUID_INK),
    GUST(ParticleTypes.GUST),
    GUST_EMITTER_LARGE(ParticleTypes.GUST_EMITTER_LARGE),
    GUST_EMITTER_SMALL(ParticleTypes.GUST_EMITTER_SMALL),
    HAPPY_VILLAGER(ParticleTypes.HAPPY_VILLAGER),
    HEART(ParticleTypes.HEART),
    INFESTED(ParticleTypes.INFESTED),
    ITEM_COBWEB(ParticleTypes.ITEM_COBWEB),
    ITEM_SLIME(ParticleTypes.ITEM_SLIME),
    ITEM_SNOWBALL(ParticleTypes.ITEM_SNOWBALL),
    LANDING_HONEY(ParticleTypes.LANDING_HONEY),
    LANDING_LAVA(ParticleTypes.LANDING_LAVA),
    LANDING_OBSIDIAN_TEAR(ParticleTypes.LANDING_OBSIDIAN_TEAR),
    LARGE_SMOKE(ParticleTypes.LARGE_SMOKE),
    LAVA(ParticleTypes.LAVA),
    MYCELIUM(ParticleTypes.MYCELIUM),
    NAUTILUS(ParticleTypes.NAUTILUS),
    NOTE(ParticleTypes.NOTE),
    OMINOUS_SPAWNING(ParticleTypes.OMINOUS_SPAWNING),
    PALE_OAK_LEAVES(ParticleTypes.PALE_OAK_LEAVES),
    POOF(ParticleTypes.POOF),
    PORTAL(ParticleTypes.PORTAL),
    RAID_OMEN(ParticleTypes.RAID_OMEN),
    RAIN(ParticleTypes.RAIN),
    REVERSE_PORTAL(ParticleTypes.REVERSE_PORTAL),
    SCRAPE(ParticleTypes.SCRAPE),
    SCULK_CHARGE_POP(ParticleTypes.SCULK_CHARGE_POP),
    SCULK_SOUL(ParticleTypes.SCULK_SOUL),
    SMALL_FLAME(ParticleTypes.SMALL_FLAME),
    SMALL_GUST(ParticleTypes.SMALL_GUST),
    SMOKE(ParticleTypes.SMOKE),
    SNEEZE(ParticleTypes.SNEEZE),
    SNOWFLAKE(ParticleTypes.SNOWFLAKE),
    SONIC_BOOM(ParticleTypes.SONIC_BOOM),
    SOUL(ParticleTypes.SOUL),
    SOUL_FIRE_FLAME(ParticleTypes.SOUL_FIRE_FLAME),
    SPIT(ParticleTypes.SPIT),
    SPLASH(ParticleTypes.SPLASH),
    SPORE_BLOSSOM_AIR(ParticleTypes.SPORE_BLOSSOM_AIR),
    SQUID_INK(ParticleTypes.SQUID_INK),
    SWEEP_ATTACK(ParticleTypes.SWEEP_ATTACK),
    TOTEM_OF_UNDYING(ParticleTypes.TOTEM_OF_UNDYING),
    TRIAL_OMEN(ParticleTypes.TRIAL_OMEN),
    TRIAL_SPAWNER_DETECTED_PLAYER(ParticleTypes.TRIAL_SPAWNER_DETECTED_PLAYER),
    TRIAL_SPAWNER_DETECTED_PLAYER_OMINOUS(ParticleTypes.TRIAL_SPAWNER_DETECTED_PLAYER_OMINOUS),
    UNDERWATER(ParticleTypes.UNDERWATER),
    VAULT_CONNECTION(ParticleTypes.VAULT_CONNECTION),
    WARPED_SPORE(ParticleTypes.WARPED_SPORE),
    WAX_ON(ParticleTypes.WAX_ON),
    WAX_OFF(ParticleTypes.WAX_OFF),
    WHITE_ASH(ParticleTypes.WHITE_ASH),
    WHITE_SMOKE(ParticleTypes.WHITE_SMOKE),
    WITCH(ParticleTypes.WITCH);

    private final ParticleType<?> particle;

    /**
     * Constructor for the AmbientParticle enum.
     * This constructor is used to associate a specific SimpleParticleType with each ambient particle.
     * The @AsOf annotation indicates the version when this constructor was introduced.
     *
     * @param particle the SimpleParticleType associated with the ambient particle
     * @version 0.0.1
     */
    @AsOf("0.0.1")
    AmbientParticle(SimpleParticleType particle) {
        this.particle = particle;
    }


    /**
     * This method returns the SimpleParticleType associated with the ambient particle.
     * The @AsOf annotation indicates the version when this method was introduced.
     *
     * @return the SimpleParticleType associated with the ambient particle
     * @version 0.0.1
     */
    @AsOf("0.0.1")
    public <T extends ParticleOptions> ParticleType<@NotNull T> getParticle() {
        return (ParticleType<T>) particle;
    }

    /**
     * Get the SimpleParticleType of this AmbientParticle.
     * @return The SimpleParticleType of this AmbientParticle.
     */
    @AsOf("0.0.6")
    public SimpleParticleType getSimpleParticle() {
        if (!(particle instanceof SimpleParticleType)) {
            throw new IllegalStateException("ParticleType is not a SimpleParticleType: " + particle);
        }
        return (SimpleParticleType) particle;
    }

}
