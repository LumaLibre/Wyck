package dev.wyck.worldgen.biome;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

@NullMarked
@ApiStatus.Internal
public record MultiNoiseBiomeSourceImpl(
    @Override List<Entry> biomes,
    @Override boolean isTransient
) implements MultiNoiseBiomeSource {
    @Override
    public Object toMinecraft() {
        net.minecraft.core.Registry<net.minecraft.world.level.biome.Biome> biomesRegistry = BootstrapSafeMinecraftRegistries.mappedRegistry(net.minecraft.core.registries.Registries.BIOME);

        List<Pair<net.minecraft.world.level.biome.Climate.ParameterPoint, net.minecraft.core.Holder<net.minecraft.world.level.biome.Biome>>> list = new ArrayList<>();
        for (Entry entry : this.biomes) {
            net.minecraft.resources.Identifier id = entry.biome().resourceKey().identifier();
            net.minecraft.core.Holder<net.minecraft.world.level.biome.Biome> holder = biomesRegistry.getOrThrow(net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.BIOME, id));
            net.minecraft.world.level.biome.Climate.ParameterPoint point = entry.climatePoint().asHandle();
            list.add(Pair.of(point, holder));
        }

        net.minecraft.world.level.biome.Climate.ParameterList<net.minecraft.core.Holder<net.minecraft.world.level.biome.Biome>> parameterList = new net.minecraft.world.level.biome.Climate.ParameterList<>(list);

        if (this.isTransient) {
            return TransientMultiNoiseBiomeSource.create(Either.left(parameterList), parameterList);
        } else {
            return net.minecraft.world.level.biome.MultiNoiseBiomeSource.createFromList(parameterList);
        }
    }
}
