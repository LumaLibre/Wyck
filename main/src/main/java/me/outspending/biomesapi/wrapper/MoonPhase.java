package me.outspending.biomesapi.wrapper;

public enum MoonPhase {
    //    FULL_MOON(0, "full_moon"),
    //    WANING_GIBBOUS(1, "waning_gibbous"),
    //    THIRD_QUARTER(2, "third_quarter"),
    //    WANING_CRESCENT(3, "waning_crescent"),
    //    NEW_MOON(4, "new_moon"),
    //    WAXING_CRESCENT(5, "waxing_crescent"),
    //    FIRST_QUARTER(6, "first_quarter"),
    //    WAXING_GIBBOUS(7, "waxing_gibbous");

    FULL_MOON(net.minecraft.world.level.MoonPhase.FULL_MOON),
    WANING_GIBBOUS(net.minecraft.world.level.MoonPhase.WANING_GIBBOUS),
    THIRD_QUARTER(net.minecraft.world.level.MoonPhase.THIRD_QUARTER),
    WANING_CRESCENT(net.minecraft.world.level.MoonPhase.WANING_CRESCENT),
    NEW_MOON(net.minecraft.world.level.MoonPhase.NEW_MOON),
    WAXING_CRESCENT(net.minecraft.world.level.MoonPhase.WAXING_CRESCENT),
    FIRST_QUARTER(net.minecraft.world.level.MoonPhase.FIRST_QUARTER),
    WAXING_GIBBOUS(net.minecraft.world.level.MoonPhase.WAXING_GIBBOUS);

    private final net.minecraft.world.level.MoonPhase delegatePhase;

    MoonPhase(net.minecraft.world.level.MoonPhase delegatePhase) {
        this.delegatePhase = delegatePhase;
    }

    public net.minecraft.world.level.MoonPhase getDelegatePhase() {
        return delegatePhase;
    }
}
