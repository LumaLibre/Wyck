package dev.wyck.worldgen.feature.configurations;

import dev.wyck.util.WeightedList;
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
        net.minecraft.util.random.WeightedList.Builder<net.minecraft.core.Holder<net.minecraft.world.level.levelgen.placement.PlacedFeature>> builder =
            net.minecraft.util.random.WeightedList.builder();

        for (WeightedList.Weighted<PlacedFeature> entry : features.unwrap()) {
            net.minecraft.core.Holder<net.minecraft.world.level.levelgen.placement.PlacedFeature> handle = entry.value().asHandle();
            builder.add(handle, entry.weight());
        }

        return new net.minecraft.world.level.levelgen.feature.configurations.WeightedRandomFeatureConfiguration(builder.build());
    }
}
