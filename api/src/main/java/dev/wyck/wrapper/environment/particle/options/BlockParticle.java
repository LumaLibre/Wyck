package dev.wyck.wrapper.environment.particle.options;

import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.environment.particle.ParticleData;
import dev.wyck.wrapper.environment.particle.ParticleOptionsHandle;
import dev.wyck.wrapper.environment.particle.ParticleTypeHandle;
import org.bukkit.Material;
import org.jspecify.annotations.NullMarked;

/**
 * Particle data for particles that require a block type.
 *
 * @param type The block type.
 * @since 2.0.0
 * @version 2.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.0.0")
public record BlockParticle(Material type) implements ParticleData {

    @Override
    @AsOf("2.0.0")
    public ParticleOptionsHandle apply(ParticleTypeHandle particleType) {
        return ParticleOptionsFactory.WIRE.get().block(particleType, type);
    }

    @AsOf("2.0.0")
    public static BlockParticle of(Material material) {
        return new BlockParticle(material);
    }
}
