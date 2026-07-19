package dev.wyck.level.dimension;

import dev.wyck.keys.ResourceKey;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.timeline.Timeline;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@ApiStatus.Internal
@Deprecated(forRemoval = true)
public record TimelineSetImpl(List<ResourceKey> timelines) implements TimelineSet {

    @Override
    public Object toMinecraft() {
        Registry<Timeline> registry = BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.TIMELINE);

        List<Holder<Timeline>> holders = timelines.stream()
            .map(key -> {
                Identifier id = (Identifier) key.resourceLocation();
                return (Holder<@NonNull Timeline>) registry.get(id).orElseThrow(() -> new IllegalArgumentException("Unknown timeline: " + id));
            })
            .toList();

        return HolderSet.direct(holders);
    }
}
