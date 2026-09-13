package dev.wyck.worldgen.structure.types;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.structure.StructureSettings;
import dev.wyck.wrapper.KeyedEnumTranslator;
import dev.wyck.wrapper.Registerable;
import dev.wyck.wrapper.WrappedEnumerator;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * An ocean ruin.
 *
 * @see <a href="https://minecraft.wiki/w/Ocean_Ruins">Ocean Ruins</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
@ApiStatus.Experimental
public interface OceanRuinStructure extends DefinedStructure, Registerable<OceanRuinStructure> {

    /**
     * The template set the ruin is drawn from.
     * @return the biome temperature
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    BiomeTemperature biomeTemperature();

    /**
     * The chance, from 0 to 1, that the ruin is a large one.
     * @return the large ruin probability
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    float largeProbability();

    /**
     * The chance, from 0 to 1, that a large ruin is surrounded by smaller ones.
     * @return the cluster probability
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    float clusterProbability();

    @Override
    @AsOf("3.4.0")
    default OceanRuinStructure withSettings(StructureSettings settings) {
        return toBuilder().settings(settings).build();
    }

    @Override
    @AsOf("3.4.0")
    default OceanRuinStructure withResourceKey(ResourceKey resourceKey) {
        return toBuilder().resourceKey(resourceKey).build();
    }

    @Override
    @AsOf("3.4.0")
    OceanRuinStructure register();

    /**
     * Converts this object back to a builder.
     * @return a builder with the same values as this object
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new ocean ruin.
     * @param resourceKey the resource key of the structure, or null if not present
     * @param settings the settings every structure carries
     * @param biomeTemperature the template set the ruin is drawn from
     * @param largeProbability the chance, from 0 to 1, that the ruin is a large one
     * @param clusterProbability the chance, from 0 to 1, that a large ruin is surrounded by smaller ones
     * @return a new ocean ruin
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static OceanRuinStructure of(@Nullable ResourceKey resourceKey, StructureSettings settings, BiomeTemperature biomeTemperature,
                                 float largeProbability, float clusterProbability) {
        Preconditions.checkArgument(largeProbability >= 0 && largeProbability <= 1, "largeProbability must be between 0 and 1, got %s", largeProbability);
        Preconditions.checkArgument(clusterProbability >= 0 && clusterProbability <= 1, "clusterProbability must be between 0 and 1, got %s", clusterProbability);
        record Holder() {
            static final ConstructWireProvider<OceanRuinStructure> WIRE =
                ConstructWireProvider.create("dev.wyck.worldgen.structure.types.OceanRuinStructureImpl");
        }
        return Holder.WIRE.construct(Optional.ofNullable(resourceKey), settings, biomeTemperature, largeProbability, clusterProbability);
    }

    /**
     * Creates a new builder with vanilla's probabilities: a 30% chance of a large ruin and a 90% chance
     * of a cluster around it.
     * @param settings the settings every structure carries
     * @param biomeTemperature the template set the ruin is drawn from
     * @return a new builder
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static Builder builder(StructureSettings settings, BiomeTemperature biomeTemperature) {
        return new Builder(settings, biomeTemperature);
    }

    /**
     * The template set an ocean ruin is drawn from.
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    enum BiomeTemperature implements WrappedEnumerator<BiomeTemperature> {
        /** Sandstone ruins, from warm oceans. */
        WARM("WARM"),

        /** Stone-brick ruins, from cold and temperate oceans. */
        COLD("COLD");

        public static final KeyedEnumTranslator<BiomeTemperature> TRANSLATOR = KeyedEnumTranslator.byKey(BiomeTemperature::getKey, BiomeTemperature.values());

        private final String key;

        @AsOf("3.4.0")
        BiomeTemperature(String key) {
            this.key = key;
        }

        @AsOf("3.4.0")
        @Override
        public KeyedEnumTranslator<BiomeTemperature> translator() {
            return TRANSLATOR;
        }

        /**
         * The vanilla name for this BiomeTemperature
         * @return the vanilla key for this enum value
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public String getKey() {
            return this.key;
        }
    }

    /**
     * Builder for {@link OceanRuinStructure}.
     * @since 3.4.0
     * @version 3.4.0
     * @author Jsinco
     */
    @AsOf("3.4.0")
    final class Builder {
        private @Nullable ResourceKey resourceKey;
        private StructureSettings settings;
        private BiomeTemperature biomeTemperature;
        private float largeProbability = 0.3F;
        private float clusterProbability = 0.9F;

        public Builder(StructureSettings settings, BiomeTemperature biomeTemperature) {
            this.settings = settings;
            this.biomeTemperature = biomeTemperature;
        }

        public Builder(OceanRuinStructure structure) {
            this.resourceKey = structure.resourceKey().orElse(null);
            this.settings = structure.settings();
            this.biomeTemperature = structure.biomeTemperature();
            this.largeProbability = structure.largeProbability();
            this.clusterProbability = structure.clusterProbability();
        }

        /**
         * Sets the resource key of the structure.
         * @param resourceKey the resource key of the structure
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder resourceKey(ResourceKey resourceKey) {
            this.resourceKey = resourceKey;
            return this;
        }

        /**
         * Sets the settings every structure carries.
         * @param settings the settings of the structure
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder settings(StructureSettings settings) {
            this.settings = settings;
            return this;
        }

        /**
         * Sets the template set the ruin is drawn from.
         * @param biomeTemperature the biome temperature
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder biomeTemperature(BiomeTemperature biomeTemperature) {
            this.biomeTemperature = biomeTemperature;
            return this;
        }

        /**
         * Sets the chance that the ruin is a large one.
         * @param largeProbability the large ruin probability, from 0 to 1
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder largeProbability(float largeProbability) {
            this.largeProbability = largeProbability;
            return this;
        }

        /**
         * Sets the chance that a large ruin is surrounded by smaller ones.
         * @param clusterProbability the cluster probability, from 0 to 1
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder clusterProbability(float clusterProbability) {
            this.clusterProbability = clusterProbability;
            return this;
        }

        /**
         * Builds the ocean ruin.
         * @return the ocean ruin
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public OceanRuinStructure build() {
            return of(resourceKey, settings, biomeTemperature, largeProbability, clusterProbability);
        }
    }
}
