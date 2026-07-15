package dev.wyck.test.carver;

import dev.wyck.keys.ResourceKey;
import dev.wyck.misc.ChunkLocation;
import dev.wyck.wrapper.worldgen.carver.custom.CarvingContext;
import dev.wyck.wrapper.worldgen.carver.custom.CustomCarver;
import dev.wyck.wrapper.worldgen.heightproviders.VerticalAnchor;
import org.bukkit.Material;
import org.jspecify.annotations.NullMarked;

import java.util.Random;
import java.util.Set;

@NullMarked
public final class StarCarver extends CustomCarver<StarConfig> {

    private static final Set<Material> REPLACEABLE = Set.of(
        Material.GRASS_BLOCK,
        Material.DIRT,
        Material.COARSE_DIRT,
        Material.ROOTED_DIRT,
        Material.PODZOL,
        Material.MYCELIUM,
        Material.STONE,
        Material.DEEPSLATE,
        Material.ANDESITE,
        Material.DIORITE,
        Material.GRANITE,
        Material.TUFF,
        Material.GRAVEL,
        Material.SAND,
        Material.SANDSTONE,
        Material.CLAY,
        Material.SNOW_BLOCK,
        Material.POWDER_SNOW
    );

    public StarCarver() {
        super(StarConfig::defaults, ResourceKey.of("example", "star"));
    }

    @Override
    public float probability() {
        return 1.0F;
    }

    @Override
    public VerticalAnchor lavaLevel() {
        return VerticalAnchor.bottom();
    }

    @Override
    public Set<Material> replaceable() {
        return REPLACEABLE;
    }

    @Override
    public boolean isStartChunk(StarConfig config, Random random) {
        return true;
    }

    @Override
    public boolean carve(CarvingContext<StarConfig> context) {
        StarConfig config = context.config();
        ChunkLocation source = context.sourceChunk();

        double centerX = (source.x() << 4) + 8.0D;
        double centerZ = (source.z() << 4) + 8.0D;

        int topY = Math.min(config.topY(), context.minGenY() + context.genDepth() - 10);
        int bottomY = Math.max(config.bottomY(), context.minGenY() + 2);
        boolean carved = false;

        for (int y = topY; y >= bottomY; y--) {
            if (!context.canReach(centerX, centerZ, 0, 1, (float) config.outerRadius())) {
                return carved;
            }

            double twist = (y - bottomY) * config.twistPerBlock();
            int points = config.points();
            double outer = config.outerRadius();
            double inner = config.innerRadius();

            carved |= context.carveEllipsoid(centerX, y, centerZ, outer, 1.0D, (ctx, xd, yd, zd, worldY) -> {
                double distanceSquared = xd * xd + zd * zd;

                if (distanceSquared >= 1.0D) {
                    return true;
                }

                double angle = Math.atan2(zd, xd) + twist;
                double lobe = (Math.cos(angle * points) + 1.0D) * 0.5D;
                double limit = (inner + (outer - inner) * lobe) / outer;

                return distanceSquared >= limit * limit;
            });
        }

        return carved;
    }
}