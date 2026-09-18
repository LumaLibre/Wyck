package dev.wyck.decode.biome.entity;

import dev.wyck.biome.entity.MobSpawnCost;
import dev.wyck.wrapper.decode.Decodable;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class MobSpawnCostDecoder implements Decodable<MobSpawnCost, net.minecraft.world.level.biome.MobSpawnSettings.MobSpawnCost> {

    @Override
    public MobSpawnCost decode(net.minecraft.world.level.biome.MobSpawnSettings.MobSpawnCost minecraftObject) {
        return MobSpawnCost.of(minecraftObject.energyBudget(), minecraftObject.charge());
    }
}
