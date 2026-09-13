package dev.wyck.worldgen.structure.templatesystem.processor;

import dev.wyck.worldgen.ruletest.RuleTest;
import dev.wyck.worldgen.structure.templatesystem.rule.BlockEntityModifier;
import dev.wyck.worldgen.structure.templatesystem.rule.PosRuleTest;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record ProcessorRuleImpl(
    @Override RuleTest inputPredicate,
    @Override RuleTest locationPredicate,
    @Override PosRuleTest positionPredicate,
    @Override BlockData outputState,
    @Override BlockEntityModifier blockEntityModifier
) implements ProcessorRule {

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule(
            this.inputPredicate.asHandle(),
            this.locationPredicate.asHandle(),
            this.positionPredicate.asHandle(),
            ((CraftBlockData) this.outputState).getState(),
            this.blockEntityModifier.asHandle()
        );
    }
}
