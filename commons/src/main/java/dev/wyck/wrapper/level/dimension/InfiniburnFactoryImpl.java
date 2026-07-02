package dev.wyck.wrapper.level.dimension;

import dev.wyck.annotations.WireFactory;
import dev.wyck.keys.ResourceKey;
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
