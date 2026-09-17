package dev.wyck.worldgen.feature.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class CustomFeatureBridge<C> implements Feature {

    private final CustomFeature<C> delegate;
    private final C config;
    private final MapCodec<CustomFeatureBridge<C>> codec;

    public CustomFeatureBridge(CustomFeature<C> delegate, C config) {
        this.delegate = delegate;
        this.config = config;
        this.codec = MapCodec.unit(this);
    }

    public CustomFeature<C> delegate() {
        return this.delegate;
    }

    @Override
    public MapCodec<CustomFeatureBridge<C>> codec() {
        return this.codec;
    }

    @Override
    @Contract("_, _, _, _ -> true")
    public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
        PlacementContext<C> wrapped = new PlacementContextImpl<>(level, chunkGenerator, random, origin, this.config);
        return this.delegate.place(wrapped);
    }
}
