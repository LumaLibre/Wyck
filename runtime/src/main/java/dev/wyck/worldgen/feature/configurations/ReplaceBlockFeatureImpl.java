package dev.wyck.worldgen.feature.configurations;

import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

@NullMarked
@ApiStatus.Internal
public record ReplaceBlockFeatureImpl(
    @Override List<OreFeature.TargetBlockState> targetStates
) implements ReplaceBlockFeature {
    @Override
    public Object toMinecraft() {
        List<net.minecraft.world.level.levelgen.feature.BlockReplacement> targets =
            new ArrayList<>(targetStates.size());
        for (OreFeature.TargetBlockState target : targetStates) {
            net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest rule = target.target().asHandle();
            net.minecraft.world.level.block.state.BlockState state = ((CraftBlockData) target.state()).getState();
            targets.add(net.minecraft.world.level.levelgen.feature.BlockReplacement.replace(rule, state));
        }

        return new net.minecraft.world.level.levelgen.feature.ReplaceBlockFeature(targets);
    }
}
