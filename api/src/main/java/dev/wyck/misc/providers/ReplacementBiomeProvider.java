package dev.wyck.misc.providers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.model.biome.AbstractBiome;
import org.bukkit.block.Biome;
import org.bukkit.generator.WorldInfo;
import org.jspecify.annotations.NullMarked;

import java.util.HashMap;
import java.util.Map;

/**
 * A biome provider that replaces biomes with Wyck's custom biomes.
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public final class ReplacementBiomeProvider extends WyckBiomeProvider {

    private final Map<Biome, AbstractBiome> replacements;

    @AsOf("2.3.0")
    public ReplacementBiomeProvider(Map<Biome, AbstractBiome> replacements) {
        this.replacements = replacements;
        super(replacements.values());
    }

    @Override
    @AsOf("2.3.0")
    public Biome getBiome(WorldInfo worldInfo, int x, int y, int z) {
        Biome vanilla = worldInfo.vanillaBiomeProvider().getBiome(worldInfo, x, y, z);
        AbstractBiome replacement = replacements.get(vanilla);
        return replacement != null ? replacement.toBukkitBiome() : vanilla;
    }

    @AsOf("2.3.0")
    public static Builder builder() {
        return new Builder();
    }

    /**
     * A builder class for creating instances of ReplacementBiomeProvider.
     * @since 2.3.0
     * @version 2.3.0
     * @author Jsinco
     */
    @AsOf("2.3.0")
    public static class Builder {
        private final Map<Biome, AbstractBiome> replacements = new HashMap<>();

        @AsOf("2.3.0")
        public Builder replace(Biome original, AbstractBiome replacement) {
            Preconditions.checkNotNull(original, "original cannot be null");
            Preconditions.checkNotNull(replacement, "replacement cannot be null");
            replacements.put(original, replacement);
            return this;
        }

        @AsOf("2.3.0")
        public ReplacementBiomeProvider build() {
            return new ReplacementBiomeProvider(replacements);
        }
    }
}
