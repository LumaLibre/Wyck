package dev.wyck.worldgen.carver.types;

import dev.wyck.worldgen.carver.custom.CustomCarver;
import dev.wyck.worldgen.carver.custom.CustomCarverBridge;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record CustomComposedCarverImpl<C>(
    @Override CustomCarver<C> carver,
    @Override C config
) implements CustomComposedCarver<C> {

    @Override
    public Object toMinecraft() {
        return net.minecraft.core.Holder.direct(new CustomCarverBridge<>(carver, config));
    }

    @Override
    public Key key() {
        return this.carver.key();
    }
}
