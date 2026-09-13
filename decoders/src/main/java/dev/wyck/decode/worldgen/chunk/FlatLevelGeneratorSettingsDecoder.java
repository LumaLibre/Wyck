package dev.wyck.decode.worldgen.chunk;

import dev.wyck.biome.Biome;
import dev.wyck.decode.Decoders;
import dev.wyck.decode.FastReflection;
import dev.wyck.worldgen.chunk.flat.FlatLayerInfo;
import dev.wyck.worldgen.chunk.flat.FlatLevelGeneratorSettings;
import dev.wyck.worldgen.placement.PlacedFeature;
import dev.wyck.worldgen.structure.StructureSet;
import dev.wyck.wrapper.decode.Decodable;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@NullMarked
@ApiStatus.Internal
public final class FlatLevelGeneratorSettingsDecoder implements Decodable<FlatLevelGeneratorSettings, net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings> {
    @Override
    public FlatLevelGeneratorSettings decode(net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings settings) {
        Biome biome = Biome.reference(Decoders.referenceKey(settings.getBiome()));
        return FlatLevelGeneratorSettings.of(
            settings.getLayersInfo().stream().map(FlatLayerInfo::decode).toList(),
            FastReflection.read(settings, "decoration"),
            FastReflection.read(settings, "addLakes"),
            biome,
            biome,
            lakes(settings),
            structures(settings.structureOverrides())
        );
    }

    private static List<PlacedFeature> lakes(Object settings) {
        List<Holder<net.minecraft.world.level.levelgen.placement.PlacedFeature>> holders =
            FastReflection.read(settings, "lakes");
        return holders.stream().map(PlacedFeature::decode).toList();
    }

    // absent and empty are not the same thing here: absent means the registry's full set of
    // structure sets applies, empty means none do.
    private static @Nullable Set<StructureSet> structures(Optional<HolderSet<net.minecraft.world.level.levelgen.structure.StructureSet>> holders) {
        if (holders.isEmpty()) return null;
        return holders.get().stream()
            .map(StructureSet::decode)
            .collect(java.util.stream.Collectors.toCollection(LinkedHashSet::new));
    }
}
