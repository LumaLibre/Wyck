package me.outspending.biomesapi.wrapper.environment.particle;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.api.annotations.AsOf;
import me.outspending.biomesapi.api.wrapper.environment.particle.ParticleData;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.attribute.AmbientParticle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * A catalog of ambient particles for a biome.
 *
 * @since 1.1.0
 * @author Jsinco
 * @param particles The list of wrapped ambient particles.
 */
@AsOf("1.1.0")
public record ParticleCatalog(List<WrappedAmbientParticle<?>> particles) {

    public static final ParticleCatalog EMPTY = new ParticleCatalog(List.of());

    /**
     * Creates a ParticleCatalog with a single complex particle.
     * @param particleType The wrapped particle type.
     * @param probability The probability of the particle.
     * @return A ParticleCatalog containing the specified complex particle.
     */
    @AsOf("1.1.0")
    public static ParticleCatalog ofSimple(WrappedParticleTypes particleType, float probability) {
        Preconditions.checkArgument(particleType.isSimple(), "Particle type must be simple.");
        return new ParticleCatalog(List.of(new WrappedAmbientParticle<>(particleType, probability)));
    }

    /**
     * Creates a ParticleCatalog with a single complex particle.
     * @param particleType The wrapped particle type.
     * @param probability The probability of the particle.
     * @param data The particle data.
     * @return A ParticleCatalog containing the specified complex particle.
     */
    @AsOf("1.1.0")
    public static ParticleCatalog ofComplex(WrappedParticleTypes particleType, float probability, @NotNull ParticleData<?> data) {
        Preconditions.checkArgument(!particleType.isSimple(), "Particle type must not be simple.");
        return new ParticleCatalog(List.of(new WrappedAmbientParticle<>(particleType, probability, data)));
    }

    /**
     * Delegates the wrapped ambient particles to Minecraft ambient particles.
     * @return A list of Minecraft ambient particles.
     */
    @AsOf("1.1.0")
    public List<AmbientParticle> delegateParticles() {
        List<AmbientParticle> list = new ArrayList<>();

        for (var wrappedParticle : particles) {
            list.add(new AmbientParticle(wrappedParticle.toMinecraftParticle(), wrappedParticle.getProbability()));
        }

        return list;
    }


    @AsOf("1.1.0")
    public static Builder builder() {
        return new Builder();
    }

    /**
     * A builder for creating ParticleCatalog instances.
     *
     * @since 1.1.0
     * @author Jsinco
     */
    @AsOf("1.1.0")
    public static class Builder {
        private final List<WrappedAmbientParticle<?>> particles = new ArrayList<>();

        /**
         * Adds a particle to the catalog.
         * @param particleType The wrapped particle type.
         * @param probability The probability of the particle.
         * @param data The particle data, or null for simple particles.
         * @return The builder instance.
         * @param <T> The type of particle options.
         */
        @AsOf("1.1.0")
        public <T extends ParticleOptions> Builder addParticle(WrappedParticleTypes particleType, float probability, @Nullable ParticleData<T> data) {
            particles.add(new WrappedAmbientParticle<>(particleType, probability, data));
            return this;
        }

        /**
         * Adds a complex particle to the catalog.
         * @param particleType The wrapped particle type.
         * @param probability The probability of the particle.
         * @param data The particle data.
         * @return The builder instance.
         * @param <T> The type of particle options.
         */
        @AsOf("1.1.0")
        public <T extends ParticleOptions> Builder addComplex(WrappedParticleTypes particleType, float probability, @NotNull ParticleData<T> data) {
            Preconditions.checkArgument(!particleType.isSimple(), "Particle type must not be simple.");
            particles.add(new WrappedAmbientParticle<>(particleType, probability, data));
            return this;
        }

        /**
         * Adds a simple particle to the catalog.
         * @param particleType The wrapped particle type.
         * @param probability The probability of the particle.
         * @return The builder instance.
         */
        @AsOf("1.1.0")
        public Builder addSimple(WrappedParticleTypes particleType, float probability) {
            Preconditions.checkArgument(particleType.isSimple(), "Particle type must be simple.");
            particles.add(new WrappedAmbientParticle<>(particleType, probability));
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
