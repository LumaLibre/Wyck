package dev.wyck.wrapper.worldgen.placement;

import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record CountPlacementImpl(@Override IntProvider count) implements CountPlacement {
    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.placement.CountPlacement.of(
            count.<net.minecraft.util.valueproviders.IntProvider>asHandle()
        );
    }
}