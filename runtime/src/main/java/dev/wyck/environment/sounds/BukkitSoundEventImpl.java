package dev.wyck.environment.sounds;

import dev.wyck.keys.ResourceKey;
import net.kyori.adventure.key.Key;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.CraftSound;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record BukkitSoundEventImpl(
    @Override Sound sound,
    @Override Optional<Float> fixedRange
) implements BukkitSoundEvent {

    @Override
    public net.minecraft.sounds.SoundEvent toMinecraft() {
        return ((CraftSound) sound).getHandle();
    }

    @Override
    public ResourceKey location() {
        NamespacedKey key = ((CraftSound) sound).getKey();
        return ResourceKey.of(key.getNamespace(), key.getKey());
    }

    @Override
    public Key key() {
        return this.location();
    }
}
