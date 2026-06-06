package me.outspending.biomesapi.wrapper.worldgen;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.wrapper.worldgen.carver.ConfiguredWorldCarver;
import me.outspending.biomesapi.wrapper.worldgen.placement.PlacedFeature;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

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
}