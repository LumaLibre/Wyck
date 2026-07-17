package dev.wyck.environment.sounds;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.bukkit.Sound;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

/**
 * A sound event that is specific to a {@link Sound}.
 *
 * @since 3.0.1
 * @version 3.0.1
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.1")
public interface BukkitSoundEvent extends SoundEvent {

    @ApiStatus.Internal
    ConstructWireProvider<BukkitSoundEvent> WIRE = ConstructWireProvider.create("dev.wyck.environment.sounds.BukkitSoundEventImpl");

    /**
     * The {@link Sound} of the sound event.
     * @return the sound of the sound event
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    Sound sound();

    /**
     * Creates a new BukkitSoundEvent from a {@link Sound}.
     * @param sound the sound to create a BukkitSoundEvent from
     * @return a new BukkitSoundEvent from the given sound
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    static BukkitSoundEvent variableRange(Sound sound) {
        return WIRE.construct(sound, Optional.empty());
    }

    /**
     * Creates a new BukkitSoundEvent with a fixed range.
     * @param sound the sound to create a BukkitSoundEvent from
     * @param range the range of the sound event
     * @return a new BukkitSoundEvent with a fixed range
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    static BukkitSoundEvent fixedRange(Sound sound, float range) {
        return WIRE.construct(sound, Optional.of(range));
    }
}
