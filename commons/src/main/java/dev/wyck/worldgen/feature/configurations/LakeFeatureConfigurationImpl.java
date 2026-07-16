package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.worldgen.blockpredicates.BlockPredicate;
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

    public LakeFeatureConfigurationImpl {
        Preconditions.checkNotNull(canPlaceFeature, "canPlaceFeature cannot be null");
        Preconditions.checkNotNull(canReplaceWithAirOrFluid, "canReplaceWithAirOrFluid cannot be null");
        Preconditions.checkNotNull(canReplaceWithBarrier, "canReplaceWithBarrier cannot be null");
    }

    @Override
    @SuppressWarnings("deprecation")
    public Object toMinecraft() {
        // note: this is probably deprecated because water lakes were replaced by
        // aquifers in 1.18. I'm guessing it's deprecated internally because of bad pipelining,
        // but it's still used for lava lakes, so im exposing w/o deprecation
        return new net.minecraft.world.level.levelgen.feature.LakeFeature.Configuration(
            fluid.asHandle(),
            barrier.asHandle(),
            canPlaceFeature.asHandle(),
            canReplaceWithAirOrFluid.asHandle(),
            canReplaceWithBarrier.asHandle()
        );
    }
}
