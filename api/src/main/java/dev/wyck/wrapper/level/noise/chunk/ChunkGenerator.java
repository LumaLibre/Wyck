package dev.wyck.wrapper.level.noise.chunk;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.WireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.model.level.LevelCreator;
import dev.wyck.wrapper.internal.Wrapper;
import dev.wyck.wrapper.level.BiomeSource;
import dev.wyck.wrapper.level.noise.NoiseGeneratorSettings;
import dev.wyck.wrapper.level.noise.Noise;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Wraps a chunk generator for use in a {@link LevelCreator}'s level stem.
 *
 * @version 2.5.0
 * @since 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
public interface ChunkGenerator extends Wrapper {

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("dev.wyck.wrapper.level.noise.chunk.ChunkGeneratorFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        ChunkGenerator noise(BiomeSource biomeSource, Noise.Reference settings);

        ChunkGenerator noise(BiomeSource biomeSource, NoiseGeneratorSettings settings);
    }

    /**
     * The biome source this generator draws from.
     * @return the biome source
     * @since 2.5.0
     */
    @AsOf("2.5.0")
    BiomeSource biomeSource();

    /**
     * The noise generator.
     * @return the noise generator
     * @since 2.5.0
     */
    @AsOf("2.5.0")
    Noise noise();

    /**
     * Converts this chunk generator to a builder.
     * @return a new builder for this chunk generator
     * @since 2.5.0
     */
    @AsOf("2.5.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Builds a noise generator over the given biome source, referencing the given settings.
     * @param biomeSource the biome source the generator draws from
     * @param abstractNoise the noise generator settings
     * @return a wrapped chunk generator
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static ChunkGenerator of(BiomeSource biomeSource, Noise abstractNoise) {
        if (abstractNoise instanceof Noise.Reference ref) {
            return WIRE.get().noise(biomeSource, ref);
        } else if (abstractNoise instanceof NoiseGeneratorSettings settings) {
            return WIRE.get().noise(biomeSource, settings);
        }
        throw new IllegalArgumentException("Unknown noise type: " + abstractNoise.getClass().getName());
    }

    /**
     * Builds a noise generator over the given biome source, referencing the given settings.
     *
     * @param biomeSource the biome source the generator draws from
     * @param resourceKey the noise generator settings key (e.g. {@code minecraft:overworld})
     * @return a wrapped chunk generator
     * @since 2.5.0
     */
    @AsOf("2.4.0")
    static ChunkGenerator of(BiomeSource biomeSource, ResourceKey resourceKey) {
        return of(biomeSource, Noise.reference(resourceKey));
    }

    /**
     * Creates a new builder for a chunk generator.
     * @return a new builder
     * @since 2.5.0
     */
    @AsOf("2.5.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * A builder for creating chunk generators.
     * @since 2.5.0
     * @version 2.5.0
     * @author Jsinco
     */
    @AsOf("2.5.0")
    final class Builder {
        private @Nullable BiomeSource biomeSource;
        private @Nullable Noise noise;

        public Builder() {}

        public Builder(ChunkGenerator other) {
            this.biomeSource = other.biomeSource();
            this.noise = other.noise();
        }

        /**
         * Sets the biome source of the chunk generator.
         * @param biomeSource the biome source of the chunk generator
         * @return this builder
         * @since 2.5.0
         */
        @AsOf("2.5.0")
        public Builder biomeSource(BiomeSource biomeSource) {
            this.biomeSource = biomeSource;
            return this;
        }

        /**
         * Sets the noise generator of the chunk generator.
         * @param noise the noise generator of the chunk generator
         * @return this builder
         * @since 2.5.0
         */
        @AsOf("2.5.0")
        public Builder noise(Noise noise) {
            this.noise = noise;
            return this;
        }

        /**
         * Sets the noise generator of the chunk generator.
         * @param resourceKey the noise generator settings key
         * @return this builder
         * @since 2.5.0
         */
        @AsOf("2.5.0")
        public Builder noise(ResourceKey resourceKey) {
            this.noise = Noise.reference(resourceKey);
            return this;
        }

        /**
         * Builds the chunk generator.
         * @return the chunk generator
         * @since 2.5.0
         */
        @AsOf("2.5.0")
        public ChunkGenerator build() {
            Preconditions.checkNotNull(biomeSource, "biomeSource cannot be null");
            Preconditions.checkNotNull(noise, "noise cannot be null");
            return of(biomeSource, noise);
        }
    }
}