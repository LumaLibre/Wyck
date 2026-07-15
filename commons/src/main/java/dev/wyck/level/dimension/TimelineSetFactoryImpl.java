package dev.wyck.level.dimension;

import dev.wyck.annotations.WireFactory;
import dev.wyck.keys.ResourceKey;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@WireFactory
@ApiStatus.Internal
public class TimelineSetFactoryImpl implements TimelineSetImpl.Factory {
    @Override
    public TimelineSetImpl create(List<ResourceKey> keys) {
        return new TimelineSetImpl(keys);
    }
}
