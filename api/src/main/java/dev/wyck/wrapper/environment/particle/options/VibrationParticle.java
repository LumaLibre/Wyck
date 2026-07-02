package dev.wyck.wrapper.environment.particle.options;

import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.environment.particle.ParticleData;
import dev.wyck.wrapper.environment.particle.ParticleOptionsHandle;
import dev.wyck.wrapper.environment.particle.ParticleTypeHandle;
import org.bukkit.Vibration;
import org.jspecify.annotations.NullMarked;

/**
 * Particle data for vibration particles.
 * 
 * @param destination The destination of the vibration.
 * @param arrivalInTicks The time in ticks until the vibration arrives.
 * @since 2.0.0
 * @version 2.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.0.0")
public record VibrationParticle(Vibration.Destination destination, int arrivalInTicks) implements ParticleData {

    @Override
    @AsOf("2.0.0")
    public ParticleOptionsHandle apply(ParticleTypeHandle particleType) {
        return ParticleOptionsFactory.WIRE.get().vibration(destination, arrivalInTicks);
    }

    @AsOf("2.0.0")
    public static VibrationParticle of(Vibration.Destination destination, int arrivalInTicks) {
        return new VibrationParticle(destination, arrivalInTicks);
    }
}
