package dev.wyck.wrapper.environment.particle.options;

import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.environment.particle.ParticleData;
import dev.wyck.wrapper.environment.particle.ParticleOptions;
import dev.wyck.wrapper.environment.particle.ParticleOptionsFactory;
import dev.wyck.wrapper.environment.particle.ParticleType;
import org.jspecify.annotations.NullMarked;

/**
 * Particle data for particles that require a color.
 *
 * @param color The color of the particle.
 * @since 2.0.0
 * @version 2.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.0.0")
public record ColorParticle(int color) implements ParticleData {

    @Override
    @AsOf("2.0.0")
    public ParticleOptions apply(ParticleType particleType) {
        return ParticleOptionsFactory.WIRE.get().color(particleType, color);
    }

    @AsOf("2.0.0")
    public static ColorParticle of(String hexColor) {
        return new ColorParticle(ParticleData.parseHex(hexColor));
    }
}
