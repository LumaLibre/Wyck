package dev.wyck.wrapper.environment.sounds;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.internal.NmsHandle;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
@AsOf("2.4.1")
public interface AmbientMoodSettings extends NmsHandle {

    @ApiStatus.Internal
    ConstructWireProvider<AmbientMoodSettings> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.environment.sounds.AmbientMoodSettingsImpl");

    /**
     * Gets the sound event of the ambient mood settings.
     * @return the sound event of the ambient mood settings
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    SoundEvent soundEvent();

    /**
     * Gets the tick delay of the ambient mood settings.
     * @return the tick delay of the ambient mood settings
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    int tickDelay();

    /**
     * Gets the block search extent of the ambient mood settings.
     * @return the block search extent of the ambient mood settings
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    int blockSearchExtent();

    /**
     * Gets the sound position offset of the ambient mood settings.
     * @return the sound position offset of the ambient mood settings
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    double soundPositionOffset();


    /**
     * Creates a new ambient mood settings record.
     * @param soundEvent the sound event of the ambient mood settings
     * @param tickDelay the tick delay of the ambient mood settings
     * @param blockSearchExtent the block search extent of the ambient mood settings
     * @param soundPositionOffset the sound position offset of the ambient mood settings
     * @return a new ambient mood settings record
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    static AmbientMoodSettings of(SoundEvent soundEvent, int tickDelay, int blockSearchExtent, double soundPositionOffset) {
        return WIRE.construct(soundEvent, tickDelay, blockSearchExtent, soundPositionOffset);
    }

    /**
     * Creates a new ambient mood settings record builder.
     * @return a new ambient mood settings record builder
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Creates a new ambient mood settings record builder.
     * @since 2.4.1
     * @version 2.4.1
     * @author Jsinco
     */
    @AsOf("2.4.1")
    final class Builder {
        private @Nullable SoundEvent soundEvent;
        private int tickDelay;
        private int blockSearchExtent;
        private double soundPositionOffset;

        /**
         * Sets the sound event of the ambient mood settings.
         * @param soundEvent the sound event of the ambient mood settings
         * @return this builder
         * @since 2.4.1
         */
        @AsOf("2.4.1")
        public Builder soundEvent(SoundEvent soundEvent) {
            this.soundEvent = soundEvent;
            return this;
        }

        /**
         * Sets the tick delay of the ambient mood settings.
         * @param tickDelay the tick delay of the ambient mood settings
         * @return this builder
         * @since 2.4.1
         */
        @AsOf("2.4.1")
        public Builder tickDelay(int tickDelay) {
            this.tickDelay = tickDelay;
            return this;
        }

        /**
         * Sets the block search extent of the ambient mood settings.
         * @param blockSearchExtent the block search extent of the ambient mood settings
         * @return this builder
         * @since 2.4.1
         */
        @AsOf("2.4.1")
        public Builder blockSearchExtent(int blockSearchExtent) {
            this.blockSearchExtent = blockSearchExtent;
            return this;
        }

        /**
         * Sets the sound position offset of the ambient mood settings.
         * @param soundPositionOffset the sound position offset of the ambient mood settings
         * @return this builder
         * @since 2.4.1
         */
        @AsOf("2.4.1")
        public Builder soundPositionOffset(double soundPositionOffset) {
            this.soundPositionOffset = soundPositionOffset;
            return this;
        }

        /**
         * Builds the ambient mood settings record.
         * @return the ambient mood settings record
         * @since 2.4.1
         */
        @AsOf("2.4.1")
        public AmbientMoodSettings build() {
            Preconditions.checkNotNull(soundEvent, "soundEvent must not be null");
            return of(soundEvent, tickDelay, blockSearchExtent, soundPositionOffset);
        }
    }
}
