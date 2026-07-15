package dev.wyck.worldgen.function.misc;

import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.Lazy;
import dev.wyck.worldgen.function.misc.YClampedGradient;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record YClampedGradientImpl(
    @Override Optional<ResourceKey> resourceKey,
    @Override int fromY,
    @Override int toY,
    @Override double fromValue,
    @Override double toValue
) implements YClampedGradient {

    private static final Lazy<WyckRegistry> REGISTRY = WyckRegistry.lazy(RegistryId.DENSITY_FUNCTION);

    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.DensityFunctions.yClampedGradient(this.fromY, this.toY, this.fromValue, this.toValue);
    }

    @Override
    public YClampedGradient register() {
        ResourceKey key = this.resourceKey.orElseThrow();
        REGISTRY.get().register(key, this);
        return this;
    }

    @Override
    public Key key() {
        return this.resourceKey.orElseThrow();
    }
}
