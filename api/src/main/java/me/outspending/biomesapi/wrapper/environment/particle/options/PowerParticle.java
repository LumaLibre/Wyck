package me.outspending.biomesapi.wrapper.environment.particle.options;

import com.mojang.serialization.Codec;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleData;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleOptionsHandle;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleTypeHandle;
import org.jspecify.annotations.NullMarked;

/**
 * Particle data for power particles.
 *
 * @param power The power of the particle.
 * @since 2.0.0
 * @version 2.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.0.0")
public record PowerParticle(float power) implements ParticleData {

    public static final Codec<PowerParticle> CODEC = Codec.FLOAT.fieldOf("power").xmap(PowerParticle::new, PowerParticle::power).codec();

    @Override
    @AsOf("2.0.0")
    public ParticleOptionsHandle apply(ParticleTypeHandle particleType) {
        return ParticleOptionsFactory.WIRE.get().power(particleType, power);
    }

    @AsOf("2.0.0")
    public static PowerParticle of(float power) {
        return new PowerParticle(power);
    }

    @Override
    @AsOf("2.4.0")
    public Codec<PowerParticle> codec() {
        return CODEC;
    }
}
