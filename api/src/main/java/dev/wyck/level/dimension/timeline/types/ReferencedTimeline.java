package dev.wyck.level.dimension.timeline.types;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.factory.WireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.level.dimension.timeline.Timeline;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("3.2.0")
public interface ReferencedTimeline extends Timeline {
    @ApiStatus.Internal
    ConstructWireProvider<ReferencedTimeline> WIRE = WireProvider.construct("dev.wyck.level.dimension.timeline.types.ReferencedTimelineImpl");

    /**
     * Creates a new timeline reference with the specified key.
     * @param key the key of the timeline to reference
     * @return a new timeline reference with the specified key
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    static ReferencedTimeline of(ResourceKey key) {
        return WIRE.construct(key);
    }
}
