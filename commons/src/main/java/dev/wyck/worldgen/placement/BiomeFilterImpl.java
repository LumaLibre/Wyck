package dev.wyck.worldgen.placement;

import dev.wyck.worldgen.placement.BiomeFilter;
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
