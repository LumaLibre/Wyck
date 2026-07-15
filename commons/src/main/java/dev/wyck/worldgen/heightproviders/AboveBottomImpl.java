package dev.wyck.worldgen.heightproviders;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record AboveBottomImpl(@Override int offset) implements AboveBottom {

    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.VerticalAnchor.aboveBottom(offset);
    }
}
