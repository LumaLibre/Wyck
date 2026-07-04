package dev.wyck.test.worldgen;

import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.worldgen.feature.custom.CustomFeature;
import dev.wyck.wrapper.worldgen.feature.custom.PlacementContext;
import org.bukkit.Axis;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Orientable;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.util.BlockVector;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@NullMarked
public class FancyTreeFeature extends CustomFeature<FancyTreeFeature.TreeConfig> {

    public FancyTreeFeature() {
        super(TreeConfig::defaults, ResourceKey.of("test", "fancy_tree"));
    }

    @Override
    public boolean place(PlacementContext<TreeConfig> context) {
        TreeConfig config = context.config();
        Random random = context.random();
        BlockVector origin = context.origin();
        Set<BlockVector> logs = new HashSet<>();
        List<double[]> tips = new ArrayList<>();

        double[] base = {origin.getBlockX() + 0.5, origin.getBlockY(), origin.getBlockZ() + 0.5};

        double tiltAzimuth = random.nextDouble() * Math.PI * 2;
        double tilt = 0.15 + random.nextDouble() * 0.2;
        double[] trunkDir = normalize(new double[]{
            Math.cos(tiltAzimuth) * tilt,
            1.0,
            Math.sin(tiltAzimuth) * tilt
        });

        growLimb(context, logs, tips, base, trunkDir, config.trunkLength(), config.trunkThickness(), 0, config, random);
        placeRoots(context, logs, base, config, random);

        for (double[] tip : tips) {
            placeLeafCluster(context, logs, tip, config, random);
        }

        return true;
    }

    private void growLimb(PlacementContext<TreeConfig> context, Set<BlockVector> logs, List<double[]> tips,
                          double[] start, double[] direction, double length, double thickness, int depth,
                          TreeConfig config, Random random) {
        double childThickness = Math.max(0.5, thickness * 0.65);
        int steps = (int) Math.ceil(length);
        double[] pos = start.clone();
        double[] dir = direction.clone();

        for (int i = 0; i < steps; i++) {
            double t = (double) i / steps;
            double radius = thickness + (childThickness - thickness) * t;
            placeSphere(context, logs, pos, radius, axisOf(dir), config.logBlock());

            dir[0] += (random.nextDouble() - 0.5) * 0.25;
            dir[1] += (random.nextDouble() - 0.5) * 0.25 + (depth > 0 ? 0.06 : 0.0);
            dir[2] += (random.nextDouble() - 0.5) * 0.25;
            normalizeInPlace(dir);

            pos[0] += dir[0];
            pos[1] += dir[1];
            pos[2] += dir[2];
        }

        if (depth >= config.maxDepth() || childThickness <= 0.6) {
            tips.add(pos.clone());
            return;
        }

        int splits = config.minSplits() + random.nextInt(config.maxSplits() - config.minSplits() + 1);
        double baseAzimuth = random.nextDouble() * Math.PI * 2;
        for (int i = 0; i < splits; i++) {
            double azimuth = baseAzimuth + (Math.PI * 2 / splits) * i + (random.nextDouble() - 0.5) * 0.8;
            double spread = 0.45 + random.nextDouble() * 0.35;
            double[] childDir = normalize(new double[]{
                dir[0] * (1 - spread) + Math.cos(azimuth) * spread,
                dir[1] * (1 - spread) + 0.15,
                dir[2] * (1 - spread) + Math.sin(azimuth) * spread
            });
            growLimb(context, logs, tips, pos, childDir, length * 0.65, childThickness, depth + 1, config, random);
        }
    }

    private void placeRoots(PlacementContext<TreeConfig> context, Set<BlockVector> logs, double[] base,
                            TreeConfig config, Random random) {
        int roots = 4 + random.nextInt(3);
        double baseAzimuth = random.nextDouble() * Math.PI * 2;
        for (int i = 0; i < roots; i++) {
            double azimuth = baseAzimuth + (Math.PI * 2 / roots) * i + (random.nextDouble() - 0.5) * 0.6;
            double[] dir = normalize(new double[]{Math.cos(azimuth), -0.3, Math.sin(azimuth)});
            double[] pos = {
                base[0] + Math.cos(azimuth) * config.trunkThickness() * 0.6,
                base[1] + 1.0,
                base[2] + Math.sin(azimuth) * config.trunkThickness() * 0.6
            };

            int steps = 3 + random.nextInt(3);
            for (int s = 0; s < steps; s++) {
                double t = (double) s / steps;
                double radius = 1.1 + (0.5 - 1.1) * t;
                placeSphere(context, logs, pos, radius, axisOf(dir), config.logBlock());

                pos[0] += dir[0];
                pos[1] = Math.max(base[1] - 1, pos[1] + dir[1]);
                pos[2] += dir[2];
            }
        }
    }

    private void placeLeafCluster(PlacementContext<TreeConfig> context, Set<BlockVector> logs, double[] tip,
                                  TreeConfig config, Random random) {
        BlockVector center = new BlockVector(
            (int) Math.round(tip[0]),
            (int) Math.round(tip[1]),
            (int) Math.round(tip[2])
        );

        placeLeafBlob(context, logs, center, config.leafRadius(), config.leafBlock(), random);

        int lumps = 2 + random.nextInt(2);
        for (int i = 0; i < lumps; i++) {
            BlockVector lump = new BlockVector(
                center.getBlockX() + random.nextInt(5) - 2,
                center.getBlockY() + random.nextInt(3) - 1,
                center.getBlockZ() + random.nextInt(5) - 2
            );
            placeLeafBlob(context, logs, lump, Math.max(2, config.leafRadius() - 1), config.leafBlock(), random);
        }

        if (config.weeping()) {
            placeStrands(context, logs, center, config.leafRadius(), config.leafBlock(), random);
        }
    }

    private void placeLeafBlob(PlacementContext<TreeConfig> context, Set<BlockVector> logs, BlockVector center,
                               int radius, Material leaf, Random random) {
        double radiusSq = radius * radius + 0.5;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius + 1; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    double distSq = dx * dx + dy * dy * 1.6 + dz * dz;
                    if (distSq > radiusSq) continue;
                    if (distSq > radiusSq - 2 && random.nextInt(3) == 0) continue;

                    setLeaf(context, logs, new BlockVector(
                        center.getBlockX() + dx,
                        center.getBlockY() + dy,
                        center.getBlockZ() + dz
                    ), leaf);
                }
            }
        }
    }

    private void placeStrands(PlacementContext<TreeConfig> context, Set<BlockVector> logs, BlockVector center,
                              int radius, Material leaf, Random random) {
        double radiusSq = radius * radius + 0.5;
        int strands = 3 + random.nextInt(radius + 1);
        for (int i = 0; i < strands; i++) {
            int sdx = random.nextInt(radius * 2 + 1) - radius;
            int sdz = random.nextInt(radius * 2 + 1) - radius;
            double slack = radiusSq - sdx * sdx - sdz * sdz;
            if (slack <= 0) continue;

            int dyMin = Math.max(-radius + 1, -(int) Math.floor(Math.sqrt(slack / 1.6)));
            int length = 2 + random.nextInt(4);
            for (int d = 1; d <= length; d++) {
                setLeaf(context, logs, new BlockVector(
                    center.getBlockX() + sdx,
                    center.getBlockY() + dyMin - d,
                    center.getBlockZ() + sdz
                ), leaf);
            }
        }
    }

    private void placeSphere(PlacementContext<TreeConfig> context, Set<BlockVector> logs, double[] center,
                             double radius, Axis axis, Material log) {
        int reach = (int) Math.ceil(radius);
        double radiusSq = radius * radius + 0.3;
        int cx = (int) Math.floor(center[0]);
        int cy = (int) Math.floor(center[1]);
        int cz = (int) Math.floor(center[2]);

        for (int dx = -reach; dx <= reach; dx++) {
            for (int dy = -reach; dy <= reach; dy++) {
                for (int dz = -reach; dz <= reach; dz++) {
                    double px = cx + dx + 0.5 - center[0];
                    double py = cy + dy + 0.5 - center[1];
                    double pz = cz + dz + 0.5 - center[2];
                    if (px * px + py * py + pz * pz > radiusSq) continue;

                    setLog(context, logs, new BlockVector(cx + dx, cy + dy, cz + dz), log, axis);
                }
            }
        }
    }

    private void setLog(PlacementContext<TreeConfig> context, Set<BlockVector> logs, BlockVector pos, Material log, Axis axis) {
        BlockData data = log.createBlockData();
        if (data instanceof Orientable orientable) {
            orientable.setAxis(axis);
        }
        context.setBlock(pos, data);
        logs.add(pos);
    }

    private void setLeaf(PlacementContext<TreeConfig> context, Set<BlockVector> logs, BlockVector pos, Material leaf) {
        if (logs.contains(pos)) return;
        BlockData data = leaf.createBlockData();
        if (data instanceof Leaves leaves) {
            leaves.setPersistent(true);
        }
        context.setBlock(pos, data);
    }

    private Axis axisOf(double[] dir) {
        double ax = Math.abs(dir[0]);
        double ay = Math.abs(dir[1]);
        double az = Math.abs(dir[2]);
        if (ay >= ax && ay >= az) return Axis.Y;
        return ax >= az ? Axis.X : Axis.Z;
    }

    private double[] normalize(double[] v) {
        double[] out = v.clone();
        normalizeInPlace(out);
        return out;
    }

    private void normalizeInPlace(double[] v) {
        double len = Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
        if (len < 1.0e-6) {
            v[0] = 0;
            v[1] = 1;
            v[2] = 0;
            return;
        }
        v[0] /= len;
        v[1] /= len;
        v[2] /= len;
    }

    public record TreeConfig(
        Material logBlock,
        Material leafBlock,
        int trunkLength,
        double trunkThickness,
        int maxDepth,
        int minSplits,
        int maxSplits,
        int leafRadius,
        boolean weeping
    ) {
        public static TreeConfig defaults() {
            return new TreeConfig(Material.OAK_LOG, Material.OAK_LEAVES, 10, 1.8, 2, 2, 3, 3, true);
        }
    }
}