package dev.wyck.wrapper.environment.particle.options;

import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.environment.particle.ParticleData;
import dev.wyck.wrapper.environment.particle.ParticleOptions;
import dev.wyck.wrapper.environment.particle.ParticleOptionsFactory;
import dev.wyck.wrapper.environment.particle.ParticleType;
import org.jspecify.annotations.NullMarked;

/**
 * Particle data for dust particles.
 *
 * @param color The color of the dust.
 * @param scale The scale of the dust.
 * @since 2.0.0
 * @version 2.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.0.0")
public record DustParticle(int color, float scale) implements ParticleData {

    @Override
    @AsOf("2.0.0")
    public ParticleOptions apply(ParticleType particleType) {
        return ParticleOptionsFactory.WIRE.get().dust(color, scale);
    }

    @AsOf("2.0.0")
    public static DustParticle of(String hexColor, float scale) {
        return new DustParticle(ParticleData.parseHex(hexColor), scale);
    }

    @AsOf("2.0.0")
    public static DustParticle of(String hexColor) {
        return of(hexColor, 1.0f);
    }
}
