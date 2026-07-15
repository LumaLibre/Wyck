package dev.wyck.environment.particle;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstraction of {@code List<AmbientParticle>} from Minecraft.
 *
 * @since 1.1.0
 * @version 2.1.0
 * @author Jsinco
 * @param particles The list of wrapped ambient particles.
 */
@NullMarked
@AsOf("2.1.0")
public record ParticleCatalog(List<AmbientParticle> particles) {

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
        return new ParticleCatalog(List.of(AmbientParticle.of(particleType, probability)));
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
        return new ParticleCatalog(List.of(AmbientParticle.of(particleType, probability, data)));
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
        List<AmbientParticle> copy = new ArrayList<>(particles);
        copy.add(AmbientParticle.of(type, probability));
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
        List<AmbientParticle> copy = new ArrayList<>(particles);
        copy.add(AmbientParticle.of(type, probability, data));
        return new ParticleCatalog(copy);
    }

    /**
     * Converts this ParticleCatalog to a Builder.
     * @return A new Builder instance with the same properties as this instance.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    public Builder toBuilder() {
        return new Builder(this);
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
        private final List<AmbientParticle> particles = new ArrayList<>();

        public Builder() {}

        public Builder(ParticleCatalog catalog) {
            this.particles.addAll(catalog.particles);
        }

        /**
         * Adds a particle to the catalog.
         * @param particleType The wrapped particle type.
         * @param probability The probability of the particle.
         * @param data The particle data, or null for simple particles.
         * @return The builder instance.
         */
        @AsOf("1.1.0")
        public Builder particle(ParticleTypes particleType, float probability, @Nullable ParticleData data) {
            this.particles.add(AmbientParticle.of(particleType, probability, data));
            return this;
        }

        /**
         * Adds a particle to the catalog.
         * @param particleType The wrapped particle type.
         * @param probability The probability of the particle.
         * @return The builder instance.
         */
        @AsOf("1.1.0")
        public Builder particle(ParticleTypes particleType, float probability) {
            this.particles.add(AmbientParticle.of(particleType, probability));
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
        public Builder complex(ParticleTypes particleType, float probability, ParticleData data) {
            Preconditions.checkArgument(!particleType.isSimple(), "Particle type must not be simple.");
            this.particles.add(AmbientParticle.of(particleType, probability, data));
            return this;
        }

        /**
         * Adds a simple particle to the catalog.
         * @param particleType The wrapped particle type.
         * @param probability The probability of the particle.
         * @return The builder instance.
         */
        @AsOf("1.1.0")
        public Builder simple(ParticleTypes particleType, float probability) {
            Preconditions.checkArgument(particleType.isSimple(), "Particle type must be simple.");
            this.particles.add(AmbientParticle.of(particleType, probability));
            return this;
        }

        /**
         * Clears the catalog.
         * @return The builder instance.
         * @since 2.1.0
         */
        @AsOf("2.1.0")
        public Builder clear() {
            this.particles.clear();
            return this;
        }

        /**
         * Adds all particles from another ParticleCatalog to this one.
         * @param source The source ParticleCatalog.
         * @return The builder instance.
         * @since 2.1.0
         */
        @AsOf("2.1.0")
        public Builder merge(ParticleCatalog source) {
            this.particles.addAll(source.particles);
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
