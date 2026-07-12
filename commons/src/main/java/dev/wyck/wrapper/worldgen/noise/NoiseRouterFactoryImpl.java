package dev.wyck.wrapper.worldgen.noise;

import dev.wyck.annotations.WireFactory;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@ApiStatus.Internal
public class NoiseRouterFactoryImpl implements NoiseRouter.Factory {

    @Override
    public NoiseRouter create(NoiseRouter.Slots slots) {
        return new NoiseRouterImpl(slots);
    }
}