package dev.wyck.level.dimension.timeline;

import dev.wyck.environment.attribute.EnvironmentAttribute;
import dev.wyck.environment.attribute.modifier.AlphaValue;
import dev.wyck.environment.attribute.modifier.AttributeOperation;
import dev.wyck.environment.attribute.modifier.GrayBlend;
import dev.wyck.level.dimension.timeline.AttributeTrack;
import dev.wyck.level.dimension.timeline.Easing;
import dev.wyck.level.dimension.timeline.Keyframe;
import dev.wyck.wrapper.Wrapper;
import net.minecraft.util.KeyframeTrack;
import net.minecraft.world.attribute.modifier.AttributeModifier;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@ApiStatus.Internal
@SuppressWarnings("unchecked")
public record AttributeTrackImpl<V, A>(
    @Override EnvironmentAttribute<V> attribute,
    @Override AttributeOperation operation,
    @Override Easing easing,
    @Override List<Keyframe<?>> keyframes
) implements AttributeTrack<V> {

    @Override
    public net.minecraft.world.timeline.AttributeTrack<?, ?> toMinecraft() {
        net.minecraft.world.attribute.EnvironmentAttribute<V> nms = attribute.asHandle();
        AttributeModifier<V, A> modifier = modifier(nms);
        net.minecraft.util.KeyframeTrack.Builder<A> keyframes = new KeyframeTrack.Builder<>();

        keyframes.setEasing(easing.asHandle());
        for (Keyframe<?> keyframe : this.keyframes) {
            keyframes.addKeyframe(keyframe.ticks(), argument((V) keyframe.value()));
        }
        return new net.minecraft.world.timeline.AttributeTrack<>(modifier, keyframes.build());
    }


    private AttributeModifier<V, A> modifier(net.minecraft.world.attribute.EnvironmentAttribute<V> nms) {
        if (this.operation == AttributeOperation.OVERRIDE) {
            return (AttributeModifier<V, A>) AttributeModifier.override();
        }

        AttributeModifier.OperationId id = this.operation.toNms(AttributeModifier.OperationId.class);
        AttributeModifier<V, ?> modifier = nms.type().modifierLibrary().get(id);
        if (modifier == null) {
            throw new IllegalArgumentException(
                "Operation " + this.operation + " is not supported by " + this.attribute.key()
                    + "; supported operations are " + nms.type().modifierLibrary().keySet());
        }
        return (AttributeModifier<V, A>) modifier;
    }

    private A argument(V value) {
        if (value instanceof AlphaValue || value instanceof GrayBlend) {
            return ((Wrapper) value).asHandle();
        }
        this.attribute.value(value);
        return this.attribute.minecraftValue();
    }
}
