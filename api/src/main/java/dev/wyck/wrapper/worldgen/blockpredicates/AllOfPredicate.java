package dev.wyck.wrapper.worldgen.blockpredicates;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

/**
 * Combined multiple block predicates. Matches if all specified block predicates match.
 *
 * @see <a href="https://minecraft.wiki/w/Block_predicate#all_of">Block predicate (all of)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface AllOfPredicate extends CombiningPredicate {

    @ApiStatus.Internal
    ConstructWireProvider<AllOfPredicate> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.blockpredicates.AllOfPredicateImpl");

    /**
     * Converts this object to a builder.
     * @return the builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new AllOfPredicate with the specified predicates.
     * @param predicates the predicates to combine
     * @return the AllOfPredicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static AllOfPredicate of(List<BlockPredicate> predicates) {
        return WIRE.construct(predicates);
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
     * Builder for {@link AllOfPredicate}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends CombiningPredicateBuilder<AllOfPredicate, Builder> {
        public Builder() {}

        public Builder(AllOfPredicate predicate) {
            super(predicate);
        }

        @Override
        protected AllOfPredicate create() {
            return of(predicates);
        }
    }
}
