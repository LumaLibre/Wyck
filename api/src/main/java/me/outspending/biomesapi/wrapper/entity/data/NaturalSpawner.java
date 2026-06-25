package me.outspending.biomesapi.wrapper.entity.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.outspending.biomesapi.annotations.AsOf;
import org.bukkit.entity.EntityType;
import org.jspecify.annotations.NullMarked;

import static me.outspending.biomesapi.serialization.Codecs.ENTITY_TYPE_CODEC;

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

    public static final Codec<NaturalSpawner> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        ENTITY_TYPE_CODEC.fieldOf("type").forGetter(NaturalSpawner::type),
        Codec.INT.fieldOf("min_count").forGetter(NaturalSpawner::minCount),
        Codec.INT.fieldOf("max_count").forGetter(NaturalSpawner::maxCount)
    ).apply(instance, NaturalSpawner::new));

    @AsOf("2.3.0")
    public static NaturalSpawner of(EntityType type, int minCount, int maxCount) {
        return new NaturalSpawner(type, minCount, maxCount);
    }
}
