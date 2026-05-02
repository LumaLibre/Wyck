package me.outspending.biomesapi.wrapper.environment.particle;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.particle.options.ParticleOptionsFactory;
import org.jetbrains.annotations.Nullable;

/**
 * A wrapper for ambient particles in a biome, including their type, probability, and optional data.
 *
 * @see ParticleCatalog
 * @since 1.1.0
 * @author Jsinco
 * @param <T> The type of ParticleOptions associated with the particle.
 */
@AsOf("1.1.0")
public class WrappedAmbientParticle<T extends ParticleData<T>> {

    private final WrappedParticleTypes ambientParticle;
    private final float probability;
    private final @Nullable ParticleData<T> particleData;

    /**
     * Creates a WrappedAmbientParticle with the specified type, probability, and optional particle data.
     * @param ambientParticle The wrapped particle type.
     * @param probability The probability of the particle.
     * @param particleData The optional particle data.
     */
    @AsOf("1.1.0")
    public WrappedAmbientParticle(WrappedParticleTypes ambientParticle, float probability, @Nullable ParticleData<T> particleData) {
        Preconditions.checkArgument(ambientParticle.isSimple() == (particleData == null), "Simple particles must not have particle data; complex particles must have particle data.");

        this.ambientParticle = ambientParticle;
        this.probability = probability;
        this.particleData = particleData;
    }

    /**
     * Creates a WrappedAmbientParticle for a simple particle type without additional data.
     * @param ambientParticle The wrapped particle type.
     * @param probability The probability of the particle.
     */
    @AsOf("1.1.0")
    public WrappedAmbientParticle(WrappedParticleTypes ambientParticle, float probability) {
        Preconditions.checkArgument(ambientParticle.isSimple(), "Particle data must be provided for complex particle types.");

        this.ambientParticle = ambientParticle;
        this.probability = probability;
        this.particleData = null;
    }

    /**
     * Delegates the wrapped ambient particle to a Minecraft ParticleOptions.
     * @return The corresponding Minecraft ParticleOptions.
     */
    @AsOf("1.1.0")
    public ParticleOptionsHandle toParticleOptions() {
        ParticleTypeHandle<T> particleType = ambientParticle.getParticleType();
        if (ambientParticle.isSimple()) {
            return ParticleOptionsFactory.WIRE.get().simple(particleType);
        }

        Preconditions.checkNotNull(particleData, "Particle data must not be null for complex particle types.");
        return particleData.apply(particleType);
    }

    /**
     * Gets the probability of the particle.
     * @return The probability of the particle.
     */
    @AsOf("1.1.0")
    public float getProbability() {
        return probability;
    }
}
