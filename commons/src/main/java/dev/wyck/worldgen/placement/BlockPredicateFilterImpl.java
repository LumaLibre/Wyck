package dev.wyck.worldgen.placement;

import dev.wyck.worldgen.blockpredicates.BlockPredicate;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record BlockPredicateFilterImpl(@Override BlockPredicate predicate) implements BlockPredicateFilter {
    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.placement.BlockPredicateFilter.forPredicate(
            predicate.asHandle()
        );
    }
}
