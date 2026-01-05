package me.outspending.biomesapi.renderer;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.AmbientParticle;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Represents a particle renderer with a specific ambient particle and probability.
 *
 * @version 0.0.8
 * @since 0.0.1
 * @author Outspending
 */
@AsOf("0.0.8")
public record ParticleRenderer(@NotNull Map<@NotNull AmbientParticle, @NotNull Float> ambientParticles) {


    /**
     * Creates a new ParticleRenderer with the given ambient particles and their corresponding probabilities.
     * This is a static factory method that provides a convenient way to create a new ParticleRenderer instance.
     *
     * @param ambientParticles the list of ambient particles for the particle renderer
     * @param probabilities    the list of probabilities for the particle renderer
     * @return a new ParticleRenderer with the given ambient particles and probabilities
     * @version 0.0.8
     */
    @AsOf("0.0.8")
    public static @NotNull ParticleRenderer of(@NotNull List<@NotNull AmbientParticle> ambientParticles, @NotNull List<@NotNull Float> probabilities) {
        if (ambientParticles.size() != probabilities.size()) {
            throw new IllegalArgumentException("The size of ambientParticles and probabilities must be the same.");
        }

        Map<AmbientParticle, Float> particleMap = new HashMap<>();
        for (int i = 0; i < ambientParticles.size(); i++) {
            particleMap.put(ambientParticles.get(i), probabilities.get(i));
        }

        return new ParticleRenderer(particleMap);
    }

    /**
     * Creates a new ParticleRenderer with the given ambient particle and probability.
     * This is a static factory method that provides a convenient way to create a new ParticleRenderer instance.
     *
     * @param ambientParticle the ambient particle for the particle renderer
     * @param probability     the probability for the particle renderer
     * @return a new ParticleRenderer with the given ambient particle and probability
     * @version 0.0.1
     */
    @AsOf("0.0.1")
    public static @NotNull ParticleRenderer of(@NotNull AmbientParticle ambientParticle, float probability) {
        return new ParticleRenderer(Map.of(ambientParticle, probability));
    }

    /**
     * Provides a default setting for the ParticleRenderer.
     * The default ambient particle is set to CLOUD and the default probability is set to 0.008F.
     *
     * @return a new instance of ParticleRenderer with default settings
     * @version 0.0.1
     */
    @AsOf("0.0.1")
    public static @NotNull ParticleRenderer defaultSettings() {
        return new ParticleRenderer(Map.of(AmbientParticle.CLOUD, 0.008F));
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ParticleRenderer that = (ParticleRenderer) obj;
        return ambientParticles.equals(that.ambientParticles);
    }

    @Override
    public int hashCode() {
        return ambientParticles.hashCode();
    }

    /**
     * Builder class for constructing ParticleRenderer instances.
     *
     * @version 0.0.8
     * @since 0.0.8
     * @author Jsinco
     */
    @AsOf("0.0.8")
    public static class Builder {
        private final Map<AmbientParticle, Float> ambientParticles = new HashMap<>();

        /**
         * Adds an ambient particle with the specified probability to the ParticleRenderer being built.
         *
         * @param ambientParticle the ambient particle to add
         * @param probability     the probability of the ambient particle
         * @return the Builder instance for method chaining
         */
        @AsOf("0.0.8")
        public Builder addAmbientParticle(@NotNull AmbientParticle ambientParticle, float probability) {
            ambientParticles.put(ambientParticle, probability);
            return this;
        }

        @AsOf("0.0.8")
        public ParticleRenderer build() {
            return new ParticleRenderer(ambientParticles);
        }
    }
}