package me.outspending.biomesapi.wrapper.environment.particle.options;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleData;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleOptionsHandle;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleTypeHandle;
import org.jspecify.annotations.NullMarked;

/**
 * Particle data for spell particles.
 * 
 * @param color The color of the spell.
 * @param power The power of the spell.
 * @since 2.0.0
 * @version 2.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.0.0")
public record SpellParticle(int color, float power) implements ParticleData {

    public static final Codec<SpellParticle> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.INT.fieldOf("color").forGetter(SpellParticle::color),
        Codec.FLOAT.fieldOf("power").forGetter(SpellParticle::power)
    ).apply(instance, SpellParticle::new));

    @Override
    @AsOf("2.0.0")
    public ParticleOptionsHandle apply(ParticleTypeHandle particleType) {
        return ParticleOptionsFactory.WIRE.get().spell(particleType, color, power);
    }

    @AsOf("2.0.0")
    public static SpellParticle of(String hexColor, float power) {
        return new SpellParticle(ParticleData.parseHex(hexColor), power);
    }

    @AsOf("2.0.0")
    public static SpellParticle of(String hexColor) {
        return of(hexColor, 1.0f);
    }

    @Override
    @AsOf("2.4.0")
    public Codec<SpellParticle> codec() {
        return CODEC;
    }
}
