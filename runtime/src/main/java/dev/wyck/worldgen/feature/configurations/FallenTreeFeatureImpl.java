package dev.wyck.worldgen.feature.configurations;

import dev.wyck.worldgen.feature.treedecorators.TreeDecorator;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@ApiStatus.Internal
public record FallenTreeFeatureImpl(
    @Override BlockStateProvider trunkProvider,
    @Override IntProvider logLength,
    @Override List<TreeDecorator> stumpDecorators,
    @Override List<TreeDecorator> logDecorators
) implements FallenTreeFeature {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.FallenTreeFeature(
            net.minecraft.core.Holder.direct(
                trunkProvider.<net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider>asHandle()
            ),
            logLength.asHandle(),
            stumpDecorators.stream().map(TreeDecorator::<net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator>asHandle).toList(),
            logDecorators.stream().map(TreeDecorator::<net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator>asHandle).toList()
        );
    }
}
