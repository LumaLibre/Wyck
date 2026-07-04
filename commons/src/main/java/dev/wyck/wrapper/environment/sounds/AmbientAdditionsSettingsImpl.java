package dev.wyck.wrapper.environment.sounds;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record AmbientAdditionsSettingsImpl(@Override SoundEvent soundEvent, @Override double tickChance) implements AmbientAdditionsSettings {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.attribute.AmbientAdditionsSettings(
            net.minecraft.core.Holder.direct(this.soundEvent.asHandle()),
            this.tickChance
        );
    }
}
