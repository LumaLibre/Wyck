package dev.wyck.biome.entity;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import org.bukkit.entity.EntityType;
import org.jspecify.annotations.NullMarked;

/**
 * Describes an entity type and the number of entities in one natural spawn attempt.
 *
 * @since 4.0.0
 * @version 4.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("4.0.0")
public interface SpawnerData extends Wrapper {

    /**
     * The entity type spawned by this entry.
     * @return the entity type to spawn
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    EntityType type();

    /**
     * The provider used to select the number of entities in a spawn attempt.
     * @return the spawn count provider
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    IntProvider count();

    /**
     * The natural mob category assigned to {@link #type()} by Minecraft.
     * @return the entity type's natural mob category
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    MobCategory naturalCategory();

    /**
     * Creates spawn data with a fixed group size.
     * @param type the entity type to spawn
     * @param count the fixed number of entities to spawn
     * @return new spawn data
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static SpawnerData of(EntityType type, int count) {
        return of(type, IntProvider.constant(count));
    }

    /**
     * Creates spawn data with a configurable group size.
     * @param type the entity type to spawn
     * @param count the provider used to select the number of entities to spawn
     * @return new spawn data
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static SpawnerData of(EntityType type, IntProvider count) {
        record Holder() {
            static final ConstructWireProvider<SpawnerData> WIRE = ConstructWireProvider.create("dev.wyck.biome.entity.SpawnerDataImpl");
        }
        return Holder.WIRE.construct(type, count);
    }

    /**
     * Reads Minecraft spawner data.
     * @param minecraftSpawnerData the Minecraft spawner data to read
     * @return the wrapped spawner data
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static SpawnerData decode(Object minecraftSpawnerData) {
        record Holder() {
            static final Decoder<SpawnerData> DECODER = Decoder.create("dev.wyck.decode.biome.entity.SpawnerDataDecoder");
        }
        return Holder.DECODER.decode(minecraftSpawnerData);
    }
}
