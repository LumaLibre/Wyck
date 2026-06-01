package me.outspending.biomesapi.providers;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A biome provider that uses a map of custom biomes to provide biomes.
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@AsOf("2.3.0")
@ApiStatus.Experimental
public abstract class CustomBiomeProvider extends BiomeProvider {

    protected final Map<BiomeResourceKey, CustomBiome> biomeMap;

    @AsOf("2.3.0")
    public CustomBiomeProvider(Collection<CustomBiome> biomes) {
        this.biomeMap = new HashMap<>();
        for (CustomBiome biome : biomes) {
            biomeMap.put(biome.getResourceKey(), biome);
        }
    }

    @Override
    @AsOf("2.3.0")
    public @NotNull List<Biome> getBiomes(@NotNull WorldInfo worldInfo) {
        return biomeMap.values().stream().map(CustomBiome::toBukkitBiome).toList();
    }

    @AsOf("2.3.0")
    public Map<BiomeResourceKey, CustomBiome> getBiomeMap() {
        return biomeMap;
    }
}