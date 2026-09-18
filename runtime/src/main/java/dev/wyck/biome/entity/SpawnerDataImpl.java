package dev.wyck.biome.entity;

import dev.wyck.util.MinecraftEntityTypes;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record SpawnerDataImpl(
    @Override EntityType type,
    @Override IntProvider count
) implements SpawnerData {

    @Override
    public MobCategory naturalCategory() {
        return MobCategory.TRANSLATOR.fromNms(MinecraftEntityTypes.toMinecraft(this.type).getCategory());
    }

    @Override
    public net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData toMinecraft() {
        return new net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData(
            MinecraftEntityTypes.toMinecraft(this.type),
            this.count.asHandle()
        );
    }
}
