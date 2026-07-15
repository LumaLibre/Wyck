package dev.wyck.worldgen.function.simple;

import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.function.simple.ZeroSimpleFunction;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record ZeroSimpleFunctionImpl() implements ZeroSimpleFunction {

    private static final ResourceKey KEY = ResourceKey.minecraft("zero");

    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.DensityFunctions.zero();
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
