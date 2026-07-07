package dev.wyck.v1_21_11.wrapper.worldgen.feature.configurations;

import dev.wyck.wrapper.worldgen.feature.configurations.CompositeFeatureConfiguration;
import dev.wyck.wrapper.worldgen.placement.PlacedFeature;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

@NullMarked
@ApiStatus.Internal
public record CompositeFeatureConfigurationImpl(@Override List<PlacedFeature> features) implements CompositeFeatureConfiguration {
    @Override
    public Object toMinecraft() {
        List<net.minecraft.core.Holder<net.minecraft.world.level.levelgen.placement.PlacedFeature>> holders = new ArrayList<>(features.size());
        for (PlacedFeature feature : features) {
            net.minecraft.core.Holder<net.minecraft.world.level.levelgen.placement.PlacedFeature> handle = feature.asHandle();
            holders.add(handle);
        }

        net.minecraft.core.HolderSet<net.minecraft.world.level.levelgen.placement.PlacedFeature> set = net.minecraft.core.HolderSet.direct(holders);
        return new net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration(set);
    }
}