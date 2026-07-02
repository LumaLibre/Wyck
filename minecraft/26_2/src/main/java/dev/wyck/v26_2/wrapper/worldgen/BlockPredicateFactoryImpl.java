package dev.wyck.v26_2.wrapper.worldgen;

import dev.wyck.annotations.WireFactory;
import dev.wyck.registry.bootstrap.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.wrapper.worldgen.BlockPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.attribute.EnvironmentAttributes;
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
public final class BlockPredicateFactoryImpl extends dev.wyck.wrapper.worldgen.BlockPredicateFactoryImpl {
    @Override
    protected Object matchingBiomes(BlockPredicate.MatchingBiomes matching) {
        EnvironmentAttributes
        Registry<Biome> registry = BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.BIOME);
        List<Holder<Biome>> holders = new ArrayList<>(matching.biomes().size());
        for (org.bukkit.block.Biome biome : matching.biomes()) {
            holders.add(registry.wrapAsHolder(((CraftBiome) biome).getHandle()));
        }
        return new MatchingBiomesPredicate(HolderSet.direct(holders));
    }
}
