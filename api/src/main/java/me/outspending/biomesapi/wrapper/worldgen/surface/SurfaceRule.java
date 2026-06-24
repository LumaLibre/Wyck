package me.outspending.biomesapi.wrapper.worldgen.surface;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

/**
 * Wraps the surface-rule family ({@code SurfaceRules.RuleSource}), the tree that decides which block
 * is placed at the surface during generation.
 *
 * @version 2.4.0
 * @since 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
@ApiStatus.Experimental
public sealed interface SurfaceRule extends NmsHandle permits SurfaceRule.Bandlands, SurfaceRule.Block, SurfaceRule.Sequence, SurfaceRule.Condition {

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.worldgen.surface.SurfaceRuleFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        Object toNms(SurfaceRule rule);
    }

    @Override
    @AsOf("2.4.0")
    default Object toMinecraft() {
        return WIRE.get().toNms(this);
    }

    /**
     * The badlands banded-terracotta rule.
     * @return the bandlands rule
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static SurfaceRule bandlands() {
        return Bandlands.INSTANCE;
    }

    /**
     * Places the given block's default state.
     * @param block the block to place
     * @return a block rule
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static SurfaceRule block(Material block) {
        return new Block(block);
    }

    /**
     * Evaluates rules in order, using the first that applies.
     * @param rules the rules to evaluate in order
     * @return a sequence rule
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static SurfaceRule sequence(List<SurfaceRule> rules) {
        return new Sequence(List.copyOf(rules));
    }

    /**
     * Applies a rule only when a condition holds.
     * @param condition the gating condition
     * @param then the rule to apply when the condition holds
     * @return a conditional rule
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static SurfaceRule ifTrue(SurfaceCondition condition, SurfaceRule then) {
        return new Condition(condition, then);
    }

    @AsOf("2.4.0")
    record Bandlands() implements SurfaceRule {
        static final Bandlands INSTANCE = new Bandlands();
    }

    @AsOf("2.4.0")
    record Block(Material block) implements SurfaceRule {}

    @AsOf("2.4.0")
    record Sequence(List<SurfaceRule> rules) implements SurfaceRule {}

    @AsOf("2.4.0")
    record Condition(SurfaceCondition condition, SurfaceRule then) implements SurfaceRule {}
}