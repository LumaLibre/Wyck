package dev.wyck.worldgen.structure.placement;

import dev.wyck.annotations.AsOf;
import dev.wyck.worldgen.structure.FrequencyReduction;
import dev.wyck.worldgen.structure.StructureSet;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import org.bukkit.util.BlockVector;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

/**
 * How a {@link StructureSet} spreads its structures across a dimension.
 *
 * @see RandomSpreadPlacement
 * @see ConcentricRingsPlacement
 * @see <a href="https://minecraft.wiki/w/Structure_set">Structure set</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
public interface StructurePlacement extends Wrapper {

    /**
     * The offset applied to a located position, used by {@code /locate structure}.
     * @return the locate offset
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    BlockVector locateOffset();

    /**
     * The reducer applied to {@link #frequency()}.
     * @return the frequency reduction method
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    FrequencyReduction frequencyReduction();

    /**
     * The fraction of eligible chunks that actually receive the structure, from 0 to 1.
     * @return the placement frequency
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    float frequency();

    /**
     * The salt mixed into the placement's random seed. Two sets sharing a salt place in the same
     * chunks.
     * @return the placement salt
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    int salt();

    /**
     * The zone around another structure set this placement avoids.
     * @return the exclusion zone, if present
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    Optional<ExclusionZone> exclusionZone();

    /**
     * Reads a Minecraft structure placement.
     * @param minecraftPlacement the structure placement to read
     * @return the decoded structure placement
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static StructurePlacement decode(Object minecraftPlacement) {
        record Holder() {
            static final Decoder<StructurePlacement> DECODER = Decoder.create("dev.wyck.decode.worldgen.structure.StructurePlacementDecoders");
        }
        return Holder.DECODER.decode(minecraftPlacement);
    }

    /**
     * Keeps a placement away from chunks where another structure set already placed.
     *
     * @param otherSet the structure set to stay clear of
     * @param chunkCount how many chunks of clearance to keep
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    record ExclusionZone(StructureSet otherSet, int chunkCount) {

        /**
         * Creates a new exclusion zone.
         * @param otherSet the structure set to stay clear of
         * @param chunkCount how many chunks of clearance to keep
         * @return a new exclusion zone
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public static ExclusionZone of(StructureSet otherSet, int chunkCount) {
            return new ExclusionZone(otherSet, chunkCount);
        }
    }
}
