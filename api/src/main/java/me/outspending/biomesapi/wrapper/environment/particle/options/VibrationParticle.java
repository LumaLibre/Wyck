package me.outspending.biomesapi.wrapper.environment.particle.options;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleData;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleOptionsHandle;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleTypeHandle;
import org.bukkit.Vibration;
import org.jetbrains.annotations.NotNull;

@AsOf("2.0.0")
public record VibrationParticle(Vibration.Destination destination, int arrivalInTicks) implements ParticleData<VibrationParticle> {

    @Override
    public @NotNull ParticleOptionsHandle apply(@NotNull ParticleTypeHandle particleType) {
        return ParticleOptionsFactory.WIRE.get().vibration(destination, arrivalInTicks);
    }

    public static VibrationParticle of(@NotNull Vibration.Destination destination, int arrivalInTicks) {
        return new VibrationParticle(destination, arrivalInTicks);
    }
}
