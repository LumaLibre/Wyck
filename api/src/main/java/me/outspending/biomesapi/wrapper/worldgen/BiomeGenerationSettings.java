package me.outspending.biomesapi.wrapper.worldgen;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import me.outspending.biomesapi.wrapper.worldgen.carver.ConfiguredWorldCarver;
import me.outspending.biomesapi.wrapper.worldgen.placement.PlacedFeature;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Wraps Minecraft's BiomeGenerationSettings, the carvers and placed features
 * that compose a biome's world generation.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public interface BiomeGenerationSettings extends NmsHandle {

    Codec<BiomeGenerationSettings> CODEC = RecordCodecBuilder.create(i -> i.group(
        Codec.list(ConfiguredWorldCarver.CODEC).optionalFieldOf("carvers", List.of())
            .forGetter(BiomeGenerationSettings::carvers),
        Codec.unboundedMap(GenerationStep.CODEC, Codec.list(PlacedFeature.CODEC))
            .optionalFieldOf("features", Map.of())
            .forGetter(BiomeGenerationSettings::features)
    ).apply(i, BiomeGenerationSettings::fromCodec));

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.worldgen.BiomeGenerationSettingsFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        BiomeGenerationSettings create(List<ConfiguredWorldCarver> carvers, Map<GenerationStep, List<PlacedFeature>> features);
    }

    /**
     * The configured carvers applied to this biome's generation.
     * @return an immutable list of carvers
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    List<ConfiguredWorldCarver> carvers();

    /**
     * The placed features applied to this biome's generation, grouped by step.
     * @return an immutable map of the generation steps to placed features
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    Map<GenerationStep, List<PlacedFeature>> features();

    @AsOf("2.3.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * An empty generation settings carrying no carvers or features.
     * @return an empty BiomeGenerationSettings
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static BiomeGenerationSettings empty() {
        return new Builder().build();
    }

    @ApiStatus.Internal
    private static BiomeGenerationSettings fromCodec(List<ConfiguredWorldCarver> carvers, Map<GenerationStep, List<PlacedFeature>> features) {
        Builder builder = builder();
        carvers.forEach(builder::addCarver);
        features.forEach((step, list) -> list.forEach(feature -> builder.addFeature(step, feature)));
        return builder.build();
    }

    /**
     * A builder for creating a BiomeGenerationSettings.
     * @since 2.3.0
     * @version 2.3.0
     * @author Jsinco
     */
    @AsOf("2.3.0")
    final class Builder {

        private final List<ConfiguredWorldCarver> carvers;
        private final Map<GenerationStep, List<PlacedFeature>> features;

        @AsOf("2.3.0")
        public Builder() {
            this.carvers = new ArrayList<>();
            this.features = new EnumMap<>(GenerationStep.class);
        }

        /**
         * Adds a configured carver to this biome's generation.
         * @param carver the configured carver to add
         * @return this builder, for chaining
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder addCarver(ConfiguredWorldCarver carver) {
            Preconditions.checkNotNull(carver, "carver");
            this.carvers.add(carver);
            return this;
        }

        /**
         * Adds a placed feature to this biome's generation under the given step.
         * @param step the generation step the feature is placed in
         * @param feature the placed feature to add
         * @return this builder, for chaining
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder addFeature(GenerationStep step, PlacedFeature feature) {
            Preconditions.checkNotNull(step, "step");
            Preconditions.checkNotNull(feature, "feature");
            this.features.computeIfAbsent(step, s -> new ArrayList<>()).add(feature);
            return this;
        }

        @AsOf("2.3.0")
        public BiomeGenerationSettings build() {
            return WIRE.get().create(this.carvers, this.features);
        }
    }
}