package me.outspending.biomesapi.wrapper.environment.particle.options;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleData;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SculkChargeParticleOptions;
import org.jetbrains.annotations.NotNull;

@AsOf("1.1.0")
public record SculkChargeParticle(float roll) implements ParticleData<SculkChargeParticleOptions> {
    @Override
    public @NotNull ParticleOptions apply(@NotNull ParticleType<@NotNull SculkChargeParticleOptions> particleType) {
        return new SculkChargeParticleOptions(roll);
    }

    public static SculkChargeParticle of(float roll) {
        return new SculkChargeParticle(roll);
    }
}
