package dev.wyck.worldgen.feature.custom;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKeyImpl;
import dev.wyck.level.dimension.Dimension;
import dev.wyck.worldgen.WorldContext;
import dev.wyck.worldgen.chunk.ChunkGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.util.RandomSourceWrapper;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Random;

@NullMarked
@AsOf("2.3.0")
@ApiStatus.Internal
public final class PlacementContextImpl<C> implements PlacementContext<C> {

    private final WorldGenLevel level;
    private final net.minecraft.world.level.chunk.ChunkGenerator chunkGenerator;
    private final RandomSource random;
    private final BlockPos origin;
    private final C config;

    public PlacementContextImpl(
        WorldGenLevel level,
        net.minecraft.world.level.chunk.ChunkGenerator chunkGenerator,
        RandomSource random,
        BlockPos origin,
        C config
    ) {
        this.level = level;
        this.chunkGenerator = chunkGenerator;
        this.random = random;
        this.origin = origin;
        this.config = config;
    }

    @Override
    public C config() {
        return this.config;
    }

    @Override
    public Random random() {
        return new RandomSourceWrapper.RandomWrapper(this.random);
    }

    @Override
    public BlockVector origin() {
        BlockPos pos = this.origin;
        return new BlockVector(pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public void setBlock(BlockVector position, BlockData data) {
        Preconditions.checkNotNull(position, "position");
        Preconditions.checkNotNull(data, "data");

        BlockPos pos = new BlockPos(position.getBlockX(), position.getBlockY(), position.getBlockZ());
        this.level.setBlock(pos, ((CraftBlockData) data).getState(), 3);
    }

    @Override
    public BlockData getBlock(BlockVector position) {
        Preconditions.checkNotNull(position, "position");

        BlockPos pos = new BlockPos(position.getBlockX(), position.getBlockY(), position.getBlockZ());
        return CraftBlockData.createData(this.level.getBlockState(pos));
    }

    @Override
    public boolean removeBlock(BlockVector position, boolean movedByPiston) {
        Preconditions.checkNotNull(position, "position");

        BlockPos pos = new BlockPos(position.getBlockX(), position.getBlockY(), position.getBlockZ());
        return this.level.removeBlock(pos, movedByPiston);
    }

    @Override
    public boolean destroyBlock(BlockVector position, boolean dropResources, @Nullable Entity breaker, int updateLimit) {
        Preconditions.checkNotNull(position, "position");

        BlockPos pos = new BlockPos(position.getBlockX(), position.getBlockY(), position.getBlockZ());
        net.minecraft.world.entity.Entity nmsBreaker = breaker == null ? null : ((CraftEntity) breaker).getHandle();
        return this.level.destroyBlock(pos, dropResources, nmsBreaker, updateLimit);
    }

    @Override
    public boolean addFreshEntity(Entity entity, CreatureSpawnEvent.@Nullable SpawnReason reason) {
        Preconditions.checkNotNull(entity, "entity");

        net.minecraft.world.entity.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        return this.level.addFreshEntity(nmsEntity, reason);
    }

    @Override
    public WorldContext worldContext() {
        ServerLevel level = this.level.getLevel();
        Identifier levelId = level.dimension().identifier();
        Identifier dimensionTypeId = level.dimensionTypeRegistration().unwrapKey().orElseThrow().identifier();

        return new WorldContext(
            new ResourceKeyImpl(levelId),
            level.uuid,
            level.getSeed(),
            level.getMinY(),
            level.getMaxY(),
            Dimension.reference(new ResourceKeyImpl(dimensionTypeId)),
            ChunkGenerator.decode(level.getChunkSource().getGenerator()),
            level.bukkitName,
            level.getWorld().getEnvironment()
        );
    }
}
