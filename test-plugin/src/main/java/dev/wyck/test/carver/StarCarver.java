package dev.wyck.test.carver;

import dev.wyck.keys.ResourceKey;
import dev.wyck.misc.ChunkLocation;
import dev.wyck.worldgen.carver.custom.CarvingContext;
import dev.wyck.worldgen.carver.custom.CustomCarver;
import org.jspecify.annotations.NullMarked;

import java.util.Random;

@NullMarked
public final class StarCarver extends CustomCarver<StarConfig> {

    public StarCarver() {
        super(StarConfig::defaults, ResourceKey.of("example", "star"));
    }

    @Override
    public float probability() {
        return 1.0F;
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

        int topY = Math.min(config.topY(), context.maxGenY() - 9);
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
