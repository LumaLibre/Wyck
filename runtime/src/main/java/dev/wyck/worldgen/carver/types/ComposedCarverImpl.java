package dev.wyck.worldgen.carver.types;

import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.Lazy;
import dev.wyck.worldgen.carver.CarverConfiguration;
import dev.wyck.worldgen.carver.WorldCarverType;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.NoSuchElementException;
import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record ComposedCarverImpl(
    @Override Optional<ResourceKey> resourceKey,
    @Override WorldCarverType type,
    @Override CarverConfiguration config
) implements ComposedCarver {

    private static final Lazy<WyckRegistry> REGISTRY = WyckRegistry.lazy(RegistryId.CARVER);

    @Override
    public Object toMinecraft() {
        net.minecraft.world.level.levelgen.carver.WorldCarver carver = this.config.asHandle();
        return net.minecraft.core.Holder.direct(carver);
    }

    @Override
    public Key key() {
        return this.resourceKey.orElseThrow();
    }

    @Override
    public ComposedCarver register() {
        ResourceKey key = this.resourceKey.orElseThrow(() -> new NoSuchElementException("Cannot register a composed carver without a resource key."));
        REGISTRY.get().register(key, this.toMinecraft());
        return this;
    }
}
