package dev.wyck.wrapper.environment.sounds;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record BackgroundMusicImpl(@Override Optional<Music> defaultMusic, @Override Optional<Music> creativeMusic, @Override Optional<Music> underwaterMusic) implements BackgroundMusic {

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.attribute.BackgroundMusic(
            this.defaultMusic.map(Music::asHandle),
            this.creativeMusic.map(Music::asHandle),
            this.underwaterMusic.map(Music::asHandle)
        );
    }
}
