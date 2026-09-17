package dev.wyck.worldgen.structure;

import dev.wyck.biome.Biome;
import dev.wyck.biome.entity.MobCategory;
import dev.wyck.tags.TagSet;
import dev.wyck.worldgen.Decoration;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.LinkedHashMap;
import java.util.Map;

@NullMarked
@ApiStatus.Internal
public record StructureSettingsImpl(
    @Override TagSet<Biome> biomes,
    @Override Map<MobCategory, StructureSpawnOverride> spawnOverrides,
    @Override Decoration step,
    @Override TerrainAdaptation terrainAdaptation
) implements StructureSettings {

    @Override
    public net.minecraft.world.level.levelgen.structure.Structure.StructureSettings toMinecraft() {
        Map<net.minecraft.world.entity.MobCategory, net.minecraft.world.level.levelgen.structure.StructureSpawnOverride> overrides =
            new LinkedHashMap<>();
        this.spawnOverrides.forEach((category, override) -> overrides.put(
            category.toNms(net.minecraft.world.entity.MobCategory.class),
            override.asHandle()
        ));

        return new net.minecraft.world.level.levelgen.structure.Structure.StructureSettings(
            this.biomes.asHolderSet(),
            Map.copyOf(overrides),
            this.step.toNms(net.minecraft.world.level.levelgen.GenerationStep.Decoration.class),
            this.terrainAdaptation.toNms(net.minecraft.world.level.levelgen.structure.TerrainAdjustment.class)
        );
    }
}
