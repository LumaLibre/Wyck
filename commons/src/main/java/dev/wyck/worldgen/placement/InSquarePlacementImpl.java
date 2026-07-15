package dev.wyck.worldgen.placement;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record InSquarePlacementImpl() implements InSquarePlacement {
    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.placement.InSquarePlacement.spread();
    }
}