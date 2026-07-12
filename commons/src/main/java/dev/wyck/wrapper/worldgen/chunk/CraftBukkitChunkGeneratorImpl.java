package dev.wyck.wrapper.worldgen.chunk;

import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.generator.CustomChunkGenerator;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class CraftBukkitChunkGeneratorImpl extends ChunkGeneratorImpl implements CraftBukkitChunkGenerator {

    private final World world;
    private final ChunkGenerator delegate;
    private final org.bukkit.generator.ChunkGenerator generator;

    public CraftBukkitChunkGeneratorImpl(World world, ChunkGenerator delegate, org.bukkit.generator.ChunkGenerator generator) {
        super(delegate.biomeSource());
        this.world = world;
        this.delegate = delegate;
        this.generator = generator;
    }

    @Override
    public World world() {
        return this.world;
    }

    @Override
    public ChunkGenerator delegate() {
        return this.delegate;
    }

    @Override
    public org.bukkit.generator.ChunkGenerator generator() {
        return this.generator;
    }

    @Override
    public Object toMinecraft() {
        net.minecraft.server.level.ServerLevel serverLevel = ((CraftWorld) this.world).getHandle();
        net.minecraft.world.level.chunk.ChunkGenerator delegate = this.delegate.asHandle();
        return new CustomChunkGenerator(
            serverLevel,
            delegate,
            this.generator
        );
    }
}
