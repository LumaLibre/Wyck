package me.outspending.biomesapi.wrapper.environment;

import me.outspending.biomesapi.annotations.AsOf;

/**
 * This enum represents the various activities that entities in Minecraft can perform.
 * Each enum value corresponds to an Activity value in the {@link net.minecraft.world.entity.schedule.Activity} class.
 *
 * @version 1.1.0
 * @since 1.1.0
 * @author Jsinco
 */
@AsOf("1.1.0")
public enum Activity {

    CORE(net.minecraft.world.entity.schedule.Activity.CORE),
    IDLE(net.minecraft.world.entity.schedule.Activity.IDLE),
    WORK(net.minecraft.world.entity.schedule.Activity.WORK),
    PLAY(net.minecraft.world.entity.schedule.Activity.PLAY),
    REST(net.minecraft.world.entity.schedule.Activity.REST),
    MEET(net.minecraft.world.entity.schedule.Activity.MEET),
    PANIC(net.minecraft.world.entity.schedule.Activity.PANIC),
    RAID(net.minecraft.world.entity.schedule.Activity.RAID),
    PRE_RAID(net.minecraft.world.entity.schedule.Activity.PRE_RAID),
    HIDE(net.minecraft.world.entity.schedule.Activity.HIDE),
    FIGHT(net.minecraft.world.entity.schedule.Activity.FIGHT),
    CELEBRATE(net.minecraft.world.entity.schedule.Activity.CELEBRATE),
    ADMIRE_ITEM(net.minecraft.world.entity.schedule.Activity.ADMIRE_ITEM),
    AVOID(net.minecraft.world.entity.schedule.Activity.AVOID),
    RIDE(net.minecraft.world.entity.schedule.Activity.RIDE),
    PLAY_DEAD(net.minecraft.world.entity.schedule.Activity.PLAY_DEAD),
    LONG_JUMP(net.minecraft.world.entity.schedule.Activity.LONG_JUMP),
    RAM(net.minecraft.world.entity.schedule.Activity.RAM),
    TONGUE(net.minecraft.world.entity.schedule.Activity.TONGUE),
    SWIM(net.minecraft.world.entity.schedule.Activity.SWIM),
    LAY_SPAWN(net.minecraft.world.entity.schedule.Activity.LAY_SPAWN),
    SNIFF(net.minecraft.world.entity.schedule.Activity.SNIFF),
    INVESTIGATE(net.minecraft.world.entity.schedule.Activity.INVESTIGATE),
    ROAR(net.minecraft.world.entity.schedule.Activity.ROAR),
    EMERGE(net.minecraft.world.entity.schedule.Activity.EMERGE),
    DIG(net.minecraft.world.entity.schedule.Activity.DIG);

    private final net.minecraft.world.entity.schedule.Activity delegateActivity;

    @AsOf("1.1.0")
    Activity(net.minecraft.world.entity.schedule.Activity delegateActivity) {
        this.delegateActivity = delegateActivity;
    }

    /**
     * This method returns the Activity that corresponds to the enum value.
     * @return The Activity that corresponds to the enum value.
     */
    @AsOf("1.1.0")
    public net.minecraft.world.entity.schedule.Activity getDelegateActivity() {
        return delegateActivity;
    }
}
