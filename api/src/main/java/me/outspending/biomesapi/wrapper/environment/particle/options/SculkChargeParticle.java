package me.outspending.biomesapi.wrapper.environment.particle.options;

import com.mojang.serialization.Codec;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleData;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleOptionsHandle;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleTypeHandle;
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

    public static final Codec<SculkChargeParticle> CODEC = Codec.FLOAT.fieldOf("roll").xmap(SculkChargeParticle::of, SculkChargeParticle::roll).codec();

    @Override
    @AsOf("2.0.0")
    public ParticleOptionsHandle apply(ParticleTypeHandle particleType) {
        return ParticleOptionsFactory.WIRE.get().sculkCharge(roll);
    }

    @AsOf("2.0.0")
    public static SculkChargeParticle of(float roll) {
        return new SculkChargeParticle(roll);
    }

    @Override
    @AsOf("2.4.0")
    public Codec<SculkChargeParticle> codec() {
        return CODEC;
    }
}
