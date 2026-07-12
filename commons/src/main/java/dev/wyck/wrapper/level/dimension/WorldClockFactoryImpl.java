package dev.wyck.wrapper.level.dimension;

import dev.wyck.annotations.WireFactory;
import dev.wyck.keys.ResourceKey;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@ApiStatus.Internal
public final class WorldClockFactoryImpl implements WorldClock.Factory {

    @Override
    public WorldClock reference(ResourceKey key) {
        return new WorldClockImpl(key);
    }

    private record WorldClockImpl(ResourceKey key) implements WorldClock {
        @Override
        public Holder<net.minecraft.world.clock.WorldClock> toMinecraft() {
            Identifier id = (Identifier) this.key.resourceLocation();
            Registry<net.minecraft.world.clock.WorldClock> registry = BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.WORLD_CLOCK);
            return registry.getOrThrow(net.minecraft.resources.ResourceKey.create(Registries.WORLD_CLOCK, id));
        }
    }
}