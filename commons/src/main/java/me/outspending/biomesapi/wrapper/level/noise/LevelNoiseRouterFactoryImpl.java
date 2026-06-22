package me.outspending.biomesapi.wrapper.level.noise;

import me.outspending.biomesapi.annotations.WireFactory;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@ApiStatus.Internal
public class LevelNoiseRouterFactoryImpl implements LevelNoiseRouter.Factory {

    @Override
    public LevelNoiseRouter create(LevelNoiseRouter.Slots slots) {
        return new LevelNoiseRouterImpl(slots);
    }
}