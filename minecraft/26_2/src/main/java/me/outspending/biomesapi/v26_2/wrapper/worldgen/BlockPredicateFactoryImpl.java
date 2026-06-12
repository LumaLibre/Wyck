package me.outspending.biomesapi.v26_2.wrapper.worldgen;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.wrapper.worldgen.BlockPredicate;
import org.bukkit.craftbukkit.block.CraftBiome;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@WireFactory
@ApiStatus.Internal
public final class BlockPredicateFactoryImpl extends me.outspending.biomesapi.wrapper.worldgen.BlockPredicateFactoryImpl {

    @Override
    public Object toNms(BlockPredicate predicate) {
        if (predicate instanceof BlockPredicate.MatchingBiomes(List<org.bukkit.block.Biome> biomes)) {
            List<net.minecraft.world.level.biome.Biome> nmsBiomes = biomes.stream()
                .map(it -> ((CraftBiome) it).getHandle())
                .toList();
            // TODO: implement this when paperweight-userdev is published
            throw new UnsupportedOperationException("26.2 paperweight-userdev not published yet");
        }
        return super.toNms(predicate);
    }
}
