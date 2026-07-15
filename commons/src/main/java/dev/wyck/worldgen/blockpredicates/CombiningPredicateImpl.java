package dev.wyck.worldgen.blockpredicates;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@ApiStatus.Internal
public abstract class CombiningPredicateImpl implements CombiningPredicate {

    protected final List<BlockPredicate> predicates;

    public CombiningPredicateImpl(List<BlockPredicate> predicates) {
        this.predicates = predicates;
    }

    @Override
    public List<BlockPredicate> predicates() {
        return this.predicates;
    }
}
