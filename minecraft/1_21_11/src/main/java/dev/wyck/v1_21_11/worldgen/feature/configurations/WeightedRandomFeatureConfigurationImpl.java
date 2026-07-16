package dev.wyck.v1_21_11.worldgen.feature.configurations;

import dev.wyck.util.WeightedList;
import dev.wyck.worldgen.feature.configurations.WeightedRandomFeatureConfiguration;
import dev.wyck.worldgen.placement.PlacedFeature;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record WeightedRandomFeatureConfigurationImpl(
    @Override WeightedList<PlacedFeature> features
) implements WeightedRandomFeatureConfiguration {
    @Override
    public Object toMinecraft() {
        throw new UnsupportedOperationException("Doesn't exist in this version of Minecraft");
    }
}
