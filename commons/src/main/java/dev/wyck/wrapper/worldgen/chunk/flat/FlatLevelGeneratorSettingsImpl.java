package dev.wyck.wrapper.worldgen.chunk.flat;

import dev.wyck.model.biome.Biome;
import dev.wyck.wrapper.internal.Wrapper;
import dev.wyck.wrapper.worldgen.placement.PlacedFeature;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record FlatLevelGeneratorSettingsImpl(
    @Override List<FlatLayerInfo> layers,
    @Override boolean decoration,
    @Override Biome biome,
    @Override Biome fallbackBiome,
    @Override List<PlacedFeature> lakes
) implements FlatLevelGeneratorSettings {

    @Override
    public Object toMinecraft() {
        // Underlying impl is awkward, so we have to do it ourselves.
        net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings gen = new net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings(
            Optional.empty(), // can't wrap
            net.minecraft.core.Holder.direct(biome.asHandle()),
            lakes.stream().map(it -> net.minecraft.core.Holder.direct(it.<net.minecraft.world.level.levelgen.placement.PlacedFeature>asHandle())).toList()
        );

        if (!lakes.isEmpty()) {
            gen.setAddLakes();
        }

        if (decoration) {
            gen.setDecoration();
        }

        gen.getLayersInfo().addAll(layers.stream().map(Wrapper::<net.minecraft.world.level.levelgen.flat.FlatLayerInfo>asHandle).toList());
        gen.updateLayers();
        return gen;
    }

}
