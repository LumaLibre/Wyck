package dev.wyck.level.dimension.timeline;

import dev.wyck.level.dimension.timeline.Keyframe;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record KeyframeImpl<V>(@Override int ticks, @Override V value) implements Keyframe<V> {

    @Override
    public Object toMinecraft() {
        return new net.minecraft.util.Keyframe<>(this.ticks, this.value);
    }
}
