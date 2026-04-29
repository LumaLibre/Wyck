package me.outspending.biomesapi.wrapper.environment.particle.options;

import me.outspending.biomesapi.api.annotations.AsOf;
import me.outspending.biomesapi.api.wrapper.environment.particle.ParticleData;
import me.outspending.biomesapi.api.wrapper.environment.particle.ParticleOptionsHandle;
import me.outspending.biomesapi.api.wrapper.environment.particle.ParticleTypeHandle;
import me.outspending.biomesapi.api.wrapper.environment.particle.options.ParticleOptionsFactory;
import org.jetbrains.annotations.NotNull;

@AsOf("2.0.0")
public record PowerParticle(float power) implements ParticleData<PowerParticle> {

    @Override
    public @NotNull ParticleOptionsHandle apply(@NotNull ParticleTypeHandle<PowerParticle> particleType) {
        return ParticleOptionsFactory.OPTIONS.get().power(particleType, power);
    }

    public static PowerParticle of(float power) {
        return new PowerParticle(power);
    }
}
