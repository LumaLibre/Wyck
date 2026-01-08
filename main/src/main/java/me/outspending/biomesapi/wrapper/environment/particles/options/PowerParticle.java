package me.outspending.biomesapi.wrapper.environment.particles.options;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.particles.ParticleData;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.PowerParticleOption;
import org.jetbrains.annotations.NotNull;

@AsOf("1.1.0")
public record PowerParticle(float power) implements ParticleData<PowerParticleOption> {
    @Override
    public @NotNull ParticleOptions apply(@NotNull ParticleType<@NotNull PowerParticleOption> particleType) {
        return PowerParticleOption.create(particleType, power);
    }

    public static PowerParticle of(float power) {
        return new PowerParticle(power);
    }
}
