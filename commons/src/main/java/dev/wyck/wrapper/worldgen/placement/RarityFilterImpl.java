package dev.wyck.wrapper.worldgen.placement;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record RarityFilterImpl(@Override int chance) implements RarityFilter {
    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.placement.RarityFilter.onAverageOnceEvery(chance);
    }
}