package dev.wyck.environment.sounds;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.factory.WireProvider;
import dev.wyck.wrapper.Wrapper;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Wrapper for AmbientAdditionsSettings.
 *
 * @since 2.4.1
 * @version 2.4.1
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.1")
public interface AmbientAdditionsSettings extends Wrapper {

    @ApiStatus.Internal
    ConstructWireProvider<AmbientAdditionsSettings> WIRE = WireProvider.construct("dev.wyck.environment.sounds.AmbientAdditionsSettingsImpl");

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

    /**
     * Creates a new ambient additions settings record builder.
     * @return a new ambient additions settings record builder
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for ambient additions settings records.
     * @since 2.4.1
     * @version 2.4.1
     * @author Jsinco
     */
    @AsOf("2.4.1")
    final class Builder {
        private @Nullable SoundEvent soundEvent;
        private double tickChance;

        /**
         * Sets the sound event to play.
         * @param soundEvent the sound event to play
         * @return this builder
         * @since 2.4.1
         */
        @AsOf("2.4.1")
        public Builder soundEvent(SoundEvent soundEvent) {
            this.soundEvent = soundEvent;
            return this;
        }

        /**
         * Sets the chance of the sound event to play per tick.
         * @param tickChance the chance of the sound event to play per tick
         * @return this builder
         * @since 2.4.1
         */
        @AsOf("2.4.1")
        public Builder tickChance(double tickChance) {
            this.tickChance = tickChance;
            return this;
        }

        /**
         * Builds the ambient additions settings record.
         * @return the ambient additions settings record
         * @since 2.4.1
         */
        @AsOf("2.4.1")
        public AmbientAdditionsSettings build() {
            Preconditions.checkNotNull(soundEvent, "soundEvent must not be null");
            return of(soundEvent, tickChance);
        }
    }
}
