package dev.wyck.worldgen.feature.configurations;

import dev.wyck.worldgen.feature.treedecorators.TreeDecorator;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@ApiStatus.Internal
public record FallenTreeConfigurationImpl(
    @Override BlockStateProvider trunkProvider,
    @Override IntProvider logLength,
    @Override List<TreeDecorator> stumpDecorators,
    @Override List<TreeDecorator> logDecorators
) implements FallenTreeConfiguration {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.configurations.FallenTreeConfiguration.FallenTreeConfigurationBuilder(
            trunkProvider.asHandle(),
            logLength.asHandle()
        )
            .stumpDecorators(stumpDecorators.stream().map(TreeDecorator::<net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator>asHandle).toList())
            .logDecorators(logDecorators.stream().map(TreeDecorator::<net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator>asHandle).toList())
            .build();
    }
}