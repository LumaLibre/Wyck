package dev.wyck.level.dimension.timeline.types;

import dev.wyck.keys.ResourceKey;
import dev.wyck.level.dimension.timeline.types.ReferencedTimeline;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;

public record ReferencedTimelineImpl(
    @Override ResourceKey key
) implements ReferencedTimeline {
    @Override
    public net.minecraft.world.timeline.Timeline toMinecraft() {
        net.minecraft.resources.Identifier id = key.identifier();
        net.minecraft.core.Registry<net.minecraft.world.timeline.Timeline> registry = BootstrapSafeMinecraftRegistries.mappedRegistry(net.minecraft.core.registries.Registries.TIMELINE);
        return registry.getOrThrow(net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.TIMELINE, id))
            .value();
    }
}
