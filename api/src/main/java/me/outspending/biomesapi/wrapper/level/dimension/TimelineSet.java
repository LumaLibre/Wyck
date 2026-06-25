package me.outspending.biomesapi.wrapper.level.dimension;

import com.mojang.serialization.Codec;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

/**
 * Wraps Minecraft's {@code TimelineSet}.
 *
 * @since 2.4.0
 * @version 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
public interface TimelineSet extends NmsHandle {

    Codec<TimelineSet> CODEC = Codec.list(ResourceKey.CODEC).xmap(
        keys -> TimelineSet.of(keys.toArray(new ResourceKey[0])),
        TimelineSet::timelines
    );

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.level.dimension.TimelineSetFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        TimelineSet create(List<ResourceKey> keys);
    }

    ResourceKey OVERWORLD_DAY = ResourceKey.minecraft("day");
    ResourceKey MOON = ResourceKey.minecraft("moon");
    ResourceKey VILLAGER_SCHEDULE = ResourceKey.minecraft("villager_schedule");
    ResourceKey EARLY_GAME = ResourceKey.minecraft("early_game");

    TimelineSet OVERWORLD = of(OVERWORLD_DAY, MOON, VILLAGER_SCHEDULE);
    TimelineSet EMPTY = of();


    /**
     * The timelines that this set contains.
     * @return the timelines
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    List<ResourceKey> timelines();


    @AsOf("2.4.0")
    static TimelineSet of(ResourceKey... keys) {
        return WIRE.get().create(List.of(keys));
    }
}