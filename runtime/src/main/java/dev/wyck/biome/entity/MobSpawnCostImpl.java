package dev.wyck.biome.entity;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record MobSpawnCostImpl(
    @Override double energyBudget,
    @Override double charge
) implements MobSpawnCost {

    @Override
    public net.minecraft.world.level.biome.MobSpawnSettings.MobSpawnCost toMinecraft() {
        return new net.minecraft.world.level.biome.MobSpawnSettings.MobSpawnCost(
            this.energyBudget,
            this.charge
        );
    }
}
