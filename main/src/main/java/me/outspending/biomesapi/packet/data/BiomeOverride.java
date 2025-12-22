package me.outspending.biomesapi.packet.data;

import me.outspending.biomesapi.biome.CustomBiome;
import org.bukkit.entity.Player;

import java.util.function.Predicate;

public record BiomeOverride(CustomBiome customBiome, Predicate<Player> condition) {
}
