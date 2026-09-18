package dev.wyck.worldgen.structure;

import dev.wyck.annotations.AsOf;
import dev.wyck.biome.entity.MobCategory;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.util.WeightedList;
import dev.wyck.biome.entity.SpawnerData;
import dev.wyck.wrapper.KeyedEnumTranslator;
import dev.wyck.wrapper.WrappedEnumerator;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import org.jspecify.annotations.NullMarked;

/**
 * Replaces a biome's spawners for one {@link MobCategory} inside a structure's bounds. A structure
 * carries at most one override per category.
 *
 * @since 3.4.0
 * @version 4.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
public interface StructureSpawnOverride extends Wrapper {

    /**
     * The volume the override applies within.
     * @return the bounding box the override applies within
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    BoundingBoxType boundingBox();

    /**
     * The spawners that replace the biome's own for this category.
     * @return the replacement spawners
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    WeightedList<SpawnerData> spawns();

    /**
     * Creates a new spawn override.
     * @param boundingBox the volume the override applies within
     * @param spawns the replacement spawners
     * @return a new spawn override
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static StructureSpawnOverride of(BoundingBoxType boundingBox, WeightedList<SpawnerData> spawns) {
        record Holder() {
            static final ConstructWireProvider<StructureSpawnOverride> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.structure.StructureSpawnOverrideImpl");
        }
        return Holder.WIRE.construct(boundingBox, spawns);
    }

    /**
     * Reads a Minecraft structure spawn override.
     * @param minecraftSpawnOverride the spawn override to read
     * @return the decoded spawn override
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static StructureSpawnOverride decode(Object minecraftSpawnOverride) {
        record Holder() {
            static final Decoder<StructureSpawnOverride> DECODER = Decoder.create("dev.wyck.decode.worldgen.structure.StructureSpawnOverrideDecoder");
        }
        return Holder.DECODER.decode(minecraftSpawnOverride);
    }

    /**
     * The volume a {@link StructureSpawnOverride} applies within.
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    enum BoundingBoxType implements WrappedEnumerator<BoundingBoxType> {
        /** Only inside the bounds of the individual piece the mob would spawn in. */
        PIECE("PIECE"),
        /** Anywhere inside the structure's full bounding box. */
        STRUCTURE("STRUCTURE");

        public static final KeyedEnumTranslator<BoundingBoxType> TRANSLATOR =
            KeyedEnumTranslator.byKey(BoundingBoxType::getKey, BoundingBoxType.values());

        private final String key;

        @AsOf("3.4.0")
        BoundingBoxType(String key) {
            this.key = key;
        }

        @AsOf("3.4.0")
        @Override
        public KeyedEnumTranslator<BoundingBoxType> translator() {
            return TRANSLATOR;
        }

        /**
         * The vanilla name for this BoundingBoxType
         * @return the vanilla key for this enum value
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public String getKey() {
            return this.key;
        }
    }
}
