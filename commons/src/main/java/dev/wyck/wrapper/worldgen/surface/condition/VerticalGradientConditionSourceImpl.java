package dev.wyck.wrapper.worldgen.surface.condition;

import dev.wyck.wrapper.worldgen.valueproviders.VerticalAnchor;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record VerticalGradientConditionSourceImpl(
    @Override String randomName,
    @Override VerticalAnchor trueAtAndBelow,
    @Override VerticalAnchor falseAtAndAbove
) implements VerticalGradientConditionSource {
    @Override
    public Object toMinecraft() {
        net.minecraft.world.level.levelgen.VerticalAnchor nmsTrueAtAndBelow = this.trueAtAndBelow.asHandle();
        net.minecraft.world.level.levelgen.VerticalAnchor nmsFalseAtAndAbove = this.falseAtAndAbove.asHandle();
        return net.minecraft.world.level.levelgen.SurfaceRules.verticalGradient(this.randomName, nmsTrueAtAndBelow, nmsFalseAtAndAbove);
    }
}
