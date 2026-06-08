package me.outspending.biomesapi.worldgen;

import org.bukkit.Material;

public record PillarConfig(Material pillarBlock, Material capBlock, int minHeight, int maxHeight) {
    public static PillarConfig defaults() {
        return new PillarConfig(Material.OBSIDIAN, Material.GLOWSTONE, 4, 9);
    }
}