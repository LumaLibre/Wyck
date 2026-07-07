package dev.wyck.wrapper.worldgen.feature.configurations;

import dev.wyck.wrapper.worldgen.blockpredicates.BlockPredicate;
import dev.wyck.wrapper.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record DiskConfigurationImpl(
    @Override BlockStateProvider stateProvider,
    @Override BlockPredicate target,
    @Override IntProvider radius,
    @Override int halfHeight
) implements DiskConfiguration {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration(
            stateProvider.asHandle(),
            target.asHandle(),
            radius.asHandle(),
            halfHeight
        );
    }
}