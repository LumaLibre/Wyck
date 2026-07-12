package dev.wyck.wrapper.worldgen.biome;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.model.biome.Biome;
import dev.wyck.wrapper.worldgen.climate.ClimatePoint;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

@NullMarked
@AsOf("3.0.0")
public interface MultiNoiseBiomeSource extends BiomeSource {

    @ApiStatus.Internal
    ConstructWireProvider<MultiNoiseBiomeSource> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.biome.MultiNoiseBiomeSourceImpl");

    /**
     * The biomes and their climate points.
     * @return the biomes and their climate points
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    List<Entry> biomes();

    /**
     * Whether to use Wyck's ASM transient biome source.
     * @return whether to use Wyck's ASM transient biome source
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    boolean isTransient();

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
     * Creates a new multi noise biome source.
     * @param biomes the biomes and their climate points
     * @param isTransient whether to use Wyck's ASM transient biome source
     * @return the multi noise biome source
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MultiNoiseBiomeSource of(List<Entry> biomes, boolean isTransient) {
        return WIRE.construct(biomes, isTransient);
    }

    /**
     * Creates a new multi noise biome source.
     * @param biomes the biomes and their climate points
     * @return the multi noise biome source
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MultiNoiseBiomeSource of(List<Entry> biomes) {
        return of(biomes, false);
    }

    /**
     * Creates a new builder.
     * @return a new builder
     * @since 3.0.0
     */
    static Builder builder() {
        return new Builder();
    }

    /**
     * A builder for {@link MultiNoiseBiomeSource}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {

        private final List<Entry> biomes = new ArrayList<>();
        private boolean isTransient = false;

        public Builder() {}

        public Builder(MultiNoiseBiomeSource source) {
            this.biomes.addAll(source.biomes());
            this.isTransient = source.isTransient();
        }

        /**
         * Adds a biome to the builder.
         * @param biome the biome to add
         * @param climatePoint the climate point to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder add(Biome biome, ClimatePoint climatePoint) {
            this.biomes.add(new Entry(biome, climatePoint));
            return this;
        }

        /**
         * Adds a biome to the builder.
         * @param biomeKey the biome key to add
         * @param point the climate point to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder add(ResourceKey biomeKey, ClimatePoint point) {
            this.biomes.add(new Entry(Biome.reference(biomeKey), point));
            return this;
        }

        /**
         * Sets whether to use Wyck's ASM transient biome source.
         * @param isTransient whether to use Wyck's ASM transient biome source
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder isTransient(boolean isTransient) {
            this.isTransient = isTransient;
            return this;
        }

        /**
         * Builds the multi noise biome source.
         * @return the multi noise biome source
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public MultiNoiseBiomeSource build() {
            return of(biomes, isTransient);
        }
    }

    /**
     * A biome and its climate point.
     * @param biome the biome
     * @param climatePoint the climate point
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    record Entry(Biome biome, ClimatePoint climatePoint) {}
}
