package dev.wyck.worldgen.blockpredicates;

import dev.wyck.wrapper.Wrapper;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@ApiStatus.Internal
public final class AnyOfPredicateImpl extends CombiningPredicateImpl implements AnyOfPredicate {

    public AnyOfPredicateImpl(List<BlockPredicate> predicates) {
        super(predicates);
    }

    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.anyOf(
            this.predicates.stream().map(Wrapper::<net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate>asHandle).toList()
        );
    }
}
