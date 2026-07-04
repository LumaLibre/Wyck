package dev.wyck.wrapper.worldgen.surface;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.WireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.internal.Wrapper;
import dev.wyck.wrapper.worldgen.valueproviders.VerticalAnchor;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

/**
 * Wraps the surface rule condition family ({@code SurfaceRules.ConditionSource}), the predicates that
 * gate where a {@link SurfaceRule} applies.
 *
 * @version 2.4.0
 * @since 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
public sealed interface SurfaceCondition extends Wrapper permits SurfaceCondition.StoneDepth, SurfaceCondition.Not, SurfaceCondition.Water, SurfaceCondition.Biome, SurfaceCondition.Noise, SurfaceCondition.YCheck, SurfaceCondition.VerticalGradient, SurfaceCondition.Steep, SurfaceCondition.Hole, SurfaceCondition.AbovePreliminarySurface, SurfaceCondition.Temperature {

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("dev.wyck.*.wrapper.worldgen.surface.SurfaceConditionFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        Object toNms(SurfaceCondition condition);
    }

    /**
     * Which face of the stone column a depth check measures from.
     *
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    enum CaveSurface {
        FLOOR,
        CEILING
    }

    @Override
    @AsOf("2.4.0")
    default Object toMinecraft() {
        return WIRE.get().toNms(this);
    }

    /**
     * Matches within a given depth of stone from the floor or ceiling.
     *
     * @param offset the depth offset
     * @param addSurfaceDepth whether the biome surface depth is added to the offset
     * @param surfaceType the face to measure from
     * @return a stone-depth condition
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static SurfaceCondition stoneDepth(int offset, boolean addSurfaceDepth, CaveSurface surfaceType) {
        return new StoneDepth(offset, addSurfaceDepth, 0, surfaceType);
    }

    /**
     * Matches within a given depth of stone from the floor or ceiling, with a secondary depth range.
     *
     * @param offset the depth offset
     * @param addSurfaceDepth whether the biome surface depth is added to the offset
     * @param secondaryDepthRange the secondary depth range
     * @param surfaceType the face to measure from
     * @return a stone-depth condition
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static SurfaceCondition stoneDepth(int offset, boolean addSurfaceDepth, int secondaryDepthRange, CaveSurface surfaceType) {
        return new StoneDepth(offset, addSurfaceDepth, secondaryDepthRange, surfaceType);
    }

    /**
     * Inverts another condition.
     *
     * @param target the condition to invert
     * @return a negated condition
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static SurfaceCondition not(SurfaceCondition target) {
        return new Not(target);
    }

    /**
     * Matches relative to the water level at the current block.
     *
     * @param offset the offset from the water level
     * @param surfaceDepthMultiplier the surface depth multiplier
     * @return a water condition
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static SurfaceCondition waterBlockCheck(int offset, int surfaceDepthMultiplier) {
        return new Water(offset, surfaceDepthMultiplier, false);
    }

    /**
     * Matches relative to the water level at the column start.
     *
     * @param offset the offset from the water level
     * @param surfaceDepthMultiplier the surface depth multiplier
     * @return a water condition
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static SurfaceCondition waterStartCheck(int offset, int surfaceDepthMultiplier) {
        return new Water(offset, surfaceDepthMultiplier, true);
    }

    /**
     * Matches when the current biome is one of the given biomes.
     *
     * @param biomes the biome keys to match
     * @return a biome condition
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static SurfaceCondition isBiome(List<ResourceKey> biomes) {
        return new Biome(List.copyOf(biomes));
    }

    /**
     * Matches when a registered noise sampled at this position is at or above the threshold.
     *
     * @param noise the noise parameters key
     * @param minThreshold the lower threshold, inclusive
     * @return a noise condition
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static SurfaceCondition noiseCondition(ResourceKey noise, double minThreshold) {
        return new Noise(noise, minThreshold, Double.MAX_VALUE);
    }

    /**
     * Matches when a registered noise sampled at this position is within the threshold range.
     *
     * @param noise the noise parameters key
     * @param minThreshold the lower threshold, inclusive
     * @param maxThreshold the upper threshold, inclusive
     * @return a noise condition
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static SurfaceCondition noiseCondition(ResourceKey noise, double minThreshold, double maxThreshold) {
        return new Noise(noise, minThreshold, maxThreshold);
    }

    /**
     * Matches at or above a vertical anchor, evaluated per block.
     *
     * @param anchor the vertical anchor
     * @param surfaceDepthMultiplier the surface depth multiplier
     * @return a y condition
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static SurfaceCondition yBlockCheck(VerticalAnchor anchor, int surfaceDepthMultiplier) {
        return new YCheck(anchor, surfaceDepthMultiplier, false);
    }

    /**
     * Matches at or above a vertical anchor, evaluated at the column start.
     *
     * @param anchor the vertical anchor
     * @param surfaceDepthMultiplier the surface depth multiplier
     * @return a y condition
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static SurfaceCondition yStartCheck(VerticalAnchor anchor, int surfaceDepthMultiplier) {
        return new YCheck(anchor, surfaceDepthMultiplier, true);
    }

    /**
     * A noisy vertical gradient that is true below one anchor and false above another, fading between.
     *
     * @param randomName the seed name for the gradient noise
     * @param trueAtAndBelow the anchor at and below which the condition is true
     * @param falseAtAndAbove the anchor at and above which the condition is false
     * @return a vertical-gradient condition
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static SurfaceCondition verticalGradient(String randomName, VerticalAnchor trueAtAndBelow, VerticalAnchor falseAtAndAbove) {
        return new VerticalGradient(randomName, trueAtAndBelow, falseAtAndAbove);
    }

    /**
     * Matches on steep terrain.
     *
     * @return a steep condition
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static SurfaceCondition steep() {
        return Steep.INSTANCE;
    }

    /**
     * Matches in surface holes.
     *
     * @return a hole condition
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static SurfaceCondition hole() {
        return Hole.INSTANCE;
    }

    /**
     * Matches above the preliminary surface level.
     *
     * @return an above-preliminary-surface condition
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static SurfaceCondition abovePreliminarySurface() {
        return AbovePreliminarySurface.INSTANCE;
    }

    /**
     * Matches where the biome is cold enough to snow.
     *
     * @return a temperature condition
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static SurfaceCondition temperature() {
        return Temperature.INSTANCE;
    }

    @AsOf("2.4.0")
    record StoneDepth(int offset, boolean addSurfaceDepth, int secondaryDepthRange, CaveSurface surfaceType) implements SurfaceCondition {}

    @AsOf("2.4.0")
    record Not(SurfaceCondition target) implements SurfaceCondition {}

    @AsOf("2.4.0")
    record Water(int offset, int surfaceDepthMultiplier, boolean addStoneDepth) implements SurfaceCondition {}

    @AsOf("2.4.0")
    record Biome(List<ResourceKey> biomes) implements SurfaceCondition {}

    @AsOf("2.4.0")
    record Noise(ResourceKey noise, double minThreshold, double maxThreshold) implements SurfaceCondition {}

    @AsOf("2.4.0")
    record YCheck(VerticalAnchor anchor, int surfaceDepthMultiplier, boolean addStoneDepth) implements SurfaceCondition {}

    @AsOf("2.4.0")
    record VerticalGradient(String randomName, VerticalAnchor trueAtAndBelow, VerticalAnchor falseAtAndAbove) implements SurfaceCondition {}

    @AsOf("2.4.0")
    record Steep() implements SurfaceCondition {
        static final Steep INSTANCE = new Steep();
    }

    @AsOf("2.4.0")
    record Hole() implements SurfaceCondition {
        static final Hole INSTANCE = new Hole();
    }

    @AsOf("2.4.0")
    record AbovePreliminarySurface() implements SurfaceCondition {
        static final AbovePreliminarySurface INSTANCE = new AbovePreliminarySurface();
    }

    @AsOf("2.4.0")
    record Temperature() implements SurfaceCondition {
        static final Temperature INSTANCE = new Temperature();
    }
}