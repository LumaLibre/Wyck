package dev.wyck.wrapper.worldgen.blockpredicates;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Checks if the position's y-level (with the specified offset) is within the height limits of the world.
 *
 * @see <a href="https://minecraft.wiki/w/Block_predicate#inside_world_bounds">Block predicate (inside world bounds)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface InsideWorldBoundsPredicate extends BlockPredicate {

    @ApiStatus.Internal
    ConstructWireProvider<InsideWorldBoundsPredicate> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.blockpredicates.InsideWorldBoundsPredicateImpl");

    /**
     * The offset from the block to check.
     * @return the offset
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockVector offset();

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
     * Creates a new inside world bounds predicate.
     * @param offset the offset
     * @return the inside world bounds predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static InsideWorldBoundsPredicate of(BlockVector offset) {
        return WIRE.construct(offset);
    }

    /**
     * Creates a new inside world bounds predicate with a zero offset.
     * @return the inside world bounds predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static InsideWorldBoundsPredicate of() {
        return WIRE.construct(new BlockVector(0, 0, 0));
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

    final class Builder {
        private BlockVector offset = new BlockVector(0, 0, 0);

        public Builder() {}

        public Builder(InsideWorldBoundsPredicate predicate) {
            this.offset = predicate.offset();
        }

        /**
         * Sets the offset.
         * @param offset the offset
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder offset(BlockVector offset) {
            this.offset = offset;
            return this;
        }

        /**
         * Builds the inside world bounds predicate.
         * @return the inside world bounds predicate
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public InsideWorldBoundsPredicate build() {
            return of(offset);
        }
    }
}