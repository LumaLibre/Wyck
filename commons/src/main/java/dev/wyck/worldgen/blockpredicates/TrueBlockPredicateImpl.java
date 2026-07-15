package dev.wyck.worldgen.blockpredicates;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record TrueBlockPredicateImpl() implements TrueBlockPredicate {
    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.alwaysTrue();
    }
}
