package dev.wyck.wrapper.environment.particle.options;

import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.environment.particle.ParticleData;
import dev.wyck.wrapper.environment.particle.ParticleOptions;
import dev.wyck.wrapper.environment.particle.ParticleOptionsFactory;
import dev.wyck.wrapper.environment.particle.ParticleType;
import org.jspecify.annotations.NullMarked;

/**
 * Particle data for geyser particles.
 *
 * @param waterBlocks the number of water blocks
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public record GeyserParticle(int waterBlocks) implements ParticleData {
    @Override
    public ParticleOptions apply(ParticleType particleType) {
        return ParticleOptionsFactory.WIRE.get().geyser(particleType, waterBlocks);
    }

    /**
     * Creates a new GeyserParticle with the given amount of water blocks.
     * @param waterBlocks the amount of water blocks
     * @return a new GeyserParticle
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    public static GeyserParticle of(int waterBlocks) {
        return new GeyserParticle(waterBlocks);
    }
}
