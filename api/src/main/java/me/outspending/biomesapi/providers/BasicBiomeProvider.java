package me.outspending.biomesapi.providers;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.biome.AbstractBiome;
import me.outspending.biomesapi.util.Lazy;
import org.bukkit.block.Biome;
import org.bukkit.generator.WorldInfo;
import org.jspecify.annotations.NullMarked;

import java.util.List;

/**
 * A biome provider that always returns a single biome.
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public final class BasicBiomeProvider extends BiomesAPIBiomeProvider {

    private final Lazy<Biome> biome;

    @AsOf("2.3.0")
    public BasicBiomeProvider(AbstractBiome abstractBiome) {
        Preconditions.checkNotNull(abstractBiome, "abstractBiome cannot be null");
        this.biome = Lazy.of(abstractBiome::toBukkitBiome);
        super(List.of(abstractBiome));
    }

    @Override
    @AsOf("2.3.0")
    public Biome getBiome(WorldInfo worldInfo, int x, int y, int z) {
        return biome.get();
    }

    @AsOf("2.3.0")
    public static BasicBiomeProvider of(AbstractBiome biome) {
        return new BasicBiomeProvider(biome);
    }
}
