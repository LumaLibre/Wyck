package dev.wyck.level.dimension.clock;

import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.util.Lazy;
import net.minecraft.core.Holder;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record WorldClockImpl(ResourceKey key) implements WorldClock {

    private static final Lazy<WyckRegistry> REGISTRY = WyckRegistry.lazy(RegistryId.WORLD_CLOCK);

    @Override
    public net.minecraft.core.Holder<net.minecraft.world.clock.WorldClock> toMinecraft() {
        net.minecraft.resources.Identifier id = this.key.identifier();
        net.minecraft.core.Registry<net.minecraft.world.clock.WorldClock> registry = BootstrapSafeMinecraftRegistries.mappedRegistry(net.minecraft.core.registries.Registries.WORLD_CLOCK);
        Optional<net.minecraft.core.Holder.Reference<net.minecraft.world.clock.WorldClock>> opt = registry.get(net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.WORLD_CLOCK, id));
        if (opt.isPresent()) {
            return opt.get();
        }

        // create a new world clock if not present
        return net.minecraft.core.Holder.direct(new net.minecraft.world.clock.WorldClock());
    }

    @Override
    public WorldClock register() {
        REGISTRY.get().register(this.key, this);
        return this;
    }
}