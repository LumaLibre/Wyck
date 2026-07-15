package dev.wyck.worldgen.blockpredicates;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Checks if the block at the specified offset is solid.
 *
 * @see <a href="https://minecraft.wiki/w/Block_predicate#solid">Block predicate (solid)</a>
 * @apiNote Deprecated in vanilla in favor of {@link HasSturdyFacePredicate}, which checks for a full
 * supporting surface on a specific face rather than relying on the ambiguous notion of solidity.
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface SolidPredicate extends BlockPredicate {

    @ApiStatus.Internal
    ConstructWireProvider<SolidPredicate> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.blockpredicates.SolidPredicateImpl");

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
     * Creates a new solid predicate.
     * @param offset the offset
     * @return the solid predicate
     * @since 3.0.0
     * @deprecated Deprecated in Minecraft.
     */
    @Deprecated
    @AsOf("3.0.0")
    static SolidPredicate of(BlockVector offset) {
        return WIRE.construct(offset);
    }

    /**
     * Creates a new solid predicate with a zero offset.
     * @return the solid predicate
     * @since 3.0.0
     * @deprecated Deprecated in Minecraft.
     */
    @Deprecated
    @AsOf("3.0.0")
    static SolidPredicate of() {
        return of(new BlockVector(0, 0, 0));
    }

    /**
     * Creates a new builder.
     * @return a new builder
     * @since 3.0.0
     * @deprecated Deprecated in Minecraft.
     */
    @Deprecated
    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    final class Builder {
        private BlockVector offset = new BlockVector(0, 0, 0);

        public Builder() {}

        public Builder(SolidPredicate predicate) {
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
         * Builds the solid predicate.
         * @return the solid predicate
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public SolidPredicate build() {
            return of(offset);
        }
    }
}