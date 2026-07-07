package dev.wyck.wrapper.worldgen.placement;

import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record RandomOffsetPlacementImpl(
    @Override IntProvider xzSpread,
    @Override IntProvider ySpread
) implements RandomOffsetPlacement {
    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement.of(
            xzSpread.asHandle(),
            ySpread.asHandle()
        );
    }
}