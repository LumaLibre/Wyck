package dev.wyck.environment.sounds;

import dev.wyck.environment.sounds.Music;
import dev.wyck.environment.sounds.SoundEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record MusicImpl(@Override SoundEvent sound, @Override int minDelay, @Override int maxDelay, @Override boolean replaceCurrentMusic) implements Music {

    @Override
    public Object toMinecraft() {
        return new net.minecraft.sounds.Music(
            net.minecraft.core.Holder.direct(this.sound.asHandle()),
            this.minDelay,
            this.maxDelay,
            this.replaceCurrentMusic
        );
    }
}
