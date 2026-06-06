package me.outspending.biomesapi.wrapper.environment.particle;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.particle.options.ParticleOptionsFactory;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * A wrapper for ambient particles in a biome, including their type, probability, and optional data.
 *
 * @see ParticleCatalog
 * @since 1.1.0
 * @author Jsinco
 * @param <T> The type of ParticleOptions associated with the particle.
 */
@NullMarked
@AsOf("1.1.0")
public class WrappedAmbientParticle<T> {

    private final WrappedParticleTypes ambientParticle;
    private final float probability;
    private final @Nullable ParticleData particleData;

    /**
     * Creates a WrappedAmbientParticle with the specified type, probability, and optional particle data.
     * @param ambientParticle The wrapped particle type.
     * @param probability The probability of the particle.
     * @param particleData The optional particle data.
     */
    @AsOf("1.1.0")
    public WrappedAmbientParticle(WrappedParticleTypes ambientParticle, float probability, @Nullable ParticleData particleData) {
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
        ParticleTypeHandle particleType = ambientParticle.getParticleType();
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

    /**
     * Constructs a new WrappedAmbientParticle instance.
     * @param ambientParticle The ambient particle type.
     * @param probability The probability of the particle.
     * @param particleData The particle data.
     * @return A new WrappedAmbientParticle instance.
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    public static WrappedAmbientParticle<?> of(WrappedParticleTypes ambientParticle, float probability, @Nullable ParticleData particleData) {
        return new WrappedAmbientParticle<>(ambientParticle, probability, (ParticleData) particleData);
    }

    /**
     * Constructs a new WrappedAmbientParticle instance for a simple particle type.
     * @param ambientParticle The ambient particle type.
     * @param probability The probability of the particle.
     * @return A new WrappedAmbientParticle instance for a simple particle type.
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    public static WrappedAmbientParticle<?> of(WrappedParticleTypes ambientParticle, float probability) {
        return new WrappedAmbientParticle<>(ambientParticle, probability);
    }
}
