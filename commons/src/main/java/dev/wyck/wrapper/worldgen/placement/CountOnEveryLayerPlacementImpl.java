package dev.wyck.wrapper.worldgen.placement;

import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
@SuppressWarnings("deprecation")
public record CountOnEveryLayerPlacementImpl(@Override IntProvider count) implements CountOnEveryLayerPlacement {
    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.placement.CountOnEveryLayerPlacement.of(
            count.asHandle()
        );
    }
}
