package dev.wyck.wrapper.environment.particle.options;

import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.environment.particle.ParticleData;
import dev.wyck.wrapper.environment.particle.ParticleOptions;
import dev.wyck.wrapper.environment.particle.ParticleOptionsFactory;
import dev.wyck.wrapper.environment.particle.ParticleType;
import org.jspecify.annotations.NullMarked;

/**
 * Particle data for dust transition particles.
 * 
 * @param fromColor The color of the dust.
 * @param toColor The color of the dust.
 * @param scale The scale of the dust.
 * @since 2.0.0
 * @version 2.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.0.0")
public record DustTransitionParticle(int fromColor, int toColor, float scale) implements ParticleData {

    @Override
    @AsOf("2.0.0")
    public ParticleOptions apply(ParticleType particleType) {
        return ParticleOptionsFactory.WIRE.get().dustTransition(fromColor, toColor, scale);
    }

    @AsOf("2.0.0")
    public static DustTransitionParticle of(String fromHexColor, String toHexColor, float scale) {
        return new DustTransitionParticle(ParticleData.parseHex(fromHexColor), ParticleData.parseHex(toHexColor), scale);
    }

    @AsOf("2.0.0")
    public static DustTransitionParticle of(String fromHexColor, String toHexColor) {
        return of(fromHexColor, toHexColor, 1.0f);
    }
}
