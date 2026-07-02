package dev.wyck.wrapper.environment.sounds;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.factory.WireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.internal.Wrapper;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

/**
 * Wraps a Minecraft SoundEvent.
 *
 * @version 2.4.1
 * @since 2.4.1
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.1")
public interface SoundEvent extends Wrapper, Keyed {

    @ApiStatus.Internal
    ConstructWireProvider<SoundEvent> WIRE = WireProvider.construct("dev.wyck.wrapper.environment.sounds.SoundEventImpl");

    /**
     * Gets the location of the sound event.
     * @return the location of the sound event
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    ResourceKey location();

    /**
     * Gets the range of the sound event.
     * @return the range of the sound event, if present
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    Optional<Float> fixedRange();

    /**
     * Creates a new sound event with a variable range.
     * @param location the location of the sound event
     * @return a new sound event with a variable range
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    static SoundEvent variableRange(ResourceKey location) {
        return WIRE.construct(location, Optional.empty());
    }

    /**
     * Creates a new sound event with a fixed range.
     * @param location the location of the sound event
     * @param range the range of the sound event
     * @return a new sound event with a fixed range
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    static SoundEvent fixedRange(ResourceKey location, float range) {
        return WIRE.construct(location, Optional.of(range));
    }
}
