package dev.wyck.registry.worldgen;

import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.Lazy;
import dev.wyck.worldgen.carver.custom.CustomCarver;
import dev.wyck.worldgen.carver.custom.CustomCarverBridge;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("3.0.0")
@ApiStatus.Internal
public final class CustomCarverRegistryImpl implements CustomCarverRegistry {

    private final Lazy<WyckRegistry> carverRegistry = WyckRegistry.lazy(RegistryId.CARVER);

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void register(ResourceKey key, CustomCarver<?> carver) {
        CustomCarverBridge<?> bridge = new CustomCarverBridge((CustomCarver) carver, carver.newConfig());
        this.carverRegistry.get().register(key, bridge);
    }
}
