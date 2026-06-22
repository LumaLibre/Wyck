package me.outspending.biomesapi.wrapper.level.dimension;

import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.keys.ResourceKey;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@ApiStatus.Internal
public class InfiniburnFactoryImpl implements Infiniburn.Factory {
    @Override
    public Infiniburn create(ResourceKey tag) {
        return new InfiniburnImpl(tag);
    }
}
