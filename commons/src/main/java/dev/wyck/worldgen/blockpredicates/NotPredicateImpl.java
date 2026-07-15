package dev.wyck.worldgen.blockpredicates;

import dev.wyck.worldgen.blockpredicates.BlockPredicate;
import dev.wyck.worldgen.blockpredicates.NotPredicate;
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
