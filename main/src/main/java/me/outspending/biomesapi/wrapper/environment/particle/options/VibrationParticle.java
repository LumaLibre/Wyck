package me.outspending.biomesapi.wrapper.environment.particle.options;

import me.outspending.biomesapi.api.annotations.AsOf;
import me.outspending.biomesapi.api.wrapper.environment.particle.ParticleData;
import me.outspending.biomesapi.api.wrapper.environment.particle.ParticleOptionsHandle;
import me.outspending.biomesapi.api.wrapper.environment.particle.ParticleTypeHandle;
import me.outspending.biomesapi.api.wrapper.environment.particle.options.ParticleOptionsFactory;
import org.bukkit.Vibration;
import org.jetbrains.annotations.NotNull;

@AsOf("1.1.0")
public record VibrationParticle(Vibration.Destination destination, int arrivalInTicks) implements ParticleData<VibrationParticle> {

    @Override
    public @NotNull ParticleOptionsHandle apply(@NotNull ParticleTypeHandle<VibrationParticle> particleType) {
        return ParticleOptionsFactory.OPTIONS.get().vibration(destination, arrivalInTicks);
    }

    public static VibrationParticle of(@NotNull Vibration.Destination destination, int arrivalInTicks) {
        return new VibrationParticle(destination, arrivalInTicks);
    }
}
