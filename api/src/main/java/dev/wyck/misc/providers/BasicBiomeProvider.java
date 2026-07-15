package dev.wyck.misc.providers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.biome.Biome;
import dev.wyck.util.Lazy;
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
public final class BasicBiomeProvider extends WyckBiomeProvider {

    private final Lazy<org.bukkit.block.Biome> biome;

    @AsOf("2.3.0")
    public BasicBiomeProvider(Biome biome) {
        Preconditions.checkNotNull(biome, "abstractBiome cannot be null");
        this.biome = Lazy.of(biome::bukkitBiome);
        super(List.of(biome));
    }

    @Override
    @AsOf("2.3.0")
    public org.bukkit.block.Biome getBiome(WorldInfo worldInfo, int x, int y, int z) {
        return biome.get();
    }

    @AsOf("2.3.0")
    public static BasicBiomeProvider of(Biome biome) {
        return new BasicBiomeProvider(biome);
    }
}
