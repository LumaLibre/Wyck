package dev.wyck.decode.biome.entity;

import dev.wyck.biome.entity.MobCategory;
import dev.wyck.decode.Decoders;
import dev.wyck.biome.entity.MobSpawnSettings;
import dev.wyck.biome.entity.SpawnerData;
import dev.wyck.wrapper.decode.Decodable;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class MobSpawnSettingsDecoder implements Decodable<MobSpawnSettings, net.minecraft.world.level.biome.MobSpawnSettings> {

    @Override
    public MobSpawnSettings decode(net.minecraft.world.level.biome.MobSpawnSettings minecraftObject) {
        MobSpawnSettings.Builder builder = MobSpawnSettings.builder();
        for (net.minecraft.world.entity.MobCategory category : minecraftObject.definedCategories()) {
            net.minecraft.util.random.WeightedList<net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData> spawns =
                minecraftObject.getMobsInCategory(category);
            if (spawns != null) {
                builder.addAllSpawns(
                    MobCategory.TRANSLATOR.fromNms(category),
                    Decoders.weighted(spawns, SpawnerData::decode)
                );
            }
        }
        minecraftObject.allSpawnCosts().forEach((type, cost) -> builder.addMobSpawnCost(
            Decoders.bukkitEntityType(type),
            cost.charge(),
            cost.energyBudget()
        ));
        return builder.build();
    }
}
