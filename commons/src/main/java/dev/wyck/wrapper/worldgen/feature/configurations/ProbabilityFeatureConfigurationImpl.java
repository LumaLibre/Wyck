package dev.wyck.wrapper.worldgen.feature.configurations;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record ProbabilityFeatureConfigurationImpl(@Override float probability) implements ProbabilityFeatureConfiguration {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration(probability);
    }
}