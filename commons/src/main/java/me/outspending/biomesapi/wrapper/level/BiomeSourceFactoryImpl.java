package me.outspending.biomesapi.wrapper.level;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.util.internal.InternalReflectUtil;
import me.outspending.biomesapi.wrapper.worldgen.climate.ClimatePoint;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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


    @Override
    public BiomeSource fromMinecraft(Object nms) {
        net.minecraft.world.level.biome.BiomeSource biomeSource = (net.minecraft.world.level.biome.BiomeSource) nms;
        Set<Holder<Biome>> possibleBiomes = biomeSource.possibleBiomes();

        if (nms instanceof net.minecraft.world.level.biome.FixedBiomeSource) {
            if (possibleBiomes.size() == 1) {
                Holder<Biome> holder = possibleBiomes.iterator().next();
                Identifier identifier = holder.unwrapKey().orElseThrow().identifier();
                ResourceKey biomeKey = ResourceKey.of(identifier.getNamespace(), identifier.getPath());
                return new FixedBiomeSourceImpl(biomeKey);
            }
            throw new IllegalStateException("FixedBiomeSource has more than one possible biome.");
        } else if (nms instanceof net.minecraft.world.level.biome.MultiNoiseBiomeSource multiNoise) {
            Either<Climate.ParameterList<Holder<Biome>>, Holder<MultiNoiseBiomeSourceParameterList>> parameters =
                InternalReflectUtil.getFieldValue(multiNoise, "parameters");

            Climate.ParameterList<Holder<Biome>> parameterList =
                parameters.map(direct -> direct, preset -> preset.value().parameters());

            List<BiomeSource.MultiNoiseEntry> entries = new ArrayList<>();
            for (Pair<Climate.ParameterPoint, Holder<Biome>> pair : parameterList.values()) {
                Holder<Biome> biomeHolder = pair.getSecond();
                Identifier identifier = biomeHolder.unwrapKey().orElseThrow().identifier();
                ResourceKey biomeKey = ResourceKey.of(identifier.getNamespace(), identifier.getPath());

                ClimatePoint climatePoint = ClimatePoint.fromMinecraft(pair.getFirst());

                entries.add(new BiomeSource.MultiNoiseEntry(biomeKey, climatePoint));
            }

            return new MultiNoiseBiomeSourceImpl(entries);
        }
        throw new IllegalArgumentException("I can't decode this! Unknown type: " + nms.getClass().getSimpleName());
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