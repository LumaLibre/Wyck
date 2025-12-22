package me.outspending.biomesapi.packet.data;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.packet.PacketHandler;
import org.bukkit.entity.Player;

import java.util.function.BiPredicate;

/**
 * A class representing a "phony" or fake custom biome designed for sending via packets.
 * The {@link CustomBiome} must exist and be registered, but phony custom biomes are never
 * actually set to any chunks and only exist for packet sending purposes.
 *
 * @author Jsinco
 * @since 0.0.4
 * @version 0.0.4
 */
@AsOf("0.0.4")
public record PhonyCustomBiome(CustomBiome customBiome, BiPredicate<Player, ChunkLocation> conditional, PacketHandler.Priority priority) {

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PhonyCustomBiome that = (PhonyCustomBiome) obj;
        return customBiome.toNamespacedKey().equals(that.customBiome.toNamespacedKey());
    }

    @Override
    public int hashCode() {
        return customBiome.toNamespacedKey().hashCode();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private CustomBiome customBiome;
        private BiPredicate<Player, ChunkLocation> conditional = (player, chunkLocation) -> true;
        private PacketHandler.Priority priority = PacketHandler.Priority.NORMAL;

        public Builder setCustomBiome(CustomBiome customBiome) {
            this.customBiome = customBiome;
            return this;
        }

        public Builder setConditional(BiPredicate<Player, ChunkLocation> conditional) {
            this.conditional = conditional;
            return this;
        }

        public Builder setPriority(PacketHandler.Priority priority) {
            this.priority = priority;
            return this;
        }

        public PhonyCustomBiome build() {
            Preconditions.checkNotNull(customBiome, "customBiome cannot be null");
            return new PhonyCustomBiome(customBiome, conditional, priority);
        }
    }
}
