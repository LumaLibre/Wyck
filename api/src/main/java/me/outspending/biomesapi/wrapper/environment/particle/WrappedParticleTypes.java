package me.outspending.biomesapi.wrapper.environment.particle;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.particle.options.BlockParticle;
import me.outspending.biomesapi.wrapper.environment.particle.options.ColorParticle;
import me.outspending.biomesapi.wrapper.environment.particle.options.DustParticle;
import me.outspending.biomesapi.wrapper.environment.particle.options.DustTransitionParticle;
import me.outspending.biomesapi.wrapper.environment.particle.options.ItemParticle;
import me.outspending.biomesapi.wrapper.environment.particle.options.ParticleOptionsFactory;
import me.outspending.biomesapi.wrapper.environment.particle.options.PowerParticle;
import me.outspending.biomesapi.wrapper.environment.particle.options.SculkChargeParticle;
import me.outspending.biomesapi.wrapper.environment.particle.options.SpellParticle;
import me.outspending.biomesapi.wrapper.environment.particle.options.TrailParticle;
import me.outspending.biomesapi.wrapper.environment.particle.options.VibrationParticle;
import org.jetbrains.annotations.Nullable;

/**
 * An enum that wraps around Minecraft's ParticleTypes, providing additional metadata and functionality.
 * Each enum constant corresponds to a specific particle type in Minecraft.
 *
 * @see WrappedAmbientParticle
 * @see ParticleCatalog
 * @see ParticleData
 * @since 2.0.0
 * @author Jsinco
 */
@AsOf("2.0.0")
public enum WrappedParticleTypes {

    ANGRY_VILLAGER("angry_villager"),
    BLOCK("block", BlockParticle.class),
    BLOCK_MARKER("block_marker", BlockParticle.class),
    BUBBLE("bubble"),
    CLOUD("cloud"),
    COPPER_FIRE_FLAME("copper_fire_flame"),
    CRIT("crit"),
    DAMAGE_INDICATOR("damage_indicator"),
    DRAGON_BREATH("dragon_breath", PowerParticle.class),
    DRIPPING_LAVA("dripping_lava"),
    FALLING_LAVA("falling_lava"),
    LANDING_LAVA("landing_lava"),
    DRIPPING_WATER("dripping_water"),
    FALLING_WATER("falling_water"),
    DUST("dust", DustParticle.class),
    DUST_COLOR_TRANSITION("dust_color_transition", DustTransitionParticle.class),
    EFFECT("effect", SpellParticle.class),
    ELDER_GUARDIAN("elder_guardian"),
    ENCHANTED_HIT("enchanted_hit"),
    ENCHANT("enchant"),
    END_ROD("end_rod"),
    ENTITY_EFFECT("entity_effect", ColorParticle.class),
    EXPLOSION_EMITTER("explosion_emitter"),
    EXPLOSION("explosion"),
    GUST("gust"),
    SMALL_GUST("small_gust"),
    GUST_EMITTER_LARGE("gust_emitter_large"),
    GUST_EMITTER_SMALL("gust_emitter_small"),
    SONIC_BOOM("sonic_boom"),
    FALLING_DUST("falling_dust", BlockParticle.class),
    FIREWORK("firework"),
    FISHING("fishing"),
    FLAME("flame"),
    INFESTED("infested"),
    CHERRY_LEAVES("cherry_leaves"),
    PALE_OAK_LEAVES("pale_oak_leaves"),
    TINTED_LEAVES("tinted_leaves", ColorParticle.class),
    SCULK_SOUL("sculk_soul"),
    SCULK_CHARGE("sculk_charge", SculkChargeParticle.class),
    SCULK_CHARGE_POP("sculk_charge_pop"),
    SOUL_FIRE_FLAME("soul_fire_flame"),
    SOUL("soul"),
    FLASH("flash", ColorParticle.class),
    HAPPY_VILLAGER("happy_villager"),
    COMPOSTER("composter"),
    HEART("heart"),
    INSTANT_EFFECT("instant_effect", SpellParticle.class),
    ITEM("item", ItemParticle.class),
    VIBRATION("vibration", VibrationParticle.class),
    TRAIL("trail", TrailParticle.class),
    ITEM_SLIME("item_slime"),
    ITEM_COBWEB("item_cobweb"),
    ITEM_SNOWBALL("item_snowball"),
    LARGE_SMOKE("large_smoke"),
    LAVA("lava"),
    MYCELIUM("mycelium"),
    NOTE("note"),
    POOF("poof"),
    PORTAL("portal"),
    RAIN("rain"),
    SMOKE("smoke"),
    WHITE_SMOKE("white_smoke"),
    SNEEZE("sneeze"),
    SPIT("spit"),
    SQUID_INK("squid_ink"),
    SWEEP_ATTACK("sweep_attack"),
    TOTEM_OF_UNDYING("totem_of_undying"),
    UNDERWATER("underwater"),
    SPLASH("splash"),
    WITCH("witch"),
    BUBBLE_POP("bubble_pop"),
    CURRENT_DOWN("current_down"),
    BUBBLE_COLUMN_UP("bubble_column_up"),
    NAUTILUS("nautilus"),
    DOLPHIN("dolphin"),
    CAMPFIRE_COSY_SMOKE("campfire_cosy_smoke"),
    CAMPFIRE_SIGNAL_SMOKE("campfire_signal_smoke"),
    DRIPPING_HONEY("dripping_honey"),
    FALLING_HONEY("falling_honey"),
    LANDING_HONEY("landing_honey"),
    FALLING_NECTAR("falling_nectar"),
    FALLING_SPORE_BLOSSOM("falling_spore_blossom"),
    ASH("ash"),
    CRIMSON_SPORE("crimson_spore"),
    WARPED_SPORE("warped_spore"),
    SPORE_BLOSSOM_AIR("spore_blossom_air"),
    DRIPPING_OBSIDIAN_TEAR("dripping_obsidian_tear"),
    FALLING_OBSIDIAN_TEAR("falling_obsidian_tear"),
    LANDING_OBSIDIAN_TEAR("landing_obsidian_tear"),
    REVERSE_PORTAL("reverse_portal"),
    WHITE_ASH("white_ash"),
    SMALL_FLAME("small_flame"),
    SNOWFLAKE("snowflake"),
    DRIPPING_DRIPSTONE_LAVA("dripping_dripstone_lava"),
    FALLING_DRIPSTONE_LAVA("falling_dripstone_lava"),
    DRIPPING_DRIPSTONE_WATER("dripping_dripstone_water"),
    FALLING_DRIPSTONE_WATER("falling_dripstone_water"),
    GLOW_SQUID_INK("glow_squid_ink"),
    GLOW("glow"),
    WAX_ON("wax_on"),
    WAX_OFF("wax_off"),
    ELECTRIC_SPARK("electric_spark"),
    SCRAPE("scrape"),
    SHRIEK("shriek"),
    EGG_CRACK("egg_crack"),
    DUST_PLUME("dust_plume"),
    TRIAL_SPAWNER_DETECTED_PLAYER("trial_spawner_detected_player"),
    TRIAL_SPAWNER_DETECTED_PLAYER_OMINOUS("trial_spawner_detected_player_ominous"),
    VAULT_CONNECTION("vault_connection"),
    DUST_PILLAR("dust_pillar", BlockParticle.class),
    OMINOUS_SPAWNING("ominous_spawning"),
    RAID_OMEN("raid_omen"),
    TRIAL_OMEN("trial_omen"),
    BLOCK_CRUMBLE("block_crumble", BlockParticle.class),
    FIREFLY("firefly");

    private final String key;
    private final @Nullable Class<? extends ParticleData<?>> particleDataClass;
    private volatile ParticleTypeHandle<?> cachedHandle;

    WrappedParticleTypes(String key) {
        this(key, null);
    }

    WrappedParticleTypes(String key, @Nullable Class<? extends ParticleData<?>> particleDataClass) {
        this.key = key;
        this.particleDataClass = particleDataClass;
    }

    /**
     * The namespaced name of this particle (without the "minecraft:" prefix).
     */
    @AsOf("1.1.0")
    public String getKey() {
        return key;
    }

    /**
     * Gets a version-agnostic handle to this particle type, resolved on first access
     * via the registered factory and cached.
     */
    @AsOf("1.1.0")
    public <T> ParticleTypeHandle<T> getParticleType() {
        ParticleTypeHandle<?> cached = cachedHandle;
        if (cached == null) {
            cached = ParticleOptionsFactory.WIRE.get().typeByKey(key);
            cachedHandle = cached;
        }
        @SuppressWarnings("unchecked")
        ParticleTypeHandle<T> typed = (ParticleTypeHandle<T>) cached;
        return typed;
    }

    @AsOf("1.1.0")
    public @Nullable Class<? extends ParticleData<?>> getParticleDataClass() {
        return particleDataClass;
    }

    @AsOf("1.1.0")
    public boolean isSimple() {
        return particleDataClass == null;
    }


    /**
     * Creates a WrappedAmbientParticle with the specified probability and optional particle data.
     * @param probability The probability of the particle.
     * @param data The optional particle data.
     * @return A WrappedAmbientParticle with the specified probability and optional particle data.
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    public WrappedAmbientParticle<?> create(float probability, @Nullable ParticleData<?> data) {
        return WrappedAmbientParticle.of(this, probability, data);
    }

    /**
     * Creates a simple WrappedAmbientParticle with the specified probability and no particle data.
     * @param probability The probability of the particle.
     * @return A simple WrappedAmbientParticle with the specified probability and no particle data.
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    public WrappedAmbientParticle<?> create(float probability) {
        return WrappedAmbientParticle.of(this, probability);
    }

}