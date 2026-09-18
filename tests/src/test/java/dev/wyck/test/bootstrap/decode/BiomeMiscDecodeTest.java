package dev.wyck.test.bootstrap.decode;

import dev.wyck.biome.BiomeSpecialEffects;
import dev.wyck.biome.ClimateSettings;
import dev.wyck.biome.entity.MobSpawnCost;
import dev.wyck.biome.entity.MobSpawnSettings;
import dev.wyck.biome.entity.SpawnerData;
import dev.wyck.biome.TemperatureModifier;
import dev.wyck.biome.entity.MobCategory;
import dev.wyck.environment.GrassColorModifier;
import dev.wyck.test.bootstrap.MinecraftBootstrap;
import dev.wyck.util.WeightedList;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MinecraftBootstrap.class)
class BiomeMiscDecodeTest {

    @Test
    void climateSettingsDecodeEveryField() {
        net.minecraft.world.level.biome.Biome.ClimateSettings minecraft =
            new net.minecraft.world.level.biome.Biome.ClimateSettings(
                false, -0.7f, net.minecraft.world.level.biome.Biome.TemperatureModifier.FROZEN, 0.85f);

        ClimateSettings decoded = ClimateSettings.decode(minecraft);

        assertFalse(decoded.hasPrecipitation());
        assertEquals(-0.7f, decoded.temperature());
        assertEquals(TemperatureModifier.FROZEN, decoded.temperatureModifier());
        assertEquals(0.85f, decoded.downfall());
    }

    @Test
    void specialEffectsDecodeOverridesAndModifier() {
        net.minecraft.world.level.biome.BiomeSpecialEffects minecraft =
            new net.minecraft.world.level.biome.BiomeSpecialEffects(
                0x123456,
                Optional.of(0x234567),
                Optional.empty(),
                Optional.of(0x345678),
                net.minecraft.world.level.biome.BiomeSpecialEffects.GrassColorModifier.DARK_FOREST
            );

        BiomeSpecialEffects decoded = BiomeSpecialEffects.decode(minecraft);

        assertEquals(0x123456, decoded.waterColor());
        assertEquals(0x234567, decoded.foliageColorOverride().orElseThrow());
        assertTrue(decoded.dryFoliageColorOverride().isEmpty());
        assertEquals(0x345678, decoded.grassColorOverride().orElseThrow());
        assertEquals(GrassColorModifier.DARK_FOREST, decoded.grassColorModifier());
    }

    @Test
    void spawnLeafRecordsDecodeTheirOwnMinecraftTypes() {
        SpawnerData spawner = SpawnerData.decode(
            new net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData(
                net.minecraft.world.entity.EntityTypes.ZOMBIE,
                net.minecraft.util.valueproviders.UniformInt.of(2, 5)
            ));
        assertEquals(EntityType.ZOMBIE, spawner.type());
        assertEquals(2, spawner.count().minInclusive());
        assertEquals(5, spawner.count().maxInclusive());

        MobSpawnCost cost = MobSpawnCost.decode(
            new net.minecraft.world.level.biome.MobSpawnSettings.MobSpawnCost(9.5, 1.25));
        assertEquals(1.25, cost.charge());
        assertEquals(9.5, cost.energyBudget());
    }

    @Test
    void mobSpawnSettingsStackLeafDecodersAndPreserveWeights() {
        MobSpawnSettings original = MobSpawnSettings.builder()
            .addSpawn(EntityType.ZOMBIE, 7, IntProvider.uniform(2, 5))
            .addSpawn(EntityType.SKELETON, 3, IntProvider.uniform(1, 4))
            .addMobSpawnCost(EntityType.ZOMBIE, 1.25, 9.5)
            .build();

        MobSpawnSettings decoded = MobSpawnSettings.decode(original.toMinecraft());

        WeightedList<SpawnerData> monsters = decoded.getMobsToSpawn(MobCategory.MONSTER);
        assertEquals(2, monsters.unwrap().size());
        assertEquals(EntityType.ZOMBIE, monsters.unwrap().getFirst().value().type());
        assertEquals(7, monsters.unwrap().getFirst().weight());
        assertEquals(EntityType.SKELETON, monsters.unwrap().getLast().value().type());
        assertEquals(3, monsters.unwrap().getLast().weight());
        MobSpawnCost zombieCost = decoded.allSpawnCosts().get(EntityType.ZOMBIE);
        assertEquals(9.5, zombieCost.energyBudget());
        assertEquals(1.25, zombieCost.charge());
    }
}
