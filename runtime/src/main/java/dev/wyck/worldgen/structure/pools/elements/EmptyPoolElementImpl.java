package dev.wyck.worldgen.structure.pools.elements;

import dev.wyck.worldgen.structure.pools.Projection;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class EmptyPoolElementImpl implements EmptyPoolElement {

    @Override
    public Projection projection() {
        return Projection.RIGID;
    }

    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.structure.pools.EmptyPoolElement.INSTANCE;
    }
}
