package dev.wyck.level.dimension.clock;

import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.Lazy;
import net.minecraft.world.clock.ClockTimeMarkers;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record TimeMarkerImpl(@Override ResourceKey key, @Override int ticks, @Override boolean showInCommands) implements TimeMarker {

    private static final Lazy<WyckRegistry> REGISTRY = WyckRegistry.lazy(RegistryId.CLOCK_TIME_MARKER);

    @Override
    public net.minecraft.resources.ResourceKey<net.minecraft.world.clock.ClockTimeMarker> toMinecraft() {
        return net.minecraft.resources.ResourceKey.create(ClockTimeMarkers.ROOT_ID, this.key.identifier());
    }

    @Override
    public TimeMarker register() {
        REGISTRY.get().register(this.key, this);
        return this;
    }
}
