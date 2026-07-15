package dev.wyck.environment.sounds;

import dev.wyck.environment.sounds.AmbientMoodSettings;
import dev.wyck.environment.sounds.SoundEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record AmbientMoodSettingsImpl(@Override SoundEvent soundEvent, @Override int tickDelay, @Override int blockSearchExtent, @Override double soundPositionOffset) implements AmbientMoodSettings {

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.attribute.AmbientMoodSettings(
            net.minecraft.core.Holder.direct(this.soundEvent.asHandle()),
            this.tickDelay,
            this.blockSearchExtent,
            this.soundPositionOffset
        );
    }
}
