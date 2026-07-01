package me.outspending.biomesapi.wrapper.environment.particle;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import me.outspending.biomesapi.annotations.AsOf;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

/**
 * A catalog of ambient particles for a biome.
 *
 * @since 1.1.0
 * @version 2.1.0
 * @author Jsinco
 * @param particles The list of wrapped ambient particles.
 */
@NullMarked
@AsOf("2.1.0")
public record ParticleCatalog(List<AmbientParticle<?>> particles) {

    public static final Codec<ParticleCatalog> CODEC = AmbientParticle.CODEC.listOf()
        .xmap(ParticleCatalog::new, ParticleCatalog::particles);

    public static final ParticleCatalog EMPTY = new ParticleCatalog(List.of());

    @AsOf("2.1.0")
    public ParticleCatalog {
        particles = List.copyOf(particles);
    }

    /**
     * Creates a ParticleCatalog with a single complex particle.
     * @param particleType The wrapped particle type.
     * @param probability The probability of the particle.
     * @return A ParticleCatalog containing the specified complex particle.
     */
    @AsOf("1.1.0")
    public static ParticleCatalog ofSimple(ParticleTypes particleType, float probability) {
        Preconditions.checkArgument(particleType.isSimple(), "Particle type must be simple.");
        return new ParticleCatalog(List.of(new AmbientParticle<>(particleType, probability)));
    }

    /**
     * Creates a ParticleCatalog with a single complex particle.
     * @param particleType The wrapped particle type.
     * @param probability The probability of the particle.
     * @param data The particle data.
     * @return A ParticleCatalog containing the specified complex particle.
     */
    @AsOf("1.1.0")
    public static ParticleCatalog ofComplex(ParticleTypes particleType, float probability, ParticleData data) {
        Preconditions.checkArgument(!particleType.isSimple(), "Particle type must not be simple.");
        return new ParticleCatalog(List.of(new AmbientParticle<>(particleType, probability, data)));
    }

    /**
     * Resolves each wrapped particle to a (handle, probability) pair.
     * NMS-side code can turn these into the underlying ambient-particle records.
     */
    @AsOf("1.1.0")
    public List<ResolvedAmbientParticle> resolveParticles() {
        List<ResolvedAmbientParticle> list = new ArrayList<>(particles.size());
        for (AmbientParticle<?> wrapped : particles) {
            list.add(new ResolvedAmbientParticle(wrapped.toParticleOptions(), wrapped.getProbability()));
        }
        return list;
    }

    /**
     * Creates a new ParticleCatalog with the specified particle added.
     * @param type The wrapped particle type.
     * @param probability The probability of the particle.
     * @return A new ParticleCatalog with the specified particle added.
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    public ParticleCatalog with(ParticleTypes type, float probability) {
        Preconditions.checkArgument(type.isSimple(), "Particle type must be simple. Got: " + type);
        List<AmbientParticle<?>> copy = new ArrayList<>(particles);
        copy.add(new AmbientParticle<>(type, probability));
        return new ParticleCatalog(copy);
    }

    /**
     * Creates a new ParticleCatalog with the specified particle added.
     * @param type The wrapped particle type.
     * @param probability The probability of the particle.
     * @param data The particle data.
     * @return A new ParticleCatalog with the specified particle added.
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    public <T extends ParticleData> ParticleCatalog with(ParticleTypes type, float probability, @Nullable T data) {
        List<AmbientParticle<?>> copy = new ArrayList<>(particles);
        copy.add(new AmbientParticle<>(type, probability, data));
        return new ParticleCatalog(copy);
    }

    /**
     * Creates a new ParticleCatalog builder.
     * @return A new ParticleCatalog builder.
     * @since 1.1.0
     */
    @AsOf("1.1.0")
    public static Builder builder() {
        return new Builder();
    }

    /**
     * A builder for creating ParticleCatalog instances.
     *
     * @since 1.1.0
     * @version 2.1.0
     * @author Jsinco
     */
    @AsOf("2.1.0")
    public static class Builder {
        private final List<AmbientParticle<?>> particles = new ArrayList<>();

        /**
         * Adds a particle to the catalog.
         * @param particleType The wrapped particle type.
         * @param probability The probability of the particle.
         * @param data The particle data, or null for simple particles.
         * @return The builder instance.
         */
        @AsOf("1.1.0")
        public Builder addParticle(ParticleTypes particleType, float probability, @Nullable ParticleData data) {
            particles.add(new AmbientParticle<>(particleType, probability, data));
            return this;
        }

        /**
         * Adds a particle to the catalog.
         * @param particleType The wrapped particle type.
         * @param probability The probability of the particle.
         * @return The builder instance.
         */
        @AsOf("1.1.0")
        public Builder addParticle(ParticleTypes particleType, float probability) {
            particles.add(new AmbientParticle<>(particleType, probability));
            return this;
        }

        /**
         * Adds a complex particle to the catalog.
         * @param particleType The wrapped particle type.
         * @param probability The probability of the particle.
         * @param data The particle data.
         * @return The builder instance.
         */
        @AsOf("1.1.0")
        public Builder addComplex(ParticleTypes particleType, float probability, ParticleData data) {
            Preconditions.checkArgument(!particleType.isSimple(), "Particle type must not be simple.");
            particles.add(new AmbientParticle<>(particleType, probability, data));
            return this;
        }

        /**
         * Adds a simple particle to the catalog.
         * @param particleType The wrapped particle type.
         * @param probability The probability of the particle.
         * @return The builder instance.
         */
        @AsOf("1.1.0")
        public Builder addSimple(ParticleTypes particleType, float probability) {
            Preconditions.checkArgument(particleType.isSimple(), "Particle type must be simple.");
            particles.add(new AmbientParticle<>(particleType, probability));
            return this;
        }

        /**
         * Clears the catalog.
         * @return The builder instance.
         * @since 2.1.0
         */
        @AsOf("2.1.0")
        public Builder clear() {
            particles.clear();
            return this;
        }

        /**
         * Adds all particles from another ParticleCatalog to this one.
         * @param source The source ParticleCatalog.
         * @return The builder instance.
         * @since 2.1.0
         */
        @AsOf("2.1.0")
        public Builder addAll(ParticleCatalog source) {
            particles.addAll(source.particles);
            return this;
        }

        /**
         * Builds the ParticleCatalog.
         * @return The constructed ParticleCatalog.
         */
        @AsOf("1.1.0")
        public ParticleCatalog build() {
            return new ParticleCatalog(particles);
        }
    }
}
