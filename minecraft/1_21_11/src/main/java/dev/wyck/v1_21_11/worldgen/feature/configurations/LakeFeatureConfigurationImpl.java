package dev.wyck.v1_21_11.worldgen.feature.configurations;

import dev.wyck.worldgen.blockpredicates.BlockPredicate;
import dev.wyck.worldgen.feature.configurations.LakeFeatureConfiguration;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record LakeFeatureConfigurationImpl(
    @Override BlockStateProvider fluid,
    @Override BlockStateProvider barrier,
    @Override BlockPredicate canPlaceFeature,
    @Override BlockPredicate canReplaceWithAirOrFluid,
    @Override BlockPredicate canReplaceWithBarrier
) implements LakeFeatureConfiguration {

    @Override
    @SuppressWarnings("deprecation")
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.LakeFeature.Configuration(
            fluid.asHandle(),
            barrier.asHandle()
        );
    }
}
