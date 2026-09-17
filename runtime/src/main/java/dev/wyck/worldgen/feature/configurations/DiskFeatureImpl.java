package dev.wyck.worldgen.feature.configurations;

import dev.wyck.worldgen.blockpredicates.BlockPredicate;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record DiskFeatureImpl(
    @Override BlockStateProvider stateProvider,
    @Override BlockPredicate target,
    @Override IntProvider radius,
    @Override int halfHeight
) implements DiskFeature {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.DiskFeature(
            stateProvider.asHandle(),
            target.asHandle(),
            radius.asHandle(),
            halfHeight
        );
    }
}