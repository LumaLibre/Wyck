package dev.wyck.worldgen.carver.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import dev.wyck.util.WorldgenConversions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import org.bukkit.craftbukkit.util.RandomSourceWrapper;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.function.Function;
import java.util.function.Supplier;

@NullMarked
@ApiStatus.Internal
public final class CustomCarverBridge<C> extends WorldCarver<CustomCarverBridge.Config<C>> {

    private final CustomCarver<C> delegate;

    public CustomCarverBridge(CustomCarver<C> delegate, Supplier<C> configSupplier) {
        super(codec(delegate, configSupplier));
        this.delegate = delegate;
    }

    private static <C> Codec<Config<C>> codec(CustomCarver<C> delegate, Supplier<C> configSupplier) {
        return MapCodec.unit(() -> new Config<>(delegate, configSupplier.get())).codec();
    }

    public Config<C> config(C config) {
        return new Config<>(this.delegate, config);
    }

    @Override
    public int getRange() {
        return this.delegate.range();
    }

    @Override
    public boolean isStartChunk(Config<C> configuration, RandomSource random) {
        return this.delegate.isStartChunk(configuration.config(), new RandomSourceWrapper.RandomWrapper(random));
    }

    @Override
    public boolean carve(
        CarvingContext context,
        Config<C> configuration,
        ChunkAccess chunk,
        Function<BlockPos, Holder<Biome>> biomeGetter,
        RandomSource random,
        Aquifer aquifer,
        ChunkPos sourceChunkPos,
        CarvingMask mask
    ) {
        CarvingContextImpl<C> wrapped = new CarvingContextImpl<>(this, context, configuration, chunk, biomeGetter, random, aquifer, sourceChunkPos, mask);
        return this.delegate.carve(wrapped);
    }

    @Override
    protected boolean canReplaceBlock(Config<C> configuration, BlockState state) {
        return this.delegate.canReplaceBlock(configuration.config(), state.asBlockData());
    }

    boolean bridgeCarveEllipsoid(
        CarvingContext context,
        Config<C> configuration,
        ChunkAccess chunk,
        Function<BlockPos, Holder<Biome>> biomeGetter,
        Aquifer aquifer,
        double x,
        double y,
        double z,
        double horizontalRadius,
        double verticalRadius,
        CarvingMask mask,
        CarveSkipChecker skipChecker
    ) {
        return this.carveEllipsoid(context, configuration, chunk, biomeGetter, aquifer, x, y, z, horizontalRadius, verticalRadius, mask, skipChecker);
    }

    static boolean bridgeCanReach(ChunkPos chunkPos, double x, double z, int currentStep, int totalSteps, float thickness) {
        return canReach(chunkPos, x, z, currentStep, totalSteps, thickness);
    }

    public static final class Config<C> extends CarverConfiguration {

        private final C config;

        Config(CustomCarver<C> carver, C config) {
            super(
                carver.probability(),
                (net.minecraft.world.level.levelgen.heightproviders.HeightProvider) carver.y().toMinecraft(),
                (net.minecraft.util.valueproviders.FloatProvider) carver.yScale().toMinecraft(),
                (net.minecraft.world.level.levelgen.VerticalAnchor) carver.lavaLevel().toMinecraft(),
                CarverDebugSettings.DEFAULT,
                WorldgenConversions.toBlockHolderSet(carver.replaceable())
            );
            this.config = config;
        }

        public C config() {
            return this.config;
        }
    }
}