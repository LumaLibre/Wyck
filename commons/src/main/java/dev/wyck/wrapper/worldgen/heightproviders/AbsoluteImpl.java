package dev.wyck.wrapper.worldgen.heightproviders;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record AbsoluteImpl(@Override int y) implements Absolute {

    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.VerticalAnchor.absolute(y);
    }
}
