package dev.wyck.worldgen.structure.placement;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.structure.FrequencyReduction;
import dev.wyck.worldgen.structure.RandomSpreadType;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * The world is cut into {@link #spacing() spacing}-chunk
 * cells and one candidate position is picked within the first {@code spacing - separation} chunks
 * of each.
 *
 * @see <a href="https://minecraft.wiki/w/Structure_set">Structure set</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
@ApiStatus.Experimental
public interface RandomSpreadPlacement extends StructurePlacement {

    /**
     * The side length, in chunks, of the cell one structure is placed in.
     * @return the placement spacing
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    int spacing();

    /**
     * The minimum distance, in chunks, between two structures of this set.
     * @return the placement separation
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    int separation();

    /**
     * How the candidate position is distributed inside its cell.
     * @return the spread type
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    RandomSpreadType spreadType();

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
     * Creates a new random-spread placement.
     * @param spacing the side length, in chunks, of the cell one structure is placed in
     * @param separation the minimum distance, in chunks, between two structures of this set
     * @param spreadType how the candidate position is distributed inside its cell
     * @param salt the salt mixed into the placement's random seed
     * @param locateOffset the offset applied to a located position
     * @param frequencyReduction the reducer applied to the frequency
     * @param frequency the fraction of eligible chunks that receive the structure
     * @param exclusionZone the zone around another structure set this placement avoids, or null
     * @return a new random-spread placement
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static RandomSpreadPlacement of(int spacing, int separation, RandomSpreadType spreadType, int salt, BlockVector locateOffset, FrequencyReduction frequencyReduction, float frequency, @Nullable ExclusionZone exclusionZone) {
        record Holder() {
            static final ConstructWireProvider<RandomSpreadPlacement> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.structure.placement.RandomSpreadPlacementImpl");
        }
        return Holder.WIRE.construct(spacing, separation, spreadType, salt, locateOffset, frequencyReduction, frequency, Optional.ofNullable(exclusionZone));
    }

    /**
     * Creates a new random-spread placement with vanilla's defaults for everything but spacing,
     * separation and salt.
     * @param spacing the side length, in chunks, of the cell one structure is placed in
     * @param separation the minimum distance, in chunks, between two structures of this set
     * @param salt the salt mixed into the placement's random seed
     * @return a new random-spread placement
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static RandomSpreadPlacement of(int spacing, int separation, int salt) {
        return of(spacing, separation, RandomSpreadType.LINEAR, salt, new BlockVector(), FrequencyReduction.DEFAULT, 1.0F, null);
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
     * Builder for {@link RandomSpreadPlacement}.
     * @since 3.4.0
     * @version 3.4.0
     * @author Jsinco
     */
    @AsOf("3.4.0")
    final class Builder {
        private int spacing = 32;
        private int separation = 8;
        private RandomSpreadType spreadType = RandomSpreadType.LINEAR;
        private int salt = 0;
        private BlockVector locateOffset = new BlockVector();
        private FrequencyReduction frequencyReduction = FrequencyReduction.DEFAULT;
        private float frequency = 1.0F;
        private @Nullable ExclusionZone exclusionZone;

        public Builder() {}

        public Builder(RandomSpreadPlacement placement) {
            this.spacing = placement.spacing();
            this.separation = placement.separation();
            this.spreadType = placement.spreadType();
            this.salt = placement.salt();
            this.locateOffset = placement.locateOffset();
            this.frequencyReduction = placement.frequencyReduction();
            this.frequency = placement.frequency();
            this.exclusionZone = placement.exclusionZone().orElse(null);
        }

        /**
         * Sets the side length, in chunks, of the cell one structure is placed in.
         * @param spacing the placement spacing
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder spacing(int spacing) {
            this.spacing = spacing;
            return this;
        }

        /**
         * Sets the minimum distance, in chunks, between two structures of this set.
         * @param separation the placement separation
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder separation(int separation) {
            this.separation = separation;
            return this;
        }

        /**
         * Sets how the candidate position is distributed inside its cell.
         * @param spreadType the spread type
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder spreadType(RandomSpreadType spreadType) {
            this.spreadType = spreadType;
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
         * Builds the random-spread placement.
         * @return the random-spread placement
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public RandomSpreadPlacement build() {
            return of(spacing, separation, spreadType, salt, locateOffset, frequencyReduction, frequency, exclusionZone);
        }
    }
}
