package dev.wyck.worldgen.function.misc;

import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.Lazy;
import dev.wyck.worldgen.function.misc.EndIslands;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record EndIslandsImpl(
    @Override Optional<ResourceKey> resourceKey,
    @Override long seed
) implements EndIslands {

    private static final Lazy<WyckRegistry> REGISTRY = WyckRegistry.lazy(RegistryId.DENSITY_FUNCTION);

    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.DensityFunctions.endIslands(this.seed);
    }

    @Override
    public EndIslands register() {
        ResourceKey key = this.resourceKey.orElseThrow();
        REGISTRY.get().register(key, this);
        return this;
    }

    @Override
    public Key key() {
        return this.resourceKey.orElseThrow();
    }
}
