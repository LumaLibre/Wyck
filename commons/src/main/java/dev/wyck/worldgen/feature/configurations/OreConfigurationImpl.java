package dev.wyck.worldgen.feature.configurations;

import dev.wyck.worldgen.feature.configurations.OreConfiguration;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

@NullMarked
@ApiStatus.Internal
public record OreConfigurationImpl(
    @Override List<OreConfiguration.TargetBlockState> targetStates,
    @Override int size,
    @Override float discardChanceOnAirExposure
) implements OreConfiguration {
    @Override
    public Object toMinecraft() {
        List<net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration.TargetBlockState> targets =
            new ArrayList<>(targetStates.size());
        for (OreConfiguration.TargetBlockState target : targetStates) {
            net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest rule = target.target().asHandle();
            net.minecraft.world.level.block.state.BlockState state = ((CraftBlockData) target.state()).getState();
            targets.add(net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration.target(rule, state));
        }

        return new net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration(targets, size, discardChanceOnAirExposure);
    }
}