package me.outspending.biomesapi.wrapper.level.dimension;

import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.keys.ResourceKey;
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
