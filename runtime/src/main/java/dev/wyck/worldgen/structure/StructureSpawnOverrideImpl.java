package dev.wyck.worldgen.structure;

import dev.wyck.util.WeightedList;
import dev.wyck.biome.entity.SpawnerData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

@NullMarked
@ApiStatus.Internal
public record StructureSpawnOverrideImpl(
    @Override BoundingBoxType boundingBox,
    @Override WeightedList<SpawnerData> spawns
) implements StructureSpawnOverride {

    @Override
    public net.minecraft.world.level.levelgen.structure.StructureSpawnOverride toMinecraft() {
        List<net.minecraft.util.random.Weighted<net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData>> entries =
            new ArrayList<>(this.spawns.unwrap().size());
        for (WeightedList.Weighted<SpawnerData> entry : this.spawns.unwrap()) {
            SpawnerData spawner = entry.value();
            entries.add(new net.minecraft.util.random.Weighted<>(
                spawner.asHandle(),
                entry.weight()
            ));
        }

        return new net.minecraft.world.level.levelgen.structure.StructureSpawnOverride(
            this.boundingBox.toNms(net.minecraft.world.level.levelgen.structure.StructureSpawnOverride.BoundingBoxType.class),
            net.minecraft.util.random.WeightedList.of(entries)
        );
    }

}
