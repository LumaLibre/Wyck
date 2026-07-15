package dev.wyck.worldgen.function.simple;

import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.function.simple.BlendAlpha;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record BlendAlphaImpl() implements BlendAlpha {

    private static final ResourceKey KEY = ResourceKey.minecraft("blend_alpha");

    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.DensityFunctions.blendAlpha();
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
