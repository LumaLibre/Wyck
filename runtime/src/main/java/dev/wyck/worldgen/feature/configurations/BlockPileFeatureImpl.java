package dev.wyck.worldgen.feature.configurations;

import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record BlockPileFeatureImpl(@Override BlockStateProvider stateProvider) implements BlockPileFeature {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.BlockPileFeature(
            stateProvider.asHandle()
        );
    }
}
