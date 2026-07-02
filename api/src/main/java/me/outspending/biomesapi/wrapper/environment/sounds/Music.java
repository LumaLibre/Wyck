package me.outspending.biomesapi.wrapper.environment.sounds;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.ConstructWireProvider;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Wraps the Music environment attribute's music record.
 *
 * @since 2.4.1
 * @version 2.4.1
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.1")
public interface Music extends NmsHandle {

    @ApiStatus.Internal
    ConstructWireProvider<Music> WIRE = ConstructWireProvider.create("me.outspending.biomesapi.wrapper.environment.sounds.MusicImpl");

    /**
     * Gets the sound of the music.
     * @return the sound of the music
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    SoundEvent sound();

    /**
     * Gets the minimum delay of the music.
     * @return the minimum delay of the music
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    int minDelay();

    /**
     * Gets the maximum delay of the music.
     * @return the maximum delay of the music
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    int maxDelay();

    /**
     * Gets whether the music should replace the current music.
     * @return whether the music should replace the current music
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    boolean replaceCurrentMusic();

    /**
     * Creates a new music record.
     * @param sound the sound of the music
     * @param minDelay the minimum delay of the music
     * @param maxDelay the maximum delay of the music
     * @param replaceCurrentMusic whether the music should replace the current music
     * @return a new music record
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    static Music of(SoundEvent sound, int minDelay, int maxDelay, boolean replaceCurrentMusic) {
        return WIRE.construct(sound, minDelay, maxDelay, replaceCurrentMusic);
    }

    /**
     * Creates a new music record builder.
     * @return a new music record builder
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    static Builder builder() {
        return new Builder();
    }


    /**
     * Builder for creating music records.
     * @since 2.4.1
     * @version 2.4.1
     * @author Jsinco
     */
    @AsOf("2.4.1")
    final class Builder {
        private @Nullable SoundEvent sound;
        private int minDelay;
        private int maxDelay;
        private boolean replaceCurrentMusic;

        /**
         * Sets the sound of the music.
         * @param sound the sound of the music
         * @return this builder
         * @since 2.4.1
         */
        @AsOf("2.4.1")
        public Builder sound(SoundEvent sound) {
            this.sound = sound;
            return this;
        }

        /**
         * Sets the minimum delay of the music.
         * @param minDelay the minimum delay of the music
         * @return this builder
         * @since 2.4.1
         */
        @AsOf("2.4.1")
        public Builder minDelay(int minDelay) {
            this.minDelay = minDelay;
            return this;
        }

        /**
         * Sets the maximum delay of the music.
         * @param maxDelay the maximum delay of the music
         * @return this builder
         * @since 2.4.1
         */
        @AsOf("2.4.1")
        public Builder maxDelay(int maxDelay) {
            this.maxDelay = maxDelay;
            return this;
        }

        /**
         * Sets whether the music should replace the current music.
         * @param replaceCurrentMusic whether the music should replace the current music
         * @return this builder
         * @since 2.4.1
         */
        @AsOf("2.4.1")
        public Builder replaceCurrentMusic(boolean replaceCurrentMusic) {
            this.replaceCurrentMusic = replaceCurrentMusic;
            return this;
        }

        /**
         * Builds the music record.
         * @return the music record
         * @since 2.4.1
         */
        @AsOf("2.4.1")
        public Music build() {
            Preconditions.checkNotNull(sound, "sound cannot be null");
            return of(sound, minDelay, maxDelay, replaceCurrentMusic);
        }
    }
}
