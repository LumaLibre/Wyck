package me.outspending.biomesapi.wrapper.environment.sounds;

import me.outspending.biomesapi.keys.ResourceKey;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record SoundEventImpl(@Override ResourceKey location, @Override Optional<Float> fixedRange) implements SoundEvent {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.sounds.SoundEvent(
            this.location.identifier(),
            this.fixedRange
        );
    }

    @Override
    public Key key() {
        return this.location.key();
    }
}
