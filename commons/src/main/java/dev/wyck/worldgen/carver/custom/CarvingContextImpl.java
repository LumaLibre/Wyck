package dev.wyck.worldgen.carver.custom;

import com.google.common.base.Preconditions;
import dev.wyck.keys.ResourceKey;
import dev.wyck.keys.ResourceKeyImpl;
import dev.wyck.misc.ChunkLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.material.Fluids;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.bukkit.craftbukkit.util.RandomSourceWrapper;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;
import java.util.Random;
import java.util.function.Function;

@NullMarked
@ApiStatus.Internal
public final class CarvingContextImpl<C> implements CarvingContext<C> {

    private final CustomCarverBridge<C> bridge;
    private final net.minecraft.world.level.levelgen.carver.CarvingContext handle;
    private final CustomCarverBridge.Config<C> configuration;
    private final ChunkAccess chunk;
    private final Function<BlockPos, Holder<Biome>> biomeGetter;
    private final RandomSource random;
    private final Aquifer aquifer;
    private final ChunkPos sourceChunkPos;
    private final CarvingMask mask;
    private final BlockPos.MutableBlockPos scratch = new BlockPos.MutableBlockPos();
    private @Nullable Random wrappedRandom;

    public CarvingContextImpl(
        CustomCarverBridge<C> bridge,
        net.minecraft.world.level.levelgen.carver.CarvingContext handle,
        CustomCarverBridge.Config<C> configuration,
        ChunkAccess chunk,
        Function<BlockPos, Holder<Biome>> biomeGetter,
        RandomSource random,
        Aquifer aquifer,
        ChunkPos sourceChunkPos,
        CarvingMask mask
    ) {
        this.bridge = bridge;
        this.handle = handle;
        this.configuration = configuration;
        this.chunk = chunk;
        this.biomeGetter = biomeGetter;
        this.random = random;
        this.aquifer = aquifer;
        this.sourceChunkPos = sourceChunkPos;
        this.mask = mask;
    }

    @Override
    public C config() {
        return this.configuration.config();
    }

    @Override
    public Random random() {
        if (this.wrappedRandom == null) {
            this.wrappedRandom = new RandomSourceWrapper.RandomWrapper(this.random);
        }

        return this.wrappedRandom;
    }

    @Override
    public ChunkLocation sourceChunk() {
        return new ChunkLocation(this.sourceChunkPos.x(), this.sourceChunkPos.z());
    }

    @Override
    public ChunkLocation chunkLocation() {
        ChunkPos pos = this.chunk.getPos();
        return new ChunkLocation(pos.x(), pos.z());
    }

    @Override
    public int minGenY() {
        return this.handle.getMinGenY();
    }

    @Override
    public int genDepth() {
        return this.handle.getGenDepth();
    }

    @Override
    public boolean carveEllipsoid(double x, double y, double z, double horizontalRadius, double verticalRadius, CarveSkipChecker skipChecker) {
        Preconditions.checkNotNull(skipChecker, "skipChecker");

        return this.bridge.bridgeCarveEllipsoid(
            this.handle,
            this.configuration,
            this.chunk,
            this.biomeGetter,
            this.aquifer,
            x, y, z,
            horizontalRadius,
            verticalRadius,
            this.mask,
            (nmsContext, xd, yd, zd, worldY) -> skipChecker.shouldSkip(this, xd, yd, zd, worldY)
        );
    }

    @Override
    public boolean canReach(double x, double z, int currentStep, int totalSteps, float thickness) {
        return CustomCarverBridge.bridgeCanReach(this.chunk.getPos(), x, z, currentStep, totalSteps, thickness);
    }

    @Override
    public Optional<BlockData> carveState(int x, int y, int z) {
        if (y <= this.configuration.lavaLevel.resolveY(this.handle)) {
            return Optional.of(CraftBlockData.createData(Fluids.LAVA.defaultFluidState().createLegacyBlock()));
        }

        BlockState substance = this.aquifer.computeSubstance(new DensityFunction.SinglePointContext(x, y, z), 0.0D);
        return substance == null ? Optional.empty() : Optional.of(CraftBlockData.createData(substance));
    }

    @Override
    public boolean shouldScheduleFluidUpdate() {
        return this.aquifer.shouldScheduleFluidUpdate();
    }

    @Override
    public BlockData getBlock(int x, int y, int z) {
        return CraftBlockData.createData(this.chunk.getBlockState(x, y, z));
    }

    @Override
    public void setBlock(int x, int y, int z, BlockData data) {
        Preconditions.checkNotNull(data, "data");
        this.chunk.setBlockState(this.scratch.set(x, y, z), ((CraftBlockData) data).getState());
    }

    @Override
    public dev.wyck.biome.Biome biome(int x, int y, int z) {
        ResourceKey resourceKey = new ResourceKeyImpl(
            this.biomeGetter.apply(this.scratch.set(x, y, z))
                .unwrapKey()
                .orElseThrow(() -> new IllegalStateException("unregistered biome at " + x + ", " + y + ", " + z))
                .identifier()
        );
        return dev.wyck.biome.Biome.reference(resourceKey);
    }

    @Override
    public boolean isCarved(int x, int y, int z) {
        ChunkPos pos = this.chunk.getPos();
        int relativeX = x - pos.getMinBlockX();
        int relativeZ = z - pos.getMinBlockZ();

        if (isOutsideChunk(relativeX, y, relativeZ)) {
            return false;
        }

        return this.mask.get(relativeX, y, relativeZ);
    }

    @Override
    public void markCarved(int x, int y, int z) {
        ChunkPos pos = this.chunk.getPos();
        int relativeX = x - pos.getMinBlockX();
        int relativeZ = z - pos.getMinBlockZ();

        if (isOutsideChunk(relativeX, y, relativeZ)) {
            return;
        }

        this.mask.set(relativeX, y, relativeZ);
    }

    @Override
    public boolean canReplaceBlock(BlockData state) {
        Preconditions.checkNotNull(state, "state");
        return ((CraftBlockData) state).getState().is(this.configuration.replaceable);
    }

    @Override
    public boolean isUpgrading() {
        return this.chunk.isUpgrading();
    }

    @Override
    public void markPosForPostProcessing(int x, int y, int z) {
        this.chunk.markPosForPostProcessing(this.scratch.set(x, y, z));
    }

    @Override
    @SuppressWarnings("deprecation")
    public Optional<BlockData> topMaterial(int x, int y, int z, boolean underFluid) {
        return this.handle.topMaterial(this.biomeGetter, this.chunk, this.scratch.set(x, y, z), underFluid)
            .map(CraftBlockData::createData);
    }

    @Override
    public Object toMinecraft() {
        return this.handle;
    }

}