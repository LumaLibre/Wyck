package dev.wyck.worldgen.feature.configurations;

import dev.wyck.worldgen.blockpredicates.BlockPredicate;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record HugeRedMushroomFeatureImpl(
    @Override BlockStateProvider capProvider,
    @Override BlockStateProvider stemProvider,
    @Override int foliageRadius,
    @Override BlockPredicate canPlaceOn
) implements HugeRedMushroomFeature {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.HugeRedMushroomFeature(
            net.minecraft.core.Holder.direct(
                capProvider.<net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider>asHandle()
            ),
            net.minecraft.core.Holder.direct(
                stemProvider.<net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider>asHandle()
            ),
            foliageRadius,
            canPlaceOn.asHandle()
        );
    }
}
