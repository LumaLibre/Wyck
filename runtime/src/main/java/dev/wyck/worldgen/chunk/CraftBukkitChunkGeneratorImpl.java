package dev.wyck.worldgen.chunk;

import net.minecraft.server.level.ServerLevel;
import org.bukkit.craftbukkit.generator.CustomChunkGenerator;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

@NullMarked
@ApiStatus.Internal
public final class CraftBukkitChunkGeneratorImpl extends ChunkGeneratorImpl implements CraftBukkitChunkGenerator {

    private static final MethodHandle LEVEL_SETTER = resolveLevelSetter();

    private final ChunkGenerator delegate;
    private final org.bukkit.generator.ChunkGenerator generator;
    private @Nullable CustomChunkGenerator handle;

    public CraftBukkitChunkGeneratorImpl(ChunkGenerator delegate, org.bukkit.generator.ChunkGenerator generator) {
        super(delegate.biomeSource());
        this.delegate = delegate;
        this.generator = generator;
    }

    @Override
    public ChunkGenerator delegate() {
        return this.delegate;
    }

    @Override
    public org.bukkit.generator.ChunkGenerator generator() {
        return this.generator;
    }

    public void bindLevel(net.minecraft.server.level.ServerLevel level) {
        CustomChunkGenerator handle = this.handle;
        if (handle == null) {
            return;
        }
        try {
            LEVEL_SETTER.invoke(handle, level);
        } catch (Throwable throwable) {
            throw new IllegalStateException("Failed to bind ServerLevel into CustomChunkGenerator for world " + level, throwable);
        }
    }

    @Override
    public Object toMinecraft() {
        CustomChunkGenerator handle = this.handle;
        if (handle == null) {
            handle = new CustomChunkGenerator(
                null,
                this.delegate.asHandle(),
                this.generator
            );
            this.handle = handle;
        }
        return handle;
    }

    private static MethodHandle resolveLevelSetter() {
        for (Field field : CustomChunkGenerator.class.getDeclaredFields()) {
            if (field.getType() != ServerLevel.class) {
                continue;
            }
            try {
                field.setAccessible(true);
                return MethodHandles.privateLookupIn(CustomChunkGenerator.class, MethodHandles.lookup()).unreflectSetter(field);
            } catch (ReflectiveOperationException exception) {
                throw new ExceptionInInitializerError(exception);
            }
        }
        throw new ExceptionInInitializerError("No ServerLevel field found on " + CustomChunkGenerator.class.getName());
    }
}