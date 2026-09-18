package dev.wyck.decode.worldgen.structure;

import dev.wyck.decode.Decoders;
import dev.wyck.biome.entity.SpawnerData;
import dev.wyck.worldgen.structure.StructureSpawnOverride;
import dev.wyck.wrapper.decode.Decodable;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class StructureSpawnOverrideDecoder implements Decodable<StructureSpawnOverride, net.minecraft.world.level.levelgen.structure.StructureSpawnOverride> {

    @Override
    public StructureSpawnOverride decode(net.minecraft.world.level.levelgen.structure.StructureSpawnOverride minecraftObject) {
        return StructureSpawnOverride.of(
            StructureSpawnOverride.BoundingBoxType.TRANSLATOR.fromNms(minecraftObject.boundingBox()),
            Decoders.weighted(minecraftObject.spawns(), SpawnerData::decode)
        );
    }
}
