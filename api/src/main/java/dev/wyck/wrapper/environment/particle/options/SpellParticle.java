package dev.wyck.wrapper.environment.particle.options;

import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.environment.particle.ParticleData;
import dev.wyck.wrapper.environment.particle.ParticleOptions;
import dev.wyck.wrapper.environment.particle.ParticleOptionsFactory;
import dev.wyck.wrapper.environment.particle.ParticleType;
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

    @Override
    @AsOf("2.0.0")
    public ParticleOptions apply(ParticleType particleType) {
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
}
