package me.outspending.biomesapi.providers;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.biome.AbstractBiome;
import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.keys.ResourceKey;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

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
@NullMarked
@AsOf("2.3.1")
public abstract class BiomesAPIBiomeProvider extends BiomeProvider {

    protected final Map<ResourceKey, AbstractBiome> biomeMap;

    @AsOf("2.3.1")
    public BiomesAPIBiomeProvider(Collection<AbstractBiome> biomes) {
        this.biomeMap = new HashMap<>();
        for (AbstractBiome biome : biomes) {
            biomeMap.put(biome.getResourceKey(), biome);
        }
    }

    @Override
    @AsOf("2.3.0")
    public List<Biome> getBiomes(WorldInfo worldInfo) {
        return biomeMap.values().stream().map(AbstractBiome::toBukkitBiome).toList();
    }

    @AsOf("2.3.1")
    public Map<ResourceKey, AbstractBiome> getBiomeMap() {
        return biomeMap;
    }
}