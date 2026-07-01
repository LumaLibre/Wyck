package me.outspending.biomesapi.wrapper.worldgen.surface;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.serialization.Codecs;
import me.outspending.biomesapi.serialization.StringRepresentable;
import me.outspending.biomesapi.wrapper.internal.NmsDecoder;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.Map;

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
public sealed interface SurfaceRule extends NmsHandle, StringRepresentable permits SurfaceRule.Bandlands, SurfaceRule.Block, SurfaceRule.Sequence, SurfaceRule.Condition {

    Codec<SurfaceRule> CODEC = Codec.recursive("SurfaceRule", self -> {
        Map<String, MapCodec<? extends SurfaceRule>> byType = Map.of(
            "bandlands", Bandlands.MAP_CODEC,
            "block", Block.MAP_CODEC,
            "sequence", Sequence.mapCodec(self),
            "condition", Condition.mapCodec(self)
        );
        return Codec.STRING.dispatch("type", SurfaceRule::type, byType::get);
    });

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.worldgen.surface.SurfaceRuleFactoryImpl");

    @ApiStatus.Internal
    interface Factory extends NmsDecoder<SurfaceRule> {
        Object toNms(SurfaceRule rule);
    }

    @Override
    @AsOf("2.4.0")
    default Object toMinecraft() {
        return WIRE.get().toNms(this);
    }

    @AsOf("2.4.0")
    static SurfaceRule fromMinecraft(Object nms) {
        return WIRE.get().fromMinecraft(nms);
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
        public static final MapCodec<Bandlands> MAP_CODEC = MapCodec.unit(INSTANCE);
    }

    @AsOf("2.4.0")
    record Block(Material block) implements SurfaceRule {
        public static final MapCodec<Block> MAP_CODEC = Codecs.MATERIAL_CODEC.fieldOf("block")
            .xmap(Block::new, Block::block);
    }

    @AsOf("2.4.0")
    record Sequence(List<SurfaceRule> rules) implements SurfaceRule {
        public static MapCodec<Sequence> mapCodec(Codec<SurfaceRule> self) {
            return Codec.list(self).fieldOf("rules").xmap(Sequence::new, Sequence::rules);
        }
    }

    @AsOf("2.4.0")
    record Condition(SurfaceCondition condition, SurfaceRule then) implements SurfaceRule {
        public static MapCodec<Condition> mapCodec(Codec<SurfaceRule> self) {
            return RecordCodecBuilder.mapCodec(i -> i.group(
                SurfaceCondition.CODEC.fieldOf("condition").forGetter(Condition::condition),
                self.fieldOf("then").forGetter(Condition::then)
            ).apply(i, Condition::new));
        }
    }
}