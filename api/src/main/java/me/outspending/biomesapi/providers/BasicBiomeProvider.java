package me.outspending.biomesapi.providers;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.factory.Lazy;
import org.bukkit.block.Biome;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A biome provider that always returns a single biome.
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@AsOf("2.3.0")
@ApiStatus.Experimental
public final class BasicBiomeProvider extends CustomBiomeProvider {

    private final Lazy<Biome> biome;

    @AsOf("2.3.0")
    public BasicBiomeProvider(CustomBiome customBiome) {
        this.biome = Lazy.of(customBiome::toBukkitBiome);
        super(List.of(customBiome));
    }

    @Override
    @AsOf("2.3.0")
    public @NotNull Biome getBiome(@NotNull WorldInfo worldInfo, int x, int y, int z) {
        return biome.get();
    }

    @AsOf("2.3.0")
    public static @NotNull BasicBiomeProvider of(CustomBiome biome) {
        return new BasicBiomeProvider(biome);
    }
}
