package dev.wyck.worldgen.structure;

import dev.wyck.biome.entity.data.NaturalSpawner;
import dev.wyck.util.WeightedList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.biome.MobSpawnSettings;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

@NullMarked
@ApiStatus.Internal
public record StructureSpawnOverrideImpl(
    @Override BoundingBoxType boundingBox,
    @Override WeightedList<NaturalSpawner> spawns
) implements StructureSpawnOverride {

    @Override
    public net.minecraft.world.level.levelgen.structure.StructureSpawnOverride toMinecraft() {
        List<net.minecraft.util.random.Weighted<MobSpawnSettings.SpawnerData>> entries =
            new ArrayList<>(this.spawns.unwrap().size());
        for (WeightedList.Weighted<NaturalSpawner> entry : this.spawns.unwrap()) {
            NaturalSpawner spawner = entry.value();
            entries.add(new net.minecraft.util.random.Weighted<>(
                new MobSpawnSettings.SpawnerData(nmsEntityType(spawner.type()), spawner.minCount(), spawner.maxCount()),
                entry.weight()
            ));
        }

        return new net.minecraft.world.level.levelgen.structure.StructureSpawnOverride(
            this.boundingBox.toNms(net.minecraft.world.level.levelgen.structure.StructureSpawnOverride.BoundingBoxType.class),
            net.minecraft.util.random.WeightedList.of(entries)
        );
    }

    private static net.minecraft.world.entity.EntityType<?> nmsEntityType(org.bukkit.entity.EntityType bukkit) {
        NamespacedKey key = bukkit.getKey();
        Identifier location = Identifier.fromNamespaceAndPath(key.getNamespace(), key.getKey());

        if (!BuiltInRegistries.ENTITY_TYPE.containsKey(location)) {
            throw new IllegalArgumentException("No NMS entity type registered for " + key);
        }
        return BuiltInRegistries.ENTITY_TYPE.getValue(location);
    }
}
