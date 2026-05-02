package me.outspending.biomesapi.wrapper.environment.particle.options;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleData;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleOptionsHandle;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleTypeHandle;
import org.jetbrains.annotations.NotNull;

@AsOf("2.0.0")
public record SculkChargeParticle(float roll) implements ParticleData<SculkChargeParticle> {

    @Override
    public @NotNull ParticleOptionsHandle apply(@NotNull ParticleTypeHandle<SculkChargeParticle> particleType) {
        return ParticleOptionsFactory.WIRE.get().sculkCharge(roll);
    }

    public static SculkChargeParticle of(float roll) {
        return new SculkChargeParticle(roll);
    }
}
