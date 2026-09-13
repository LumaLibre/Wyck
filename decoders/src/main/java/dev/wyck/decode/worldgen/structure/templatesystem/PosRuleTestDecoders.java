package dev.wyck.decode.worldgen.structure.templatesystem;

import dev.wyck.decode.Decoders;
import dev.wyck.decode.FastReflection;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.structure.templatesystem.rule.PosRuleTest;
import dev.wyck.wrapper.decode.DecoderRegistry;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosRuleTestType;
import org.bukkit.Axis;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class PosRuleTestDecoders extends DecoderRegistry<PosRuleTest, net.minecraft.world.level.levelgen.structure.templatesystem.PosRuleTest> {

    public PosRuleTestDecoders() {
        register("always_true", _ -> PosRuleTest.alwaysTrue());
        register("linear_pos", test -> PosRuleTest.linearPos(
            FastReflection.<Float>read(test, "minChance"), FastReflection.<Float>read(test, "maxChance"),
            FastReflection.<Integer>read(test, "minDist"), FastReflection.<Integer>read(test, "maxDist")
        ));
        register("axis_aligned_linear_pos", test -> PosRuleTest.axisAlignedLinearPos(
            FastReflection.<Float>read(test, "minChance"), FastReflection.<Float>read(test, "maxChance"),
            FastReflection.<Integer>read(test, "minDist"), FastReflection.<Integer>read(test, "maxDist"),
            // same axis names
            Axis.valueOf(FastReflection.<Direction.Axis>read(test, "axis").name())
        ));
    }

    @Override
    protected ResourceKey discriminate(net.minecraft.world.level.levelgen.structure.templatesystem.PosRuleTest minecraftObject) {
        PosRuleTestType<?> type = FastReflection.call(minecraftObject, "getType");
        return Decoders.registryKey(BuiltInRegistries.POS_RULE_TEST, type);
    }
}
