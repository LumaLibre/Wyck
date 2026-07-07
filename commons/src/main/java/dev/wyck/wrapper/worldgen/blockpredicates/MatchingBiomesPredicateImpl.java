package dev.wyck.wrapper.worldgen.blockpredicates;

import dev.wyck.model.biome.Biome;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

@NullMarked
@ApiStatus.Internal
public record MatchingBiomesPredicateImpl(
    @Override List<Biome> biomes
) implements MatchingBiomesPredicate {
    @Override
    public Object toMinecraft() {
        List<net.minecraft.core.Holder<net.minecraft.world.level.biome.Biome>> holders = new ArrayList<>(biomes.size());

        for (Biome biome : biomes) {
            net.minecraft.world.level.biome.Biome handle = biome.asHandle();
            holders.add(net.minecraft.core.Holder.direct(handle));
        }

        return net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.matchesBiomes(
            net.minecraft.core.HolderSet.direct(holders)
        );
    }
}