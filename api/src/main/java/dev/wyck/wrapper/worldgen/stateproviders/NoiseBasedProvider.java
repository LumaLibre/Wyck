package dev.wyck.wrapper.worldgen.stateproviders;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.worldgen.synth.NoiseParameters;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.concurrent.ThreadLocalRandom;

/**
 * A noise-based provider.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface NoiseBasedProvider extends BlockStateProvider {

    /**
     * The seed of the noise.
     * @return the seed
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    long seed();

    /**
     * The parameters of the noise.
     * @return the parameters
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    NoiseParameters noise(); // vanilla-name: parameters

    /**
     * Horizontal scale of the noise.
     * Must be a positive value.
     * @return the scale
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float scale();

    /**
     * Shared base for {@link NoiseBasedProvider} builders.
     *
     * @param <B> the concrete builder type
     * @param <T> the concrete provider type
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    @SuppressWarnings("unchecked")
    abstract class AbstractNoiseProviderBuilder<B extends AbstractNoiseProviderBuilder<B, T>, T extends NoiseBasedProvider> {
        protected long seed = ThreadLocalRandom.current().nextLong();
        protected @Nullable NoiseParameters parameters;
        protected float scale;

        protected AbstractNoiseProviderBuilder() {}

        protected AbstractNoiseProviderBuilder(NoiseBasedProvider provider) {
            this.seed = provider.seed();
            this.parameters = provider.noise();
            this.scale = provider.scale();
        }

        /**
         * Sets the seed of the noise.
         * @param seed the seed
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public B seed(long seed) {
            this.seed = seed;
            return (B) this;
        }

        /**
         * Sets the parameters of the noise.
         * @param parameters the parameters
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public B parameters(NoiseParameters parameters) {
            this.parameters = parameters;
            return (B) this;
        }

        /**
         * Sets the horizontal scale of the noise.
         * @param scale the scale
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public B scale(float scale) {
            this.scale = scale;
            return (B) this;
        }

        /**
         * Builds the provider, validating the shared fields before delegating to {@link #create()}.
         * @return the built provider
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public final T build() {
            Preconditions.checkNotNull(parameters, "parameters must be set");
            Preconditions.checkArgument(scale > 0, "scale must be positive");
            return create();
        }

        protected abstract T create();
    }

}
