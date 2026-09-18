package dev.wyck.decode.biome.entity;

import dev.wyck.decode.Decoders;
import dev.wyck.biome.entity.SpawnerData;
import dev.wyck.worldgen.valueproviders.IntProvider;
import dev.wyck.wrapper.decode.Decodable;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class SpawnerDataDecoder implements Decodable<SpawnerData, net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData> {

    @Override
    public SpawnerData decode(net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData minecraftObject) {
        return SpawnerData.of(
            Decoders.bukkitEntityType(minecraftObject.type()),
            IntProvider.decode(minecraftObject.count())
        );
    }
}
