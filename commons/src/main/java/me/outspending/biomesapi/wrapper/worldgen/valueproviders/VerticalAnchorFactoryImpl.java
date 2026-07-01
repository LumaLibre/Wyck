package me.outspending.biomesapi.wrapper.worldgen.valueproviders;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
public final class VerticalAnchorFactoryImpl implements VerticalAnchor.Factory {

    @Override
    public Object toNms(VerticalAnchor anchor) {
        return switch (anchor) {
            case VerticalAnchor.Absolute abs -> net.minecraft.world.level.levelgen.VerticalAnchor.absolute(abs.y());
            case VerticalAnchor.AboveBottom above -> net.minecraft.world.level.levelgen.VerticalAnchor.aboveBottom(above.offset());
            case VerticalAnchor.BelowTop below -> net.minecraft.world.level.levelgen.VerticalAnchor.belowTop(below.offset());
        };
    }

    @Override
    public VerticalAnchor fromMinecraft(Object nms) {
        net.minecraft.world.level.levelgen.VerticalAnchor anchor = (net.minecraft.world.level.levelgen.VerticalAnchor) nms;
        return switch (anchor) {
            case net.minecraft.world.level.levelgen.VerticalAnchor.Absolute(int y) -> VerticalAnchor.absolute(y);
            case net.minecraft.world.level.levelgen.VerticalAnchor.AboveBottom(int offset) -> VerticalAnchor.aboveBottom(offset);
            case net.minecraft.world.level.levelgen.VerticalAnchor.BelowTop(int offset) -> VerticalAnchor.belowTop(offset);
            default -> throw new IllegalStateException("Unexpected value: " + anchor.getClass().getSimpleName());
        };
    }
}