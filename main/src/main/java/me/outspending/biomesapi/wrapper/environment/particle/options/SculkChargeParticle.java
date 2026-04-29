package me.outspending.biomesapi.wrapper.environment.particle.options;

import me.outspending.biomesapi.api.annotations.AsOf;
import me.outspending.biomesapi.api.wrapper.environment.particle.ParticleData;
import me.outspending.biomesapi.api.wrapper.environment.particle.ParticleOptionsHandle;
import me.outspending.biomesapi.api.wrapper.environment.particle.ParticleTypeHandle;
import me.outspending.biomesapi.api.wrapper.environment.particle.options.ParticleOptionsFactory;
import org.jetbrains.annotations.NotNull;

@AsOf("2.0.0")
public record SculkChargeParticle(float roll) implements ParticleData<SculkChargeParticle> {

    @Override
    public @NotNull ParticleOptionsHandle apply(@NotNull ParticleTypeHandle<SculkChargeParticle> particleType) {
        return ParticleOptionsFactory.OPTIONS.get().sculkCharge(roll);
    }

    public static SculkChargeParticle of(float roll) {
        return new SculkChargeParticle(roll);
    }
}
