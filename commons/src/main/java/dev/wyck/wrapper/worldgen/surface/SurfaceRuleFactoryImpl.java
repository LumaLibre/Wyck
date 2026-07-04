package dev.wyck.wrapper.worldgen.surface;

import dev.wyck.annotations.WireFactory;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.SurfaceRules;
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
            case SurfaceRule.Bandlands ignored -> SurfaceRules.bandlands();
            case SurfaceRule.Block block -> block(block);
            case SurfaceRule.Sequence sequence -> sequence(sequence);
            case SurfaceRule.Condition condition -> condition(condition);
        };
    }

    private SurfaceRules.RuleSource block(SurfaceRule.Block block) {
        Block nmsBlock = CraftMagicNumbers.getBlock(block.block());
        if (nmsBlock == null) {
            throw new IllegalArgumentException("Material " + block.block() + " does not map to a block");
        }
        BlockState state = nmsBlock.defaultBlockState();
        return SurfaceRules.state(state);
    }

    private SurfaceRules.RuleSource sequence(SurfaceRule.Sequence sequence) {
        List<SurfaceRules.RuleSource> sources = new ArrayList<>();
        for (SurfaceRule rule : sequence.rules()) {
            SurfaceRules.RuleSource source = (SurfaceRules.RuleSource) rule.toMinecraft();
            sources.add(source);
        }
        SurfaceRules.RuleSource[] array = sources.toArray(SurfaceRules.RuleSource[]::new);
        return SurfaceRules.sequence(array);
    }

    private SurfaceRules.RuleSource condition(SurfaceRule.Condition condition) {
        SurfaceRules.ConditionSource ifTrue = (SurfaceRules.ConditionSource) condition.condition().toMinecraft();
        SurfaceRules.RuleSource then = (SurfaceRules.RuleSource) condition.then().toMinecraft();
        return SurfaceRules.ifTrue(ifTrue, then);
    }
}