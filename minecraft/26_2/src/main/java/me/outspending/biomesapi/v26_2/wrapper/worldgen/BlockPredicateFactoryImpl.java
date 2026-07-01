package me.outspending.biomesapi.v26_2.wrapper.worldgen;

import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.registry.bootstrap.util.BootstrapSafeMinecraftRegistries;
import me.outspending.biomesapi.util.internal.InternalReflectUtil;
import me.outspending.biomesapi.wrapper.worldgen.BlockPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.blockpredicates.MatchingBiomesPredicate;
import org.bukkit.craftbukkit.block.CraftBiome;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

@NullMarked
@WireFactory
@ApiStatus.Internal
public final class BlockPredicateFactoryImpl extends me.outspending.biomesapi.wrapper.worldgen.BlockPredicateFactoryImpl {

    @Override
    protected Object matchingBiomes(BlockPredicate.MatchingBiomes matching) {
        Registry<Biome> registry = BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.BIOME);
        List<Holder<Biome>> holders = new ArrayList<>(matching.biomes().size());
        for (org.bukkit.block.Biome biome : matching.biomes()) {
            holders.add(registry.wrapAsHolder(((CraftBiome) biome).getHandle()));
        }
        return new MatchingBiomesPredicate(HolderSet.direct(holders));
    }

    @Override
    protected BlockPredicate fromUnsupported(net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate predicate) {
        if (predicate instanceof MatchingBiomesPredicate matching) {
            HolderSet<Biome> biomes = InternalReflectUtil.getFieldValue(matching, "biomes");
            List<org.bukkit.block.Biome> wrapped = new ArrayList<>(biomes.size());
            for (Holder<Biome> holder : biomes) {
                wrapped.add(CraftBiome.minecraftHolderToBukkit(holder));
            }
            return new BlockPredicate.MatchingBiomes(wrapped);
        }
        return super.fromUnsupported(predicate);
    }
}