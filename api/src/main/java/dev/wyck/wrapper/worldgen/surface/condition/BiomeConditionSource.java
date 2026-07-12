package dev.wyck.wrapper.worldgen.surface.condition;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.model.biome.Biome;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

/**
 * Checks the biome at the current position.
 *
 * @see <a href="https://minecraft.wiki/w/Surface_rule#Surface_conditions">Surface rule (surface conditions)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface BiomeConditionSource extends ConditionSource {

    @ApiStatus.Internal
    ConstructWireProvider<BiomeConditionSource> WIRE = ConstructWireProvider.create("dev.wyck.*?.wrapper.worldgen.surface.condition.BiomeConditionSourceImpl");

    /**
     * A list of biomes where this condition matches.
     * @return the biomes
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    List<Biome> targets(); // codec name: biome_is; vanilla-type: ResourceKey<Biome>[]

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
     * Creates a new biome condition source.
     * @param targets the biomes
     * @return the biome condition source
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BiomeConditionSource of(List<Biome> targets) {
        return WIRE.construct(targets);
    }

    /**
     * Creates a new biome condition source.
     * @param targets the biomes
     * @return the biome condition source
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BiomeConditionSource of(Biome... targets) {
        return of(List.of(targets));
    }

    /**
     * Creates a new builder.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link BiomeConditionSource}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private final List<Biome> targets = new ArrayList<>();

        public Builder() {}

        public Builder(BiomeConditionSource other) {
            this.targets.addAll(other.targets());
        }

        /**
         * Sets the biomes where this condition matches.
         * @param targets the biomes
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder targets(List<Biome> targets) {
            this.targets.addAll(targets);
            return this;
        }

        /**
         * Adds a biome to the list of biomes where this condition matches.
         * @param biome the biome
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder target(Biome biome) {
            this.targets.add(biome);
            return this;
        }

        /**
         * Adds a biome to the list of biomes where this condition matches.
         * @param biomeKey the biome key
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder target(ResourceKey biomeKey) {
            this.targets.add(Biome.reference(biomeKey));
            return this;
        }

        /**
         * Builds the biome condition source.
         * @return the biome condition source
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public BiomeConditionSource build() {
            return of(targets);
        }
    }
}
