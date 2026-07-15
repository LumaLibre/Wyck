package dev.wyck.worldgen.feature.configurations;

import dev.wyck.worldgen.feature.configurations.ProbabilityFeatureConfiguration;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public class ProbabilityFeatureConfigurationImpl implements ProbabilityFeatureConfiguration {

    protected final float probability;

    public ProbabilityFeatureConfigurationImpl(float probability) {
        this.probability = probability;
    }

    @Override
    public float probability() {
        return this.probability;
    }

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration(
            this.probability
        );
    }
}