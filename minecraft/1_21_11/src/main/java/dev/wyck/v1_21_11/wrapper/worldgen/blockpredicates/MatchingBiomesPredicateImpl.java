package dev.wyck.v1_21_11.wrapper.worldgen.blockpredicates;

import dev.wyck.model.biome.Biome;
import dev.wyck.wrapper.worldgen.blockpredicates.MatchingBiomesPredicate;
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