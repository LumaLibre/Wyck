package dev.wyck.worldgen.feature.configurations;

import dev.wyck.worldgen.blockpredicates.BlockPredicate;
import dev.wyck.worldgen.feature.configurations.HugeMushroomFeatureConfiguration;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
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