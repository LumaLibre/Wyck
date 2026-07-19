//ff32c77be77b8131edb14362ea48289a
package dev.wyck.level.dimension.timeline;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.Generated;
import dev.wyck.keys.ResourceKey;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Auto-generated. Do not modify!
 * Run ./gradlew generateSources to regenerate.
 * <p>
 * The easing functions vanilla registers for timeline keyframe tracks.
 * Inherited by {@link Easing}, which is where these are used from.
 * </p>
 *
 * @since 3.2.0
 * @version 3.2.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("3.2.0")
@Generated("2026-07-19T05:19:17.097892Z")
@ApiStatus.NonExtendable
public interface EasingType {

    // From: EasingType 
    @AsOf("3.2.0")
    Easing CONSTANT = reference("constant");
    @AsOf("3.2.0")
    Easing LINEAR = reference("linear");
    @AsOf("3.2.0")
    Easing IN_BACK = reference("in_back");
    @AsOf("3.2.0")
    Easing IN_BOUNCE = reference("in_bounce");
    @AsOf("3.2.0")
    Easing IN_CIRC = reference("in_circ");
    @AsOf("3.2.0")
    Easing IN_CUBIC = reference("in_cubic");
    @AsOf("3.2.0")
    Easing IN_ELASTIC = reference("in_elastic");
    @AsOf("3.2.0")
    Easing IN_EXPO = reference("in_expo");
    @AsOf("3.2.0")
    Easing IN_QUAD = reference("in_quad");
    @AsOf("3.2.0")
    Easing IN_QUART = reference("in_quart");
    @AsOf("3.2.0")
    Easing IN_QUINT = reference("in_quint");
    @AsOf("3.2.0")
    Easing IN_SINE = reference("in_sine");
    @AsOf("3.2.0")
    Easing IN_OUT_BACK = reference("in_out_back");
    @AsOf("3.2.0")
    Easing IN_OUT_BOUNCE = reference("in_out_bounce");
    @AsOf("3.2.0")
    Easing IN_OUT_CIRC = reference("in_out_circ");
    @AsOf("3.2.0")
    Easing IN_OUT_CUBIC = reference("in_out_cubic");
    @AsOf("3.2.0")
    Easing IN_OUT_ELASTIC = reference("in_out_elastic");
    @AsOf("3.2.0")
    Easing IN_OUT_EXPO = reference("in_out_expo");
    @AsOf("3.2.0")
    Easing IN_OUT_QUAD = reference("in_out_quad");
    @AsOf("3.2.0")
    Easing IN_OUT_QUART = reference("in_out_quart");
    @AsOf("3.2.0")
    Easing IN_OUT_QUINT = reference("in_out_quint");
    @AsOf("3.2.0")
    Easing IN_OUT_SINE = reference("in_out_sine");
    @AsOf("3.2.0")
    Easing OUT_BACK = reference("out_back");
    @AsOf("3.2.0")
    Easing OUT_BOUNCE = reference("out_bounce");
    @AsOf("3.2.0")
    Easing OUT_CIRC = reference("out_circ");
    @AsOf("3.2.0")
    Easing OUT_CUBIC = reference("out_cubic");
    @AsOf("3.2.0")
    Easing OUT_ELASTIC = reference("out_elastic");
    @AsOf("3.2.0")
    Easing OUT_EXPO = reference("out_expo");
    @AsOf("3.2.0")
    Easing OUT_QUAD = reference("out_quad");
    @AsOf("3.2.0")
    Easing OUT_QUART = reference("out_quart");
    @AsOf("3.2.0")
    Easing OUT_QUINT = reference("out_quint");
    @AsOf("3.2.0")
    Easing OUT_SINE = reference("out_sine");

    private static Easing reference(String path) {
        return Easing.of(ResourceKey.minecraft(path));
    }
}
