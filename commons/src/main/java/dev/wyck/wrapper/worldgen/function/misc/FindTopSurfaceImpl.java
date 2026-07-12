package dev.wyck.wrapper.worldgen.function.misc;

import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.Lazy;
import dev.wyck.wrapper.worldgen.function.DensityFunction;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record FindTopSurfaceImpl(
    @Override Optional<ResourceKey> resourceKey,
    @Override DensityFunction density,
    @Override DensityFunction upperBound,
    @Override int lowerBound,
    @Override int cellHeight
) implements FindTopSurface {

    private static final Lazy<WyckRegistry> REGISTRY = WyckRegistry.lazy(RegistryId.DENSITY_FUNCTION);

    @Override
    public Object toMinecraft() {
        net.minecraft.world.level.levelgen.DensityFunction unwrappedDensity = this.density.asHandle();
        net.minecraft.world.level.levelgen.DensityFunction unwrappedUpperBound = this.upperBound.asHandle();
        return net.minecraft.world.level.levelgen.DensityFunctions.findTopSurface(
            unwrappedDensity,
            unwrappedUpperBound,
            this.lowerBound,
            this.cellHeight
        );
    }

    @Override
    public FindTopSurface register() {
        ResourceKey key = this.resourceKey.orElseThrow();
        REGISTRY.get().register(key, this);
        return this;
    }

    @Override
    public Key key() {
        return this.resourceKey.orElseThrow();
    }
}
