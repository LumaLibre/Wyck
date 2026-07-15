package dev.wyck.worldgen.feature.configurations;

import dev.wyck.worldgen.feature.configurations.RandomBooleanFeatureConfiguration;
import dev.wyck.worldgen.placement.PlacedFeature;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record RandomBooleanFeatureConfigurationImpl(
    @Override PlacedFeature featureTrue,
    @Override PlacedFeature featureFalse
) implements RandomBooleanFeatureConfiguration {
    @Override
    public Object toMinecraft() {
        net.minecraft.core.Holder<net.minecraft.world.level.levelgen.placement.PlacedFeature> trueHandle = featureTrue.asHandle();
        net.minecraft.core.Holder<net.minecraft.world.level.levelgen.placement.PlacedFeature> falseHandle = featureFalse.asHandle();
        return new net.minecraft.world.level.levelgen.feature.configurations.RandomBooleanFeatureConfiguration(trueHandle, falseHandle);
    }
}