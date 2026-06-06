package me.outspending.biomesapi.renderer.packet.data;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.biome.RegisteredBiomes;
import me.outspending.biomesapi.misc.ChunkLocation;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.renderer.packet.PacketHandler;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.function.BiPredicate;

/**
 * A class representing a "phony" or fake custom biome designed for sending via packets.
 * The {@link CustomBiome} must exist and be registered, but phony custom biomes are never
 * actually set to any chunks and only exist for packet sending purposes.
 *
 * <p>Two conditions gate whether this biome applies to a chunk:
 * <ul>
 *   <li>{@link #conditional()}  the cheap, biome-independent spatial gate
 *       ({@code (player, chunkLocation)}). Evaluated <em>before</em> the chunk is decoded, so put
 *       world/permission/region checks here.</li>
 *   <li>{@link #biomeCondition()}  optional, biome-aware refinement
 *       ({@code (player, snapshotChunkData)}). Evaluated <em>after</em> decoding, only for chunks
 *       that passed the spatial gate. May be {@code null}, in which case it always passes.</li>
 * </ul>
 *
 * @author Jsinco
 * @since 0.0.6
 * @version 2.2.0
 */
@NullMarked
@AsOf("2.2.0")
public record PhonyCustomBiome(
    BiomeResourceKey biomeResourceKey,
    BiPredicate<Player, ChunkLocation> conditional,
    @Nullable BiPredicate<Player, SnapshotChunkData> biomeCondition,
    PacketHandler.Priority priority
) {

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PhonyCustomBiome that = (PhonyCustomBiome) obj;
        return Objects.equals(biomeResourceKey, that.biomeResourceKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(biomeResourceKey);
    }

    public CustomBiome toCustomBiome() {
        return RegisteredBiomes.getOrThrow(biomeResourceKey);
    }

    public CustomBiome customBiome() {
        return toCustomBiome();
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * A builder for creating PhonyCustomBiome instances.
     * @version 2.2.0
     * @since 0.0.6
     * @author Jsinco
     */
    @AsOf("0.0.10")
    public static class Builder {
        private BiomeResourceKey biomeResourceKey;
        private BiPredicate<Player, ChunkLocation> conditional = (player, chunkLocation) -> true;
        private BiPredicate<Player, SnapshotChunkData> biomeCondition = null;
        private PacketHandler.Priority priority = PacketHandler.Priority.NORMAL;

        @AsOf("0.0.6")
        public Builder setCustomBiome(CustomBiome customBiome) {
            this.biomeResourceKey = customBiome.getResourceKey();
            return this;
        }

        @AsOf("0.0.10")
        public Builder setCustomBiome(BiomeResourceKey biomeResourceKey) {
            this.biomeResourceKey = biomeResourceKey;
            return this;
        }

        @AsOf("0.0.6")
        public Builder setConditional(BiPredicate<Player, ChunkLocation> conditional) {
            this.conditional = conditional;
            return this;
        }

        @AsOf("2.2.0")
        public Builder setBiomeCondition(@Nullable BiPredicate<Player, SnapshotChunkData> biomeCondition) {
            this.biomeCondition = biomeCondition;
            return this;
        }

        @AsOf("0.0.6")
        public Builder setPriority(PacketHandler.Priority priority) {
            this.priority = priority;
            return this;
        }

        @AsOf("0.0.6")
        public PhonyCustomBiome build() {
            Preconditions.checkNotNull(biomeResourceKey, "customBiome cannot be null");
            return new PhonyCustomBiome(biomeResourceKey, conditional, biomeCondition, priority);
        }
    }
}