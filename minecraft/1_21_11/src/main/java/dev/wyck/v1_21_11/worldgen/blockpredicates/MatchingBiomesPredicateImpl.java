package dev.wyck.v1_21_11.worldgen.blockpredicates;

import dev.wyck.biome.Biome;
import dev.wyck.worldgen.blockpredicates.MatchingBiomesPredicate;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@ApiStatus.Internal
public record MatchingBiomesPredicateImpl(
    @Override List<Biome> biomes
) implements MatchingBiomesPredicate {
    @Override
    public Object toMinecraft() {
        throw new UnsupportedOperationException("Doesn't exist in this version of Minecraft");
    }
}