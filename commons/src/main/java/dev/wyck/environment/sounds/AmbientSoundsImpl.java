package dev.wyck.environment.sounds;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record AmbientSoundsImpl(@Override Optional<SoundEvent> loop, @Override Optional<AmbientMoodSettings> mood, @Override List<AmbientAdditionsSettings> additions) implements AmbientSounds {

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.attribute.AmbientSounds(
            this.loop.map(SoundEvent::<net.minecraft.sounds.SoundEvent>asHandle).map(net.minecraft.core.Holder::direct),
            this.mood.map(AmbientMoodSettings::asHandle),
            this.additions.stream().map(AmbientAdditionsSettings::<net.minecraft.world.attribute.AmbientAdditionsSettings>asHandle).toList()
        );
    }
}
