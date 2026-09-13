package dev.wyck.decode.worldgen.structure.templatesystem;

import dev.wyck.decode.Decoders;
import dev.wyck.decode.FastReflection;
import dev.wyck.worldgen.ruletest.RuleTest;
import dev.wyck.worldgen.structure.templatesystem.processor.ProcessorRule;
import dev.wyck.worldgen.structure.templatesystem.rule.BlockEntityModifier;
import dev.wyck.worldgen.structure.templatesystem.rule.PosRuleTest;
import dev.wyck.wrapper.decode.Decodable;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class ProcessorRuleDecoder implements Decodable<ProcessorRule, net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule> {

    @Override
    public ProcessorRule decode(net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule minecraftObject) {
        return ProcessorRule.of(
            RuleTest.decode(FastReflection.read(minecraftObject, "inputPredicate")),
            RuleTest.decode(FastReflection.read(minecraftObject, "locPredicate")),
            PosRuleTest.decode(FastReflection.read(minecraftObject, "posPredicate")),
            Decoders.blockData(FastReflection.read(minecraftObject, "outputState")),
            BlockEntityModifier.decode(FastReflection.read(minecraftObject, "blockEntityModifier"))
        );
    }
}
