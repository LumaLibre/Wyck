package dev.wyck.wrapper.worldgen.blockpredicates;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.bukkit.block.BlockFace;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Checks if the block at the specified offset has a full block supporting surface in the specified direction.
 *
 * @see <a href="https://minecraft.wiki/w/Block_predicate#has_sturdy_face">Block predicate (has sturdy face)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface HasSturdyFacePredicate extends BlockPredicate {

    @ApiStatus.Internal
    ConstructWireProvider<HasSturdyFacePredicate> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.blockpredicates.HasSturdyFacePredicateImpl");

    /**
     * The offset from the block to check.
     * @return the offset
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockVector offset();

    /**
     * The direction to check.
     * @return the direction
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockFace direction();

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
     * Creates a new has sturdy face predicate.
     * @param offset the offset
     * @param direction the direction
     * @return the has sturdy face predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static HasSturdyFacePredicate of(BlockVector offset, BlockFace direction) {
        return WIRE.construct(offset, direction);
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
        private @Nullable BlockFace direction;

        public Builder() {}

        public Builder(HasSturdyFacePredicate predicate) {
            this.offset = predicate.offset();
            this.direction = predicate.direction();
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
         * Sets the direction.
         * @param direction the direction
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder direction(BlockFace direction) {
            this.direction = direction;
            return this;
        }

        /**
         * Builds the has sturdy face predicate.
         * @return the has sturdy face predicate
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public HasSturdyFacePredicate build() {
            // skipping preconditions checks for offsets
            Preconditions.checkNotNull(direction, "direction must be set");
            return of(offset, direction);
        }
    }
}
