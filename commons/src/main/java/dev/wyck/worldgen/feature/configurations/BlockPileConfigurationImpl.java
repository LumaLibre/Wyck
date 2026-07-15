package dev.wyck.worldgen.feature.configurations;

import dev.wyck.worldgen.feature.configurations.BlockPileConfiguration;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record BlockPileConfigurationImpl(@Override BlockStateProvider stateProvider) implements BlockPileConfiguration {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration(
            stateProvider.asHandle()
        );
    }
}
