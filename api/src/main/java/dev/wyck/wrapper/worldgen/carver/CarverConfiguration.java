package dev.wyck.wrapper.worldgen.carver;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.worldgen.feature.configurations.ProbabilityFeatureConfiguration;
import dev.wyck.wrapper.worldgen.valueproviders.FloatProvider;
import dev.wyck.wrapper.worldgen.valueproviders.HeightProvider;
import dev.wyck.wrapper.worldgen.valueproviders.VerticalAnchor;
import org.bukkit.Material;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Wraps Minecraft's CarverConfiguration class.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public interface CarverConfiguration extends ProbabilityFeatureConfiguration {

    /**
     * @return the y-level of the carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    HeightProvider y();

    /**
     * @return the y-scale of the carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    FloatProvider yScale();

    /**
     * @return the y-level of the lava level of the carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    VerticalAnchor lavaLevel();

    /**
     * @return the debug settings of the carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    CarverDebugSettings debugSettings();

    /**
     * @return the materials that can be replaced by this carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    Set<Material> replaceable();

    @AsOf("3.0.0")
    @SuppressWarnings("unchecked")
    abstract class CarverConfigurationBuilder<T extends CarverConfiguration, B extends CarverConfigurationBuilder<T, B>> {
        protected float probability;
        protected @Nullable HeightProvider y;
        protected @Nullable FloatProvider yScale;
        protected @Nullable VerticalAnchor lavaLevel;
        protected @Nullable CarverDebugSettings debugSettings;
        protected Set<Material> replaceable = new HashSet<>();

        protected CarverConfigurationBuilder() {}

        protected CarverConfigurationBuilder(CarverConfiguration configuration) {
            this.probability = configuration.probability();
            this.y = configuration.y();
            this.yScale = configuration.yScale();
            this.lavaLevel = configuration.lavaLevel();
            this.debugSettings = configuration.debugSettings();
            this.replaceable.addAll(configuration.replaceable());
        }

        @AsOf("3.0.0")
        public B probability(float probability) {
            this.probability = probability;
            return (B) this;
        }

        @AsOf("3.0.0")
        public B y(HeightProvider y) {
            this.y = y;
            return (B) this;
        }

        @AsOf("3.0.0")
        public B yScale(FloatProvider yScale) {
            this.yScale = yScale;
            return (B) this;
        }

        @AsOf("3.0.0")
        public B lavaLevel(VerticalAnchor lavaLevel) {
            this.lavaLevel = lavaLevel;
            return (B) this;
        }

        @AsOf("3.0.0")
        public B debugSettings(CarverDebugSettings debugSettings) {
            this.debugSettings = debugSettings;
            return (B) this;
        }

        @AsOf("3.0.0")
        public B replaceable(Set<Material> replaceable) {
            this.replaceable = replaceable;
            return (B) this;
        }

        // Friendly builder methods

        @AsOf("3.0.0")
        public B replaceable(Material... replaceable) {
            Collections.addAll(this.replaceable, replaceable);
            return (B) this;
        }

        public final T build() {
            Preconditions.checkArgument(probability >= 0.0F && probability <= 1.0F, "probability must be between 0.0 and 1.0");
            Preconditions.checkNotNull(y, "y must be set");
            Preconditions.checkNotNull(yScale, "yScale must be set");
            Preconditions.checkNotNull(lavaLevel, "lavaLevel must be set");
            Preconditions.checkNotNull(debugSettings, "debugSettings must be set");
            return create();
        }

        protected abstract T create();
    }
}