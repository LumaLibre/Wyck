package me.outspending.biomesapi.wrapper.environment.particle.options;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleData;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleOptionsHandle;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleTypeHandle;
import org.bukkit.Vibration;
import org.jspecify.annotations.NullMarked;

import static me.outspending.biomesapi.serialization.Codecs.DESTINATION_CODEC;

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

    public static final Codec<VibrationParticle> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        DESTINATION_CODEC.fieldOf("destination").forGetter(VibrationParticle::destination),
        Codec.INT.fieldOf("arrivalInTicks").forGetter(VibrationParticle::arrivalInTicks)
    ).apply(instance, VibrationParticle::new));

    @Override
    @AsOf("2.0.0")
    public ParticleOptionsHandle apply(ParticleTypeHandle particleType) {
        return ParticleOptionsFactory.WIRE.get().vibration(destination, arrivalInTicks);
    }

    @AsOf("2.0.0")
    public static VibrationParticle of(Vibration.Destination destination, int arrivalInTicks) {
        return new VibrationParticle(destination, arrivalInTicks);
    }

    @Override
    @AsOf("2.4.0")
    public Codec<VibrationParticle> codec() {
        return CODEC;
    }
}
