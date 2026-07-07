package dev.wyck.wrapper.worldgen.feature.configurations;

import dev.wyck.wrapper.worldgen.blockpredicates.BlockPredicate;
import dev.wyck.wrapper.worldgen.stateproviders.BlockStateProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record HugeMushroomFeatureConfigurationImpl(
    @Override BlockStateProvider capProvider,
    @Override BlockStateProvider stemProvider,
    @Override int foliageRadius,
    @Override BlockPredicate canPlaceOn
) implements HugeMushroomFeatureConfiguration {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration(
            capProvider.asHandle(),
            stemProvider.asHandle(),
            foliageRadius,
            canPlaceOn.asHandle()
        );
    }
}