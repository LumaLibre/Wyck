package dev.wyck.wrapper.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * A configuration for a feature that has a probability of being placed.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface ProbabilityFeatureConfiguration extends FeatureConfiguration {

    @ApiStatus.Internal
    ConstructWireProvider<ProbabilityFeatureConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.feature.configurations.ProbabilityFeatureConfigurationImpl");

    /**
     * Gets the probability of the feature being placed between 0.0 and 1.0.
     * @return the probability of the feature being placed
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float probability();

    /**
     * Converts this object back to a builder.
     * @return a builder with the same values as this object
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new instance of this configuration.
     * @param probability the probability of the feature being placed between 0.0 and 1.0
     * @return a new instance of this configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ProbabilityFeatureConfiguration of(float probability) {
        return WIRE.construct(probability);
    }

    /**
     * Creates a new builder for this configuration.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link ProbabilityFeatureConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private float probability = 0.0F;

        public Builder() {}

        public Builder(ProbabilityFeatureConfiguration configuration) {
            this.probability = configuration.probability();
        }

        /**
         * Sets the probability of the feature being placed between 0.0 and 1.0.
         * @param probability the probability of the feature being placed
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder probability(float probability) {
            this.probability = probability;
            return this;
        }

        /**
         * Builds the configuration.
         * @return the configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public ProbabilityFeatureConfiguration build() {
            Preconditions.checkArgument(probability >= 0.0F && probability <= 1.0F, "probability must be between 0.0 and 1.0");
            return of(probability);
        }
    }
}