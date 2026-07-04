package dev.wyck.wrapper.worldgen.feature.custom;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
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

    private final FeaturePlaceContext<?> handle;
    private final C config;

    public PlacementContextImpl(FeaturePlaceContext<?> handle, C config) {
        this.handle = handle;
        this.config = config;
    }

    @Override
    public C config() {
        return this.config;
    }

    @Override
    public Random random() {
        return new RandomSourceWrapper.RandomWrapper(this.handle.random());
    }

    @Override
    public BlockVector origin() {
        BlockPos pos = this.handle.origin();
        return new BlockVector(pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public void setBlock(BlockVector position, BlockData data) {
        Preconditions.checkNotNull(position, "position");
        Preconditions.checkNotNull(data, "data");

        BlockPos pos = new BlockPos(position.getBlockX(), position.getBlockY(), position.getBlockZ());
        this.handle.level().setBlock(pos, ((CraftBlockData) data).getState(), 3);
    }

    @Override
    public BlockData getBlock(BlockVector position) {
        Preconditions.checkNotNull(position, "position");

        BlockPos pos = new BlockPos(position.getBlockX(), position.getBlockY(), position.getBlockZ());
        return CraftBlockData.createData(this.handle.level().getBlockState(pos));
    }

    @Override
    public boolean removeBlock(BlockVector position, boolean movedByPiston) {
        Preconditions.checkNotNull(position, "position");

        BlockPos pos = new BlockPos(position.getBlockX(), position.getBlockY(), position.getBlockZ());
        return this.handle.level().removeBlock(pos, movedByPiston);
    }

    @Override
    public boolean destroyBlock(BlockVector position, boolean dropResources, @Nullable Entity breaker, int updateLimit) {
        Preconditions.checkNotNull(position, "position");

        BlockPos pos = new BlockPos(position.getBlockX(), position.getBlockY(), position.getBlockZ());
        net.minecraft.world.entity.Entity nmsBreaker = breaker == null ? null : ((CraftEntity) breaker).getHandle();
        return this.handle.level().destroyBlock(pos, dropResources, nmsBreaker, updateLimit);
    }

    @Override
    public boolean addFreshEntity(Entity entity, CreatureSpawnEvent.@Nullable SpawnReason reason) {
        Preconditions.checkNotNull(entity, "entity");

        net.minecraft.world.entity.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        return this.handle.level().addFreshEntity(nmsEntity, reason);
    }
}