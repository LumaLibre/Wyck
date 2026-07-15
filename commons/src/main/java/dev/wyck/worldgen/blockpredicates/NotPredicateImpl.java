package dev.wyck.worldgen.blockpredicates;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record NotPredicateImpl(@Override BlockPredicate predicate) implements NotPredicate {
    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.not(
            this.predicate.asHandle()
        );
    }
}
