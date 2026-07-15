package dev.wyck.biome.entity.data;

import dev.wyck.annotations.AsOf;
import org.bukkit.entity.EntityType;
import org.jspecify.annotations.NullMarked;

/**
 * Represents a natural spawner.
 * @param type the type of entity that spawns
 * @param minCount the minimum number of entities that can spawn
 * @param maxCount the maximum number of entities that can spawn
 * @since 2.3.0
 * @version 2.3.0
 */
@NullMarked
@AsOf("2.3.0")
public record NaturalSpawner(EntityType type, int minCount, int maxCount) {
    @AsOf("2.3.0")
    public static NaturalSpawner of(EntityType type, int minCount, int maxCount) {
        return new NaturalSpawner(type, minCount, maxCount);
    }
}
