package dev.wyck.worldgen.carver.custom;

import dev.wyck.misc.ChunkLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.CarverOutput;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import org.bukkit.craftbukkit.util.RandomSourceWrapper;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Random;

@NullMarked
@ApiStatus.Internal
public final class CarvingContextImpl<C> implements CarvingContext<C> {
    private final C config;
    private final WorldGenerationContext generationContext;
    private final ChunkPos target;
    private final ChunkPos source;
    private final CarverOutput output;
    private final Random random;

    public CarvingContextImpl(C config, WorldGenerationContext generationContext, RandomSource random,
                              ChunkPos target, ChunkPos source, CarverOutput output) {
        this.config = config;
        this.generationContext = generationContext;
        this.target = target;
        this.source = source;
        this.output = output;
        this.random = new RandomSourceWrapper.RandomWrapper(random);
    }

    @Override public C config() { return config; }
    @Override public Random random() { return random; }
    @Override public ChunkLocation sourceChunk() { return new ChunkLocation(source.x(), source.z()); }
    @Override public ChunkLocation chunkLocation() { return new ChunkLocation(target.x(), target.z()); }
    @Override public int minGenY() { return output.minY(); }
    @Override public int maxGenY() { return output.maxY(); }

    @Override
    public void carve(int x, int y, int z) {
        int relativeX = x - target.getMinBlockX();
        int relativeZ = z - target.getMinBlockZ();
        if (relativeX >= 0 && relativeX < 16 && relativeZ >= 0 && relativeZ < 16 && y >= output.minY() && y <= output.maxY()) {
            output.carve(relativeX, y, relativeZ);
        }
    }

    @Override
    public boolean carveEllipsoid(double x, double y, double z, double horizontalRadius, double verticalRadius, CarveSkipChecker checker) {
        WorldCarver.carveEllipsoid(target, x, y, z, horizontalRadius, verticalRadius, output,
            (xd, yd, zd, worldY) -> checker.shouldSkip(this, xd, yd, zd, worldY));
        return true;
    }

    @Override
    public boolean canReach(double x, double z, int currentStep, int totalSteps, float thickness) {
        return WorldCarver.canReach(target, x, z, currentStep, totalSteps, thickness);
    }

    @Override public Object toMinecraft() { return output; }
}
