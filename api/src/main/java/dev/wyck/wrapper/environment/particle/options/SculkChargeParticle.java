package dev.wyck.wrapper.environment.particle.options;

import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.environment.particle.ParticleData;
import dev.wyck.wrapper.environment.particle.ParticleOptions;
import dev.wyck.wrapper.environment.particle.ParticleOptionsFactory;
import dev.wyck.wrapper.environment.particle.ParticleType;
import org.jspecify.annotations.NullMarked;

/**
 * Particle data for sculk charge particles.
 * 
 * @param roll The roll of the sculk charge.
 * @since 2.0.0
 * @version 2.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.0.0")
public record SculkChargeParticle(float roll) implements ParticleData {

    @Override
    @AsOf("2.0.0")
    public ParticleOptions apply(ParticleType particleType) {
        return ParticleOptionsFactory.WIRE.get().sculkCharge(roll);
    }

    @AsOf("2.0.0")
    public static SculkChargeParticle of(float roll) {
        return new SculkChargeParticle(roll);
    }
}
