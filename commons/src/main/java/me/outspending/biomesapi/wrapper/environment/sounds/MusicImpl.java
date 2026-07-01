package me.outspending.biomesapi.wrapper.environment.sounds;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record MusicImpl(@Override SoundEvent sound, @Override int minDelay, @Override int maxDelay, @Override boolean replaceCurrentMusic) implements Music {

    @Override
    public Object toMinecraft() {
        net.minecraft.sounds.SoundEvent sound = (net.minecraft.sounds.SoundEvent) this.sound.toMinecraft();
        return new net.minecraft.sounds.Music(
            net.minecraft.core.Holder.direct(sound),
            this.minDelay,
            this.maxDelay,
            this.replaceCurrentMusic
        );
    }
}
