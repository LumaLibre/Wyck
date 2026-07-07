package dev.wyck.wrapper.worldgen.placement;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

/**
 * Returns all specified positions if they are in the current chunk.
 *
 * @see <a href="https://minecraft.wiki/w/Placed_feature#Placement_modifiers">Placement modifiers</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface FixedPlacement extends PlacementModifier {

    @ApiStatus.Internal
    ConstructWireProvider<FixedPlacement> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.placement.FixedPlacementImpl");

    /**
     * A list of all placement positions.
     * This can be empty.
     * @return the list of positions
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    List<BlockVector> positions();

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
     * Creates a new fixed placement.
     * @param positions the list of positions
     * @return a new fixed placement
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static FixedPlacement of(List<BlockVector> positions) {
        return WIRE.construct(positions);
    }

    /**
     * Creates a new fixed placement.
     * @param positions the positions
     * @return a new fixed placement
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static FixedPlacement of(BlockVector... positions) {
        return of(List.of(positions));
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
     * Builder for {@link FixedPlacement}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private final List<BlockVector> positions = new ArrayList<>();

        public Builder() {}

        public Builder(FixedPlacement placement) {
            this.positions.addAll(placement.positions());
        }

        /**
         * Adds a list of positions to the builder.
         * @param positions the positions to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder positions(List<BlockVector> positions) {
            this.positions.addAll(positions);
            return this;
        }

        /**
         * Adds a list of positions to the builder.
         * @param positions the positions to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder positions(BlockVector... positions) {
            this.positions.addAll(List.of(positions));
            return this;
        }

        /**
         * Adds a single position to the builder.
         * @param position the position to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder position(BlockVector position) {
            this.positions.add(position);
            return this;
        }

        /**
         * Builds the fixed placement.
         * @return the fixed placement
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public FixedPlacement build() {
            return of(positions);
        }
    }
}
