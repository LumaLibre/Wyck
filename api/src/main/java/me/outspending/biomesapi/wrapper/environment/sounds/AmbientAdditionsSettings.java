package me.outspending.biomesapi.wrapper.environment.sounds;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.ConstructWireProvider;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Wrapper for AmbientAdditionsSettings.
 *
 * @since 2.4.1
 * @version 2.4.1
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.1")
public interface AmbientAdditionsSettings extends NmsHandle {

    @ApiStatus.Internal
    ConstructWireProvider<AmbientAdditionsSettings> WIRE = WireProvider.construct("me.outspending.biomesapi.wrapper.environment.sounds.AmbientAdditionsSettingsImpl");

    /**
     * The sound event to play.
     * @return the sound event to play
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    SoundEvent soundEvent();

    /**
     * The chance of the sound event to play per tick.
     * @return the chance of the sound event to play per tick
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    double tickChance();

    /**
     * Creates a new ambient additions settings record.
     * @param soundEvent the sound event to play
     * @param tickChance the chance of the sound event to play per tick
     * @return a new ambient additions settings record
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    static AmbientAdditionsSettings of(SoundEvent soundEvent, double tickChance) {
        return WIRE.construct(soundEvent, tickChance);
    }
}
