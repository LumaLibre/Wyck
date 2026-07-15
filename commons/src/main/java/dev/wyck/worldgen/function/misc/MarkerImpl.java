package dev.wyck.worldgen.function.misc;

import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.Lazy;
import dev.wyck.worldgen.function.DensityFunction;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record MarkerImpl(
    @Override Optional<ResourceKey> resourceKey,
    @Override Type type,
    @Override DensityFunction input
) implements Marker {

    private static final Lazy<WyckRegistry> REGISTRY = WyckRegistry.lazy(RegistryId.DENSITY_FUNCTION);

    @Override
    public Object toMinecraft() {
        net.minecraft.world.level.levelgen.DensityFunction unwrapped = this.input.asHandle();
        return switch (type) {
            case INTERPOLATED -> net.minecraft.world.level.levelgen.DensityFunctions.interpolated(unwrapped);
            case FLAT_CACHE -> net.minecraft.world.level.levelgen.DensityFunctions.flatCache(unwrapped);
            case CACHE_2D -> net.minecraft.world.level.levelgen.DensityFunctions.cache2d(unwrapped);
            case CACHE_ONCE -> net.minecraft.world.level.levelgen.DensityFunctions.cacheOnce(unwrapped);
            case CACHE_ALL_IN_CELL -> net.minecraft.world.level.levelgen.DensityFunctions.cacheAllInCell(unwrapped);
            case BLEND_DENSITY -> net.minecraft.world.level.levelgen.DensityFunctions.blendDensity(unwrapped);
        };
    }

    @Override
    public Marker register() {
        ResourceKey key = this.resourceKey.orElseThrow();
        REGISTRY.get().register(key, this);
        return this;
    }

    @Override
    public Key key() {
        return this.resourceKey.orElseThrow();
    }
}
