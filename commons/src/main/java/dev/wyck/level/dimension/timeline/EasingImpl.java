package dev.wyck.level.dimension.timeline;

import com.google.gson.JsonPrimitive;
import com.mojang.serialization.JsonOps;
import dev.wyck.level.dimension.timeline.Easing;
import net.minecraft.util.EasingType;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record EasingImpl(@Override String id) implements Easing {

    @Override
    public EasingType toMinecraft() {
        return EasingType.CODEC.parse(JsonOps.INSTANCE, new JsonPrimitive(this.id))
            .getOrThrow(error -> new IllegalArgumentException("Unknown easing '" + this.id + "': " + error));
    }

    public record CubicBezier(float x1, float y1, float x2, float y2) implements Easing {

        @Override
        public String id() {
            return "cubic_bezier";
        }

        @Override
        public EasingType toMinecraft() {
            return EasingType.cubicBezier(this.x1, this.y1, this.x2, this.y2);
        }
    }
}
