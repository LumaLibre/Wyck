package dev.wyck.worldgen.feature.configurations;

import dev.wyck.worldgen.placement.PlacedFeature;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

@NullMarked
@ApiStatus.Internal
public record RandomFeatureConfigurationImpl(
    @Override List<RandomFeatureConfiguration.WeightedPlacedFeature> features,
    @Override PlacedFeature defaultFeature
) implements RandomFeatureConfiguration {
    @Override
    public Object toMinecraft() {
        List<net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature> weighted = new ArrayList<>(features.size());
        for (RandomFeatureConfiguration.WeightedPlacedFeature entry : features) {
            net.minecraft.core.Holder<net.minecraft.world.level.levelgen.placement.PlacedFeature> handle = entry.feature().asHandle();
            weighted.add(new net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature(handle, entry.chance()));
        }

        net.minecraft.core.Holder<net.minecraft.world.level.levelgen.placement.PlacedFeature> defaultHandle = defaultFeature.asHandle();
        return new net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration(weighted, defaultHandle);
    }
}