package dev.wyck.worldgen.feature.configurations;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record NoneFeatureConfigurationImpl() implements NoneFeatureConfiguration {
    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration.INSTANCE;
    }
}