package dev.wyck.test.worldgen;

import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.worldgen.feature.custom.CustomFeature;
import dev.wyck.wrapper.worldgen.feature.custom.PlacementContext;
import org.bukkit.Material;
import org.bukkit.util.BlockVector;
import org.jspecify.annotations.NullMarked;

import java.util.Random;

@NullMarked
public class PillarFeature extends CustomFeature<PillarFeature.PillarConfig> {

    public PillarFeature() {
        super(PillarConfig::defaults, ResourceKey.of("test", "pillar_feature"));
    }

    @Override
    public boolean place(PlacementContext<PillarConfig> context) {
        PillarConfig config = context.config();
        Random random = context.random();
        BlockVector origin = context.origin();

        int height = config.minHeight() + random.nextInt(config.maxHeight() - config.minHeight() + 1);

        for (int y = 0; y < height; y++) {
            BlockVector pos = new BlockVector(origin.getBlockX(), origin.getBlockY() + y, origin.getBlockZ());
            context.setBlock(pos, config.pillarBlock().createBlockData());
        }

        BlockVector cap = new BlockVector(origin.getBlockX(), origin.getBlockY() + height, origin.getBlockZ());
        context.setBlock(cap, config.capBlock().createBlockData());

        return true;
    }

    public record PillarConfig(Material pillarBlock, Material capBlock, int minHeight, int maxHeight) {
        public static PillarConfig defaults() {
            return new PillarConfig(Material.OBSIDIAN, Material.GLOWSTONE, 4, 9);
        }
    }
}