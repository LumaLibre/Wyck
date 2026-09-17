package dev.wyck.environment.attribute.modifier;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record AlphaValueImpl(@Override float value, @Override float alpha) implements AlphaValue {

    @Override
    public net.minecraft.world.attribute.modifier.FloatWithAlpha toMinecraft() {
        return new net.minecraft.world.attribute.modifier.FloatWithAlpha(this.value, this.alpha);
    }
}