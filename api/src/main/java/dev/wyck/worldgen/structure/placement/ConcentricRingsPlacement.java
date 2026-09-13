package dev.wyck.worldgen.structure.placement;

import dev.wyck.annotations.AsOf;
import dev.wyck.biome.Biome;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.tags.TagSet;
import dev.wyck.worldgen.structure.FrequencyReduction;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * Places {@link #count()} structures spread over rings expanding outward
 * from the world's center. Strongholds use this.
 *
 * @see <a href="https://minecraft.wiki/w/Structure_set">Structure set</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
@ApiStatus.Experimental
public interface ConcentricRingsPlacement extends StructurePlacement {

    /**
     * The distance, in chunks, from the world center to the first ring.
     * @return the distance to the first ring
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    int distance();

    /**
     * The spread, in chunks, of the positions within a ring.
     * @return the ring spread
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    int spread();

    /**
     * How many structures are placed in total.
     * @return the structure count
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    int count();

    /**
     * The biomes a ring position is snapped to, as an explicit set or a named biome tag.
     * @return the preferred biomes
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    TagSet<Biome> preferredBiomes();

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
     * Creates a new concentric-rings placement.
     * @param distance the distance, in chunks, from the world center to the first ring
     * @param spread the spread, in chunks, of the positions within a ring
     * @param count how many structures are placed in total
     * @param preferredBiomes the biomes a ring position is snapped to
     * @param salt the salt mixed into the placement's random seed
     * @param locateOffset the offset applied to a located position
     * @param frequencyReduction the reducer applied to the frequency
     * @param frequency the fraction of eligible chunks that receive the structure
     * @param exclusionZone the zone around another structure set this placement avoids, or null
     * @return a new concentric-rings placement
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static ConcentricRingsPlacement of(int distance, int spread, int count, TagSet<Biome> preferredBiomes, int salt, BlockVector locateOffset, FrequencyReduction frequencyReduction, float frequency, @Nullable ExclusionZone exclusionZone) {
        record Holder() {
            static final ConstructWireProvider<ConcentricRingsPlacement> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.structure.placement.ConcentricRingsPlacementImpl");
        }
        return Holder.WIRE.construct(distance, spread, count, preferredBiomes, salt, locateOffset, frequencyReduction, frequency, Optional.ofNullable(exclusionZone));
    }

    /**
     * Creates a new concentric-rings placement with vanilla's defaults for everything but the ring
     * geometry and preferred biomes.
     * @param distance the distance, in chunks, from the world center to the first ring
     * @param spread the spread, in chunks, of the positions within a ring
     * @param count how many structures are placed in total
     * @param preferredBiomes the biomes a ring position is snapped to
     * @return a new concentric-rings placement
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static ConcentricRingsPlacement of(int distance, int spread, int count, TagSet<Biome> preferredBiomes) {
        return of(distance, spread, count, preferredBiomes, 0, new BlockVector(), FrequencyReduction.DEFAULT, 1.0F, null);
    }

    /**
     * Creates a new builder.
     * @return a new builder
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link ConcentricRingsPlacement}.
     * @since 3.4.0
     * @version 3.4.0
     * @author Jsinco
     */
    @AsOf("3.4.0")
    final class Builder {
        private int distance = 32;
        private int spread = 3;
        private int count = 128;
        private TagSet<Biome> preferredBiomes = TagSet.ofBiomes();
        private int salt = 0;
        private BlockVector locateOffset = new BlockVector();
        private FrequencyReduction frequencyReduction = FrequencyReduction.DEFAULT;
        private float frequency = 1.0F;
        private @Nullable ExclusionZone exclusionZone;

        public Builder() {}

        public Builder(ConcentricRingsPlacement placement) {
            this.distance = placement.distance();
            this.spread = placement.spread();
            this.count = placement.count();
            this.preferredBiomes = placement.preferredBiomes();
            this.salt = placement.salt();
            this.locateOffset = placement.locateOffset();
            this.frequencyReduction = placement.frequencyReduction();
            this.frequency = placement.frequency();
            this.exclusionZone = placement.exclusionZone().orElse(null);
        }

        /**
         * Sets the distance, in chunks, from the world center to the first ring.
         * @param distance the distance to the first ring
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder distance(int distance) {
            this.distance = distance;
            return this;
        }

        /**
         * Sets the spread, in chunks, of the positions within a ring.
         * @param spread the ring spread
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder spread(int spread) {
            this.spread = spread;
            return this;
        }

        /**
         * Sets how many structures are placed in total.
         * @param count the structure count
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder count(int count) {
            this.count = count;
            return this;
        }

        /**
         * Sets the biomes a ring position is snapped to.
         * @param preferredBiomes the preferred biomes
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder preferredBiomes(TagSet<Biome> preferredBiomes) {
            this.preferredBiomes = preferredBiomes;
            return this;
        }

        /**
         * Sets the salt mixed into the placement's random seed.
         * @param salt the placement salt
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder salt(int salt) {
            this.salt = salt;
            return this;
        }

        /**
         * Sets the offset applied to a located position.
         * @param locateOffset the locate offset
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder locateOffset(BlockVector locateOffset) {
            this.locateOffset = locateOffset;
            return this;
        }

        /**
         * Sets the reducer applied to the frequency.
         * @param frequencyReduction the frequency reduction method
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder frequencyReduction(FrequencyReduction frequencyReduction) {
            this.frequencyReduction = frequencyReduction;
            return this;
        }

        /**
         * Sets the fraction of eligible chunks that receive the structure.
         * @param frequency the placement frequency
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder frequency(float frequency) {
            this.frequency = frequency;
            return this;
        }

        /**
         * Sets the zone around another structure set this placement avoids.
         * @param exclusionZone the exclusion zone
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder exclusionZone(ExclusionZone exclusionZone) {
            this.exclusionZone = exclusionZone;
            return this;
        }

        /**
         * Builds the concentric-rings placement.
         * @return the concentric-rings placement
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public ConcentricRingsPlacement build() {
            return of(distance, spread, count, preferredBiomes, salt, locateOffset, frequencyReduction, frequency, exclusionZone);
        }
    }
}
