package me.outspending.biomesapi.providers;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.biome.CustomBiome;
import org.bukkit.block.Biome;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * A biome provider that replaces biomes with BiomesAPI's custom biomes.
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@AsOf("2.3.0")
@ApiStatus.Experimental
public final class ReplacementBiomeProvider extends CustomBiomeProvider {

    private final Map<Biome, CustomBiome> replacements;

    @AsOf("2.3.0")
    public ReplacementBiomeProvider(@NotNull Map<Biome, CustomBiome> replacements) {
        this.replacements = replacements;
        super(replacements.values());
    }

    @Override
    @AsOf("2.3.0")
    public @NotNull Biome getBiome(@NotNull WorldInfo worldInfo, int x, int y, int z) {
        Biome vanilla = worldInfo.vanillaBiomeProvider().getBiome(worldInfo, x, y, z);
        CustomBiome replacement = replacements.get(vanilla);
        return replacement != null ? replacement.toBukkitBiome() : vanilla;
    }

    @AsOf("2.3.0")
    public static @NotNull Builder builder() {
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
        private final Map<Biome, CustomBiome> replacements = new HashMap<>();

        @AsOf("2.3.0")
        public @NotNull Builder replace(@NotNull Biome original, @NotNull CustomBiome replacement) {
            Preconditions.checkNotNull(original, "original cannot be null");
            Preconditions.checkNotNull(replacement, "replacement cannot be null");
            replacements.put(original, replacement);
            return this;
        }

        @AsOf("2.3.0")
        public @NotNull ReplacementBiomeProvider build() {
            return new ReplacementBiomeProvider(replacements);
        }
    }
}
