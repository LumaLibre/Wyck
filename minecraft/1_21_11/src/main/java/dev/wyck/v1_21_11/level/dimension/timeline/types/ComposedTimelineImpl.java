package dev.wyck.v1_21_11.level.dimension.timeline.types;

import dev.wyck.environment.attribute.modifier.AlphaValue;
import dev.wyck.environment.attribute.modifier.AttributeOperation;
import dev.wyck.environment.attribute.modifier.GrayBlend;
import dev.wyck.keys.ResourceKey;
import dev.wyck.level.dimension.clock.TimeMarker;
import dev.wyck.level.dimension.clock.WorldClock;
import dev.wyck.level.dimension.timeline.AttributeTrack;
import dev.wyck.level.dimension.timeline.Keyframe;
import dev.wyck.level.dimension.timeline.types.ComposedTimeline;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.Lazy;
import dev.wyck.wrapper.Wrapper;
import net.minecraft.util.KeyframeTrack;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.attribute.modifier.AttributeModifier;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record ComposedTimelineImpl(
    @Override ResourceKey key,
    @Override WorldClock clock,
    @Override Optional<Integer> periodTicks,
    @Override List<AttributeTrack<?>> tracks,
    @Override List<TimeMarker> timeMarkers
) implements ComposedTimeline {

    private static final Lazy<WyckRegistry> REGISTRY = WyckRegistry.lazy(RegistryId.TIMELINE);

    @Override
    public net.minecraft.world.timeline.Timeline toMinecraft() {
        net.minecraft.world.timeline.Timeline.Builder builder = net.minecraft.world.timeline.Timeline.builder();
        this.periodTicks.ifPresent(builder::setPeriodTicks);
        for (AttributeTrack<?> track : this.tracks) {
            applyTo(builder, track);
        }
        return builder.build();
    }

    @Override
    public ComposedTimeline register() {
        REGISTRY.get().register(this.key, this);
        return this;
    }

    // TODO: clean this up

    private static <Value, Argument> void applyTo(net.minecraft.world.timeline.Timeline.Builder builder, AttributeTrack<?> track) {
        EnvironmentAttribute<Value> nms = track.attribute().asHandle();
        AttributeModifier<Value, Argument> modifier = modifier(track, nms);
        builder.addModifierTrack(nms, modifier, keyframes -> fill(keyframes, track));
    }

    private static <A> void fill(KeyframeTrack.Builder<A> keyframes, AttributeTrack<?> track) {
        keyframes.setEasing(track.easing().asHandle());
        for (Keyframe<?> keyframe : track.keyframes()) {
            keyframes.addKeyframe(keyframe.ticks(), argument(track, keyframe.value()));
        }
    }

    @SuppressWarnings("unchecked")
    private static <V, A> AttributeModifier<V, A> modifier(AttributeTrack<?> track, EnvironmentAttribute<V> nms) {
        if (track.operation() == AttributeOperation.OVERRIDE) {
            return (AttributeModifier<V, A>) AttributeModifier.override();
        }

        AttributeModifier.OperationId id = track.operation().toNms(AttributeModifier.OperationId.class);
        AttributeModifier<V, ?> modifier = nms.type().modifierLibrary().get(id);
        if (modifier == null) {
            throw new IllegalArgumentException(
                "Operation " + track.operation() + " is not supported by " + track.attribute().key()
                    + "; supported operations are " + nms.type().modifierLibrary().keySet());
        }
        return (AttributeModifier<V, A>) modifier;
    }

    private static <V, A> A argument(AttributeTrack<V> track, Object value) {
        if (value instanceof AlphaValue || value instanceof GrayBlend) {
            return ((Wrapper) value).asHandle();
        }
        track.attribute().value((V) value);
        return track.attribute().minecraftValue();
    }
}
