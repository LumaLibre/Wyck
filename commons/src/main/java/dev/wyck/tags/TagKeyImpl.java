package dev.wyck.tags;

import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record TagKeyImpl(
    @Override RegistryId registry,
    @Override ResourceKey key
) implements TagKey {

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Object toMinecraft() {
        // FIXME: should loop over registry keys and find the first one that exists on this version
        net.minecraft.resources.Identifier registryId = registry.keys().getFirst().identifier();
        net.minecraft.resources.ResourceKey registryKey = net.minecraft.resources.ResourceKey.createRegistryKey(registryId);
        return net.minecraft.tags.TagKey.create(registryKey, key.identifier());
    }
}
