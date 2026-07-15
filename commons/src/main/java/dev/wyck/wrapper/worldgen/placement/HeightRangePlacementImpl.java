package dev.wyck.wrapper.worldgen.placement;

import dev.wyck.wrapper.worldgen.heightproviders.HeightProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record HeightRangePlacementImpl(@Override HeightProvider height) implements HeightRangePlacement {
    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.placement.HeightRangePlacement.of(
            height.asHandle()
        );
    }
}
