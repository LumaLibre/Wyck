package me.outspending.biomesapi.wrapper.environment;

import me.outspending.biomesapi.api.annotations.AsOf;

/**
 * This enum represents the phases of the moon in Minecraft.
 * Includes all 8 moon phases mapped to their underlying Minecraft delegate.
 *
 * @version 1.1.0
 * @since 1.1.0
 * @author Jsinco
 */
@AsOf("1.1.0")
public enum MoonPhase {

    FULL_MOON(net.minecraft.world.level.MoonPhase.FULL_MOON),
    WANING_GIBBOUS(net.minecraft.world.level.MoonPhase.WANING_GIBBOUS),
    THIRD_QUARTER(net.minecraft.world.level.MoonPhase.THIRD_QUARTER),
    WANING_CRESCENT(net.minecraft.world.level.MoonPhase.WANING_CRESCENT),
    NEW_MOON(net.minecraft.world.level.MoonPhase.NEW_MOON),
    WAXING_CRESCENT(net.minecraft.world.level.MoonPhase.WAXING_CRESCENT),
    FIRST_QUARTER(net.minecraft.world.level.MoonPhase.FIRST_QUARTER),
    WAXING_GIBBOUS(net.minecraft.world.level.MoonPhase.WAXING_GIBBOUS);

    private final net.minecraft.world.level.MoonPhase delegatePhase;

    @AsOf("1.1.0")
    MoonPhase(net.minecraft.world.level.MoonPhase delegatePhase) {
        this.delegatePhase = delegatePhase;
    }

    /**
     * Gets the underlying delegate moon phase.
     * @return the delegate moon phase
     */
    @AsOf("1.1.0")
    public net.minecraft.world.level.MoonPhase getDelegatePhase() {
        return delegatePhase;
    }

    /**
     * Converts a delegate moon phase to its corresponding MoonPhase enum value.
     * @param delegatePhase the delegate moon phase
     * @return the corresponding MoonPhase enum value
     * @throws IllegalArgumentException if the delegate moon phase is unknown
     */
    @AsOf("1.1.0")
    public static MoonPhase fromDelegate(net.minecraft.world.level.MoonPhase delegatePhase) throws IllegalArgumentException {
        for (MoonPhase phase : values()) {
            if (phase.getDelegatePhase() == delegatePhase) {
                return phase;
            }
        }
        throw new IllegalArgumentException("Unknown delegate moon phase: " + delegatePhase);
    }
}
