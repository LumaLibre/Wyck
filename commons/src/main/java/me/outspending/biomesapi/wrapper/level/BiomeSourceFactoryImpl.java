package me.outspending.biomesapi.wrapper.level;

import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.keys.ResourceKey;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@WireFactory
@ApiStatus.Internal
public class BiomeSourceFactoryImpl implements BiomeSource.Factory {

    @Override
    public BiomeSource fixed(ResourceKey biome) {
        return new FixedBiomeSourceImpl(biome);
    }

    @Override
    public BiomeSource multiNoise(List<BiomeSource.MultiNoiseEntry> entries) {
        return new MultiNoiseBiomeSourceImpl(entries);
    }

    @Override
    public BiomeSource preset(ResourceKey preset) {
        return new PresetBiomeSourceImpl(preset);
    }

    // TODO: Do when decoders are available
//    @Override
//    public List<BiomeSource.MultiNoiseEntry> presetEntries(ResourceKey preset) {
//        Holder<MultiNoiseBiomeSourceParameterList> holder = PresetBiomeSourceImpl.resolvePreset(preset);
//        return flatten(holder.value().parameters());
//    }
//
//    private static List<BiomeSource.MultiNoiseEntry> flatten(Climate.ParameterList<Holder<Biome>> parameterList) {
//        List<BiomeSource.MultiNoiseEntry> entries = new ArrayList<>();
//        for (Pair<Climate.ParameterPoint, Holder<Biome>> pair : parameterList.values()) {
//            Holder<Biome> biomeHolder = pair.getSecond();
//            Identifier identifier = biomeHolder.unwrapKey().orElseThrow().identifier();
//            ResourceKey biomeKey = ResourceKey.of(identifier.getNamespace(), identifier.getPath());
//            ClimatePoint climatePoint = ClimatePoint.fromMinecraft(pair.getFirst());
//            entries.add(new BiomeSource.MultiNoiseEntry(biomeKey, climatePoint));
//        }
//        return entries;
//    }
}