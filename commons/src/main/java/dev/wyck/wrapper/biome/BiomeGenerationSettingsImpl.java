package dev.wyck.wrapper.biome;

import dev.wyck.wrapper.worldgen.GenerationStep;
import dev.wyck.wrapper.worldgen.carver.ConfiguredWorldCarver;
import dev.wyck.wrapper.worldgen.placement.PlacedFeature;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@NullMarked
@ApiStatus.Internal
public record BiomeGenerationSettingsImpl(
    List<ConfiguredWorldCarver> carvers,
    Map<GenerationStep, List<PlacedFeature>> features
) implements BiomeGenerationSettings {

    public BiomeGenerationSettingsImpl {
        carvers = List.copyOf(carvers);
        // deep-copy the per-step lists so the map and its values are both immutable
        Map<GenerationStep, List<PlacedFeature>> copied = new EnumMap<>(GenerationStep.class);
        for (Map.Entry<GenerationStep, List<PlacedFeature>> entry : features.entrySet()) {
            copied.put(entry.getKey(), List.copyOf(entry.getValue()));
        }
        features = Map.copyOf(copied);
    }

    @Override
    public Object toMinecraft() {
        net.minecraft.world.level.biome.BiomeGenerationSettings.PlainBuilder builder = new net.minecraft.world.level.biome.BiomeGenerationSettings.PlainBuilder();

        for (ConfiguredWorldCarver carver : this.carvers) {
            builder.addCarver(carver.asHandle());
        }

        for (Map.Entry<GenerationStep, List<PlacedFeature>> entry : this.features.entrySet()) {
            net.minecraft.world.level.levelgen.GenerationStep.Decoration step = entry.getKey().toNms(net.minecraft.world.level.levelgen.GenerationStep.Decoration.class);
            for (PlacedFeature feature : entry.getValue()) {
                builder.addFeature(step, feature.asHandle());
            }
        }

        return builder.build();
    }
}