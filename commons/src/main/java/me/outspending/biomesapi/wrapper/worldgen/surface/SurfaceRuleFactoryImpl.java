package me.outspending.biomesapi.wrapper.worldgen.surface;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.util.internal.InternalReflectUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.bukkit.Material;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

@NullMarked
@WireFactory
@ApiStatus.Internal
public class SurfaceRuleFactoryImpl implements SurfaceRule.Factory {

    @Override
    public Object toNms(SurfaceRule rule) {
        return switch (rule) {
            case SurfaceRule.Bandlands _ -> SurfaceRules.bandlands();
            case SurfaceRule.Block block -> {
                Block nmsBlock = CraftMagicNumbers.getBlock(block.block());
                Preconditions.checkNotNull(nmsBlock, "Material '" + block.block() + "' does not map to a block");
                BlockState state = nmsBlock.defaultBlockState();
                yield SurfaceRules.state(state);
            }
            case SurfaceRule.Sequence sequence -> {
                List<SurfaceRules.RuleSource> sources = new ArrayList<>();
                for (SurfaceRule sequenceRule : sequence.rules()) {
                    SurfaceRules.RuleSource source = (SurfaceRules.RuleSource) sequenceRule.toMinecraft();
                    sources.add(source);
                }
                SurfaceRules.RuleSource[] array = sources.toArray(SurfaceRules.RuleSource[]::new);
                yield SurfaceRules.sequence(array);
            }
            case SurfaceRule.Condition condition -> {
                SurfaceRules.ConditionSource ifTrue = (SurfaceRules.ConditionSource) condition.condition().toMinecraft();
                SurfaceRules.RuleSource then = (SurfaceRules.RuleSource) condition.then().toMinecraft();
                yield SurfaceRules.ifTrue(ifTrue, then);
            }
        };
    }

    @Override
    public SurfaceRule fromMinecraft(Object nms) {
        SurfaceRules.RuleSource source = (SurfaceRules.RuleSource) nms;

        Identifier typeId = BuiltInRegistries.MATERIAL_RULE.getKey(source.codec().codec());
        if (typeId == null) {
            throw new IllegalArgumentException("unregistered surface rule type: " + source.getClass());
        }

        return switch (typeId.getPath()) {
            case "bandlands" -> SurfaceRule.bandlands();

            case "block" -> {
                BlockState state = InternalReflectUtil.getFieldValue(source, "resultState");
                Material material = CraftMagicNumbers.getMaterial(state.getBlock());
                yield SurfaceRule.block(material);
            }

            case "sequence" -> {
                List<SurfaceRules.RuleSource> sequence = InternalReflectUtil.getFieldValue(source, "sequence");
                List<SurfaceRule> rules = new ArrayList<>();
                for (SurfaceRules.RuleSource child : sequence) {
                    rules.add(fromMinecraft(child));
                }
                yield SurfaceRule.sequence(rules);
            }

            case "condition" -> {
                SurfaceRules.ConditionSource ifTrue = InternalReflectUtil.getFieldValue(source, "ifTrue");
                SurfaceRules.RuleSource thenRun = InternalReflectUtil.getFieldValue(source, "thenRun");
                yield SurfaceRule.ifTrue(SurfaceCondition.fromMinecraft(ifTrue), fromMinecraft(thenRun));
            }

            default -> throw new UnsupportedOperationException("no wrapper representation for surface rule type: " + typeId);
        };
    }

}