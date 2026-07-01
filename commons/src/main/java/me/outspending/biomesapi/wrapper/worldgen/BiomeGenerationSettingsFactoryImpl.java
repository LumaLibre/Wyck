package me.outspending.biomesapi.wrapper.worldgen;

import com.google.common.collect.Lists;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.wrapper.worldgen.carver.ConfiguredWorldCarver;
import me.outspending.biomesapi.wrapper.worldgen.placement.PlacedFeature;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@NullMarked
@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
public final class BiomeGenerationSettingsFactoryImpl implements BiomeGenerationSettings.Factory {

    @Override
    public BiomeGenerationSettings create(List<ConfiguredWorldCarver> carvers, Map<GenerationStep, List<PlacedFeature>> features) {
        return new BiomeGenerationSettingsImpl(carvers, features);
    }

    @Override
    public BiomeGenerationSettings fromMinecraft(Object nms) {
        net.minecraft.world.level.biome.BiomeGenerationSettings settings = (net.minecraft.world.level.biome.BiomeGenerationSettings) nms;

        List<Holder<net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<?>>> nmsCarvers = Lists.newArrayList(settings.getCarvers());
        List<HolderSet<net.minecraft.world.level.levelgen.placement.PlacedFeature>> nmsFeatures = settings.features();

        List<ConfiguredWorldCarver> carvers = new ArrayList<>();
        for (Holder<net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<?>> holder : nmsCarvers) {
            carvers.add(ConfiguredWorldCarver.fromMinecraft(holder));
        }

        Map<GenerationStep, List<PlacedFeature>> features = new EnumMap<>(GenerationStep.class);
        GenerationStep[] steps = GenerationStep.values();
        for (int i = 0; i < nmsFeatures.size() && i < steps.length; i++) {
            List<PlacedFeature> placed = new ArrayList<>();
            for (Holder<net.minecraft.world.level.levelgen.placement.PlacedFeature> holder : nmsFeatures.get(i)) {
                placed.add(PlacedFeature.fromMinecraft(holder));
            }
            features.put(steps[i], placed);
        }

        return new BiomeGenerationSettingsImpl(carvers, features);
    }
}