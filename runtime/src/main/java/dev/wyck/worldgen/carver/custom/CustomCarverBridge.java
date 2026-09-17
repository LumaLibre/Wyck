package dev.wyck.worldgen.carver.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.CarverOutput;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import org.bukkit.craftbukkit.util.RandomSourceWrapper;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class CustomCarverBridge<C> implements WorldCarver {
    private final CustomCarver<C> delegate;
    private final C config;
    private final MapCodec<CustomCarverBridge<C>> codec;

    public CustomCarverBridge(CustomCarver<C> delegate, C config) {
        this.delegate = delegate;
        this.config = config;
        this.codec = MapCodec.unit(() -> this);
    }

    @Override public int getRange() { return delegate.range(); }

    @Override
    public boolean isStartChunk(RandomSource random) {
        return delegate.isStartChunk(config, new RandomSourceWrapper.RandomWrapper(random));
    }

    @Override
    public boolean carve(WorldGenerationContext context, RandomSource random, ChunkPos chunkPos, ChunkPos sourceChunkPos, CarverOutput output) {
        return delegate.carve(new CarvingContextImpl<>(config, context, random, chunkPos, sourceChunkPos, output));
    }

    @Override public MapCodec<? extends WorldCarver> codec() { return codec; }
}
