package me.outspending.biomesapi.wrapper.level;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.registry.bootstrap.util.BootstrapSafeMinecraftRegistries;
import me.outspending.biomesapi.wrapper.worldgen.TransientMultiNoiseBiomeSource;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@NullMarked
@ApiStatus.Internal
public record MultiNoiseBiomeSourceImpl(@Override List<BiomeSource.MultiNoiseEntry> entries) implements BiomeSource {

    @Override
    public Object toMinecraft() {
        Registry<Biome> biomes = BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.BIOME);

        List<Pair<Climate.ParameterPoint, Holder<Biome>>> list = new ArrayList<>();
        for (BiomeSource.MultiNoiseEntry entry : this.entries) {
            Identifier id = (Identifier) entry.biome().resourceLocation();
            Holder<Biome> holder = biomes.getOrThrow(net.minecraft.resources.ResourceKey.create(Registries.BIOME, id));
            Climate.ParameterPoint point = (Climate.ParameterPoint) entry.climatePoint().toMinecraft();
            list.add(Pair.of(point, holder));
        }
        Climate.ParameterList<Holder<Biome>> parameterList = new Climate.ParameterList<>(list);
        return TransientMultiNoiseBiomeSource.create(Either.left(parameterList), parameterList);
    }

    @Override
    public @Nullable ResourceKey fixedBiome() {
        return null;
    }
}