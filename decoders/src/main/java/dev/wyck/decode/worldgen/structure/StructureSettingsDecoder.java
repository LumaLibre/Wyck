package dev.wyck.decode.worldgen.structure;

import dev.wyck.biome.entity.MobCategory;
import dev.wyck.tags.TagSet;
import dev.wyck.worldgen.Decoration;
import dev.wyck.worldgen.structure.StructureSettings;
import dev.wyck.worldgen.structure.StructureSpawnOverride;
import dev.wyck.worldgen.structure.TerrainAdaptation;
import dev.wyck.wrapper.decode.Decodable;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.LinkedHashMap;
import java.util.Map;

@NullMarked
@ApiStatus.Internal
public final class StructureSettingsDecoder implements Decodable<StructureSettings, Structure.StructureSettings> {

    @Override
    public StructureSettings decode(Structure.StructureSettings minecraftObject) {
        Map<MobCategory, StructureSpawnOverride> overrides = new LinkedHashMap<>();
        minecraftObject.spawnOverrides().forEach((category, override) -> overrides.put(
            MobCategory.TRANSLATOR.fromNms(category),
            StructureSpawnOverride.decode(override)
        ));

        return StructureSettings.of(
            TagSet.decodeBiomes(minecraftObject.biomes()),
            overrides,
            Decoration.TRANSLATOR.fromNms(minecraftObject.step()),
            TerrainAdaptation.TRANSLATOR.fromNms(minecraftObject.terrainAdaptation())
        );
    }
}
