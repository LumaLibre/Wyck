package dev.wyck.level.dimension.timeline;

import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.level.dimension.timeline.types.ComposedTimeline;
import dev.wyck.level.dimension.timeline.types.ReferencedTimeline;
import dev.wyck.wrapper.Wrapper;
import net.kyori.adventure.key.Keyed;
import org.jspecify.annotations.NullMarked;

import java.util.Set;

@NullMarked
@AsOf("3.2.0")
public interface Timeline extends Wrapper, Keyed {

    Timeline OVERWORLD_DAY = reference(ResourceKey.minecraft("day"));
    Timeline MOON = reference(ResourceKey.minecraft("moon"));
    Timeline VILLAGER_SCHEDULE = reference(ResourceKey.minecraft("villager_schedule"));
    Timeline EARLY_GAME = reference(ResourceKey.minecraft("early_game"));
    Set<Timeline> OVERWORLD = Set.of(OVERWORLD_DAY, MOON, VILLAGER_SCHEDULE);

    /**
     * The registry key of this timeline.
     * @return the registry key of this timeline
     * @since 3.2.0
     */
    @Override
    @AsOf("3.2.0")
    ResourceKey key();

    /**
     * Creates a timeline reference to the given timeline.
     * @param key the key of the timeline to reference
     * @return a timeline reference to the given timeline
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    static ReferencedTimeline reference(ResourceKey key) {
        return ReferencedTimeline.of(key);
    }

    /**
     * Creates a new timeline builder.
     * @return a new timeline builder
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    static ComposedTimeline.Builder builder() {
        return ComposedTimeline.builder();
    }
}
