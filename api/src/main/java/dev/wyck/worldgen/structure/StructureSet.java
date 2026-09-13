package dev.wyck.worldgen.structure;

import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.worldgen.structure.placement.StructurePlacement;
import dev.wyck.worldgen.structure.types.ComposedStructureSet;
import dev.wyck.worldgen.structure.types.ReferencedStructureSet;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

/**
 * Wraps Minecraft's StructureSet which are just a weighted list of {@link Structure structures} sharing one
 * {@link StructurePlacement placement}.
 *
 * @see <a href="https://minecraft.wiki/w/Structure_set">Structure set</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
@ApiStatus.Experimental
public interface StructureSet extends Wrapper, Keyed {

    /**
     * References a structure set already registered under the given key.
     * @param key the registry key of the structure set
     * @return a reference to the registered structure set
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static ReferencedStructureSet reference(ResourceKey key) {
        return ReferencedStructureSet.of(key);
    }

    /**
     * Resolves this set's key in Minecraft's structure-set registry and decodes the registered
     * value, keeping the key on the result.
     * @return the decoded structure set
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    default StructureSet wrap() {
        ResourceKey key = ResourceKey.of(key().namespace(), key().value());
        Object minecraft = WyckRegistry.of(RegistryId.STRUCTURE_SET).retrieveOrThrow(key);
        StructureSet decoded = decode(minecraft);
        if (decoded instanceof ComposedStructureSet composed && composed.resourceKey().isEmpty()) {
            return composed.toBuilder().resourceKey(key).build();
        }
        return decoded;
    }

    /**
     * Authors a structure set from its entries and placement.
     * @param structures the weighted structures in the set
     * @param placement how the set spreads its structures
     * @return an authored structure set
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static ComposedStructureSet of(List<Entry> structures, StructurePlacement placement) {
        return ComposedStructureSet.of(null, structures, placement);
    }

    /**
     * Authors a structure set holding a single structure.
     * @param structure the structure in the set
     * @param placement how the set spreads its structure
     * @return an authored structure set
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static ComposedStructureSet of(Structure structure, StructurePlacement placement) {
        return ComposedStructureSet.of(null, List.of(Entry.of(structure)), placement);
    }

    /**
     * Reads a Minecraft structure set, or a keyed structure-set holder, into a wrapper.
     * @param minecraftStructureSet the structure set or structure-set holder to read
     * @return a reference to the structure set, or the decoded set when it carries no key
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static StructureSet decode(Object minecraftStructureSet) {
        record Holder() {
            static final Decoder<StructureSet> DECODER = Decoder.create("dev.wyck.decode.worldgen.structure.StructureSetDecoder");
        }
        return Holder.DECODER.decode(minecraftStructureSet);
    }

    /**
     * One structure in a set, with the weight it is chosen by when the set holds several.
     *
     * @param structure the structure
     * @param weight the weight the structure is chosen by
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    record Entry(Structure structure, int weight) {

        /**
         * Creates a new entry.
         * @param structure the structure
         * @param weight the weight the structure is chosen by
         * @return a new entry
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public static Entry of(Structure structure, int weight) {
            return new Entry(structure, weight);
        }

        /**
         * Creates a new entry with a weight of 1.
         * @param structure the structure
         * @return a new entry
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public static Entry of(Structure structure) {
            return new Entry(structure, 1);
        }
    }
}
