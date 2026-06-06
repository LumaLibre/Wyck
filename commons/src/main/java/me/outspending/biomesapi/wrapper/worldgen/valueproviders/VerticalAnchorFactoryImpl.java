package me.outspending.biomesapi.wrapper.worldgen.valueproviders;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
public final class VerticalAnchorFactoryImpl implements VerticalAnchor.Factory {

    @Override
    public @NotNull Object toNms(@NotNull VerticalAnchor anchor) {
        return switch (anchor) {
            case VerticalAnchor.Absolute abs -> net.minecraft.world.level.levelgen.VerticalAnchor.absolute(abs.y());
            case VerticalAnchor.AboveBottom above -> net.minecraft.world.level.levelgen.VerticalAnchor.aboveBottom(above.offset());
            case VerticalAnchor.BelowTop below -> net.minecraft.world.level.levelgen.VerticalAnchor.belowTop(below.offset());
        };
    }
}