package dev.wyck.worldgen.chunk.flat;

import dev.wyck.biome.Biome;
import dev.wyck.keys.ResourceKey;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.worldgen.placement.PlacedFeature;
import dev.wyck.wrapper.Wrapper;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@NullMarked
@ApiStatus.Internal
public record FlatLevelGeneratorSettingsImpl(
    @Override List<FlatLayerInfo> layers,
    @Override boolean decoration,
    @Override boolean addLakes,
    @Override Biome biome,
    @Override Biome fallbackBiome,
    @Override List<PlacedFeature> lakes,
    @Override Set<ResourceKey> structures
) implements FlatLevelGeneratorSettings {

    @Override
    public Object toMinecraft() {
        net.minecraft.core.HolderGetter<net.minecraft.world.level.levelgen.structure.StructureSet> structureSets = BootstrapSafeMinecraftRegistries.getter(net.minecraft.core.registries.Registries.STRUCTURE_SET);

        net.minecraft.core.HolderSet.Direct<net.minecraft.world.level.levelgen.structure.StructureSet> structuresHolder = net.minecraft.core.HolderSet.direct(structures.stream().map(it -> {
            return structureSets.getOrThrow(net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.STRUCTURE_SET, it.identifier()));
        }).collect(Collectors.toList()));

        // Underlying impl is awkward, so we have to do it ourselves.
        net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings gen = new net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings(
            structures.isEmpty() ? Optional.empty() : Optional.of(structuresHolder),
            net.minecraft.core.Holder.direct(biome.asHandle()),
            lakes.stream().map(it -> net.minecraft.core.Holder.direct(it.<net.minecraft.world.level.levelgen.placement.PlacedFeature>asHandle())).toList()
        );

        if (addLakes) {
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
