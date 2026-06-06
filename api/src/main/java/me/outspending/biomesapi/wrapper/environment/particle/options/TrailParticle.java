package me.outspending.biomesapi.wrapper.environment.particle.options;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleData;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleOptionsHandle;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleTypeHandle;
import org.bukkit.Location;
import org.jspecify.annotations.NullMarked;

/**
 * Particle data for trail particles.
 *
 * @param target The target location of the trail.
 * @param color The color of the trail.
 * @param duration The duration of the trail.
 * @since 2.0.0
 * @version 2.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.0.0")
public record TrailParticle(Location target, int color, int duration) implements ParticleData {

    @Override
    @AsOf("2.0.0")
    public ParticleOptionsHandle apply(ParticleTypeHandle particleType) {
        return ParticleOptionsFactory.WIRE.get().trail(target, color, duration);
    }

    @AsOf("2.0.0")
    public static TrailParticle of(Location target, String hexColor, int duration) {
        return new TrailParticle(target, ParticleData.parseHex(hexColor), duration);
    }
}