package dev.wyck.wrapper.worldgen.placement;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record BiomeFilterImpl() implements BiomeFilter {
    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.placement.BiomeFilter.biome();
    }
}
