package dev.wyck.environment.attribute.modifier;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record GrayBlendImpl(@Override float brightness, @Override float factor) implements GrayBlend {

    @Override
    public net.minecraft.world.attribute.modifier.ColorModifier.BlendToGray toMinecraft() {
        return new net.minecraft.world.attribute.modifier.ColorModifier.BlendToGray(this.brightness, this.factor);
    }
}