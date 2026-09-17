package dev.wyck.worldgen.feature.configurations;

import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record SimpleBlockFeatureImpl(
    @Override BlockStateProvider toPlace,
    @Override boolean scheduleTick
) implements SimpleBlockFeature {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.SimpleBlockFeature(
            toPlace.asHandle(),
            scheduleTick
        );
    }
}