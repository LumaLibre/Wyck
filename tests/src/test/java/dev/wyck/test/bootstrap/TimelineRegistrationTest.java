package dev.wyck.test.bootstrap;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import dev.wyck.environment.attribute.EnvironmentAttributes;
import dev.wyck.environment.attribute.modifier.AttributeOperation;
import dev.wyck.keys.ResourceKey;
import dev.wyck.level.dimension.clock.TimeMarker;
import dev.wyck.level.dimension.clock.WorldClock;
import dev.wyck.level.dimension.timeline.Easing;
import dev.wyck.level.dimension.timeline.Timeline;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryOps;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@NullMarked
@ExtendWith(MinecraftBootstrap.class)
class TimelineRegistrationTest {

    private static Registry<net.minecraft.world.timeline.Timeline> timelines() {
        return BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.TIMELINE);
    }

    private static JsonElement encode(ResourceKey key) {
        net.minecraft.world.timeline.Timeline timeline = timelines().getValue(key.<Identifier>identifier());
        assertNotNull(timeline, "timeline " + key + " is not registered");

        RegistryOps<JsonElement> ops = BootstrapSafeMinecraftRegistries.serialization()
            .createSerializationContext(JsonOps.INSTANCE);

        return net.minecraft.world.timeline.Timeline.DIRECT_CODEC.encodeStart(ops, timeline).getOrThrow();
    }

    @Test
    void registerWritesTheTimelineIntoTheTimelineRegistry() {
        ResourceKey key = ResourceKey.of("wyck:test_day");

        Timeline.builder()
            .key(key)
            .clock(WorldClock.OVERWORLD)
            .periodTicks(24000)
            .timeMarker(TimeMarker.NOON, 6000, true)
            .track(EnvironmentAttributes.SUN_ANGLE, track -> track
                .easing(Easing.symmetricCubicBezier(0.362F, 0.241F))
                .keyframe(6000, 360.0F)
                .keyframe(6000, 0.0F))
            .register();

        JsonElement json = encode(key);

        assertEquals("minecraft:overworld", json.getAsJsonObject().get("clock").getAsString());
        assertEquals(24000, json.getAsJsonObject().get("period_ticks").getAsInt());

        JsonElement marker = json.getAsJsonObject().getAsJsonObject("time_markers").get("minecraft:noon");
        assertEquals(6000, marker.getAsJsonObject().get("ticks").getAsInt());
        assertTrue(marker.getAsJsonObject().get("show_in_commands").getAsBoolean());

        JsonElement track = json.getAsJsonObject().getAsJsonObject("tracks").get("minecraft:visual/sun_angle");
        assertEquals(
            "[{\"ticks\":6000,\"value\":360.0},{\"ticks\":6000,\"value\":0.0}]",
            track.getAsJsonObject().get("keyframes").toString());
        assertEquals("[0.362,0.241,0.638,0.759]", track.getAsJsonObject().getAsJsonObject("ease").get("cubic_bezier").toString());
    }

    @Test
    void colorTracksEncodeTheirModifierAndHexValues() {
        ResourceKey key = ResourceKey.of("wyck:test_fog");

        Timeline.builder()
            .key(key)
            .clock(WorldClock.OVERWORLD)
            .periodTicks(24000)
            .track(EnvironmentAttributes.FOG_COLOR, AttributeOperation.MULTIPLY, track -> track
                .keyframe(133, "#FFFFFF")
                .keyframe(13670, "#0D0D17"))
            .register();

        JsonElement track = encode(key).getAsJsonObject().getAsJsonObject("tracks").get("minecraft:visual/fog_color");

        assertEquals("multiply", track.getAsJsonObject().get("modifier").getAsString());
        assertEquals(
            "[{\"ticks\":133,\"value\":\"#ffffff\"},{\"ticks\":13670,\"value\":\"#0d0d17\"}]",
            track.getAsJsonObject().get("keyframes").toString());
    }

    @Test
    void booleanTracksEncodeTheirModifier() {
        ResourceKey key = ResourceKey.of("wyck:test_fireflies");

        Timeline.builder()
            .key(key)
            .clock(WorldClock.OVERWORLD)
            .periodTicks(24000)
            .track(EnvironmentAttributes.FIREFLY_BUSH_SOUNDS, AttributeOperation.OR, track -> track
                .easing(Easing.IN_OUT_SINE)
                .keyframe(12600, true)
                .keyframe(23401, false))
            .register();

        JsonElement track = encode(key).getAsJsonObject().getAsJsonObject("tracks").get("minecraft:audio/firefly_bush_sounds");

        assertEquals("or", track.getAsJsonObject().get("modifier").getAsString());
        assertEquals("in_out_sine", track.getAsJsonObject().get("ease").getAsString());
        assertEquals(
            "[{\"ticks\":12600,\"value\":true},{\"ticks\":23401,\"value\":false}]",
            track.getAsJsonObject().get("keyframes").toString());
    }

    @Test
    void alphaBlendKeyframesCarryTheirAlpha() {
        ResourceKey key = ResourceKey.of("wyck:test_alpha");

        Timeline.builder()
            .key(key)
            .clock(WorldClock.OVERWORLD)
            .periodTicks(24000)
            .track(EnvironmentAttributes.STAR_BRIGHTNESS, AttributeOperation.ALPHA_BLEND, track -> track
                .keyframe(0, 1.0F, 0.5F))
            .register();

        JsonElement track = encode(key).getAsJsonObject().getAsJsonObject("tracks").get("minecraft:visual/star_brightness");

        assertEquals("alpha_blend", track.getAsJsonObject().get("modifier").getAsString());
        assertEquals(
            "[{\"ticks\":0,\"value\":{\"value\":1.0,\"alpha\":0.5}}]",
            track.getAsJsonObject().get("keyframes").toString());
    }

    @Test
    void anOperationTheAttributeDoesNotSupportIsRejected() {
        Timeline timeline = Timeline.builder()
            .key(ResourceKey.of("wyck:test_bad_operation"))
            .clock(WorldClock.OVERWORLD)
            .track(EnvironmentAttributes.FIREFLY_BUSH_SOUNDS, AttributeOperation.MULTIPLY, track -> track.keyframe(0, true))
            .build();

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, timeline::toMinecraft);
        assertTrue(thrown.getMessage().contains("MULTIPLY"), thrown.getMessage());
    }
}
