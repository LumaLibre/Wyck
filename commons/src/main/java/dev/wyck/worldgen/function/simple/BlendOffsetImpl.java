package dev.wyck.worldgen.function.simple;

import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.function.simple.BlendOffset;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record BlendOffsetImpl() implements BlendOffset {

    private static final ResourceKey KEY = ResourceKey.minecraft("blend_offset");

    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.DensityFunctions.blendOffset();
    }

    @Override
    public Optional<ResourceKey> resourceKey() {
        return Optional.of(KEY);
    }

    @Override
    public Key key() {
        return KEY;
    }
}
