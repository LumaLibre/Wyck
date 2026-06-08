package me.outspending.biomesapi.wrapper.worldgen;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.wrapper.worldgen.carver.ConfiguredWorldCarver;
import me.outspending.biomesapi.wrapper.worldgen.placement.PlacedFeature;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@NullMarked
@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
public final class BiomeGenerationSettingsImpl implements BiomeGenerationSettings {

    private final List<ConfiguredWorldCarver> carvers;
    private final Map<GenerationStep, List<PlacedFeature>> features;

    @AsOf("2.3.0")
    public BiomeGenerationSettingsImpl(List<ConfiguredWorldCarver> carvers, Map<GenerationStep, List<PlacedFeature>> features) {
        this.carvers = List.copyOf(carvers);

        // deep-copy the per-step lists so the map and its values are both immutable
        Map<GenerationStep, List<PlacedFeature>> copied = new EnumMap<>(GenerationStep.class);
        for (Map.Entry<GenerationStep, List<PlacedFeature>> entry : features.entrySet()) {
            copied.put(entry.getKey(), List.copyOf(entry.getValue()));
        }
        this.features = Map.copyOf(copied);
    }

    @Override
    public List<ConfiguredWorldCarver> carvers() {
        return this.carvers;
    }

    @Override
    public Map<GenerationStep, List<PlacedFeature>> features() {
        return this.features;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object toMinecraft() {
        net.minecraft.world.level.biome.BiomeGenerationSettings.PlainBuilder builder =
                new net.minecraft.world.level.biome.BiomeGenerationSettings.PlainBuilder();

        for (ConfiguredWorldCarver carver : this.carvers) {
            net.minecraft.core.Holder<net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<?>> holder =
                    (net.minecraft.core.Holder<net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<?>>) carver.toMinecraft();
            builder.addCarver(holder);
        }

        for (Map.Entry<GenerationStep, List<PlacedFeature>> entry : this.features.entrySet()) {
            int stepIndex = entry.getKey().ordinal();
            for (PlacedFeature feature : entry.getValue()) {
                net.minecraft.core.Holder<net.minecraft.world.level.levelgen.placement.PlacedFeature> holder =
                        (net.minecraft.core.Holder<net.minecraft.world.level.levelgen.placement.PlacedFeature>) feature.toMinecraft();
                builder.addFeature(stepIndex, holder);
            }
        }

        return builder.build();
    }
}