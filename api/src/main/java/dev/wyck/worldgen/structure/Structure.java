package dev.wyck.worldgen.structure;

import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.worldgen.structure.types.DefinedStructure;
import dev.wyck.worldgen.structure.types.ReferencedStructure;
import dev.wyck.worldgen.structure.pools.TemplatePool;
import dev.wyck.worldgen.structure.types.JigsawStructure;
import dev.wyck.worldgen.structure.types.MineshaftStructure;
import dev.wyck.worldgen.structure.types.NetherFossilStructure;
import dev.wyck.worldgen.structure.types.OceanRuinStructure;
import dev.wyck.worldgen.structure.types.RuinedPortalStructure;
import dev.wyck.worldgen.structure.types.ShipwreckStructure;
import dev.wyck.worldgen.structure.types.SimpleStructure;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Structures are naturally-generated formations that are placed in the world from pre-built
 * templates.
 * <p>
 * Wraps Minecraft's {@code Structure}.
 *
 * @see StructureSettings#biomes()
 * @see <a href="https://minecraft.wiki/w/Structure">Structure</a>
 * @see <a href="https://minecraft.wiki/w/Structure_definition">Structure definition</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
@ApiStatus.Experimental
public interface Structure extends Wrapper, Keyed {

    /**
     * Resolves this structure's key in Minecraft's structure registry and decodes the registered
     * value, keeping the key on the result.
     * @return the decoded structure
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    default Structure wrap() {
        ResourceKey key = ResourceKey.of(key().namespace(), key().value());
        Object minecraft = WyckRegistry.of(RegistryId.STRUCTURE).retrieveOrThrow(key);
        Structure decoded = decode(minecraft);
        if (decoded instanceof DefinedStructure defined && defined.resourceKey().isEmpty()) {
            return defined.withResourceKey(key);
        }
        return decoded;
    }

    /**
     * References a structure already registered under the given key.
     * @param key the registry key of the structure
     * @return a reference to the registered structure
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static ReferencedStructure reference(ResourceKey key) {
        return ReferencedStructure.of(key);
    }

    /**
     * Creates a new builder for a jigsaw structure, which grows outward from a piece drawn from its
     * start pool. Villages, bastions, ancient cities and trial chambers are jigsaw structures.
     * @param settings the settings every structure carries
     * @param startPool the pool the first piece is drawn from
     * @return a new builder
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static JigsawStructure.Builder jigsaw(StructureSettings settings, TemplatePool startPool) {
        return JigsawStructure.builder(settings, startPool);
    }

    /**
     * Creates a new builder for buried treasure.
     * @param settings the settings every structure carries
     * @return a new builder
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static SimpleStructure.Builder buriedTreasure(StructureSettings settings) {
        return SimpleStructure.builder(StructureType.BURIED_TREASURE, settings);
    }

    /**
     * Creates a new builder for a desert pyramid.
     * @param settings the settings every structure carries
     * @return a new builder
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static SimpleStructure.Builder desertPyramid(StructureSettings settings) {
        return SimpleStructure.builder(StructureType.DESERT_PYRAMID, settings);
    }

    /**
     * Creates a new builder for an end city.
     * @param settings the settings every structure carries
     * @return a new builder
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static SimpleStructure.Builder endCity(StructureSettings settings) {
        return SimpleStructure.builder(StructureType.END_CITY, settings);
    }

    /**
     * Creates a new builder for a nether fortress.
     * @param settings the settings every structure carries
     * @return a new builder
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static SimpleStructure.Builder fortress(StructureSettings settings) {
        return SimpleStructure.builder(StructureType.FORTRESS, settings);
    }

    /**
     * Creates a new builder for an igloo.
     * @param settings the settings every structure carries
     * @return a new builder
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static SimpleStructure.Builder igloo(StructureSettings settings) {
        return SimpleStructure.builder(StructureType.IGLOO, settings);
    }

    /**
     * Creates a new builder for a jungle temple.
     * @param settings the settings every structure carries
     * @return a new builder
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static SimpleStructure.Builder jungleTemple(StructureSettings settings) {
        return SimpleStructure.builder(StructureType.JUNGLE_TEMPLE, settings);
    }

    /**
     * Creates a new builder for an ocean monument.
     * @param settings the settings every structure carries
     * @return a new builder
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static SimpleStructure.Builder oceanMonument(StructureSettings settings) {
        return SimpleStructure.builder(StructureType.OCEAN_MONUMENT, settings);
    }

    /**
     * Creates a new builder for a stronghold.
     * @param settings the settings every structure carries
     * @return a new builder
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static SimpleStructure.Builder stronghold(StructureSettings settings) {
        return SimpleStructure.builder(StructureType.STRONGHOLD, settings);
    }

    /**
     * Creates a new builder for a swamp hut.
     * @param settings the settings every structure carries
     * @return a new builder
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static SimpleStructure.Builder swampHut(StructureSettings settings) {
        return SimpleStructure.builder(StructureType.SWAMP_HUT, settings);
    }

    /**
     * Creates a new builder for a woodland mansion.
     * @param settings the settings every structure carries
     * @return a new builder
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static SimpleStructure.Builder woodlandMansion(StructureSettings settings) {
        return SimpleStructure.builder(StructureType.WOODLAND_MANSION, settings);
    }

    /**
     * Creates a new builder for an oak mineshaft.
     * @param settings the settings every structure carries
     * @return a new builder
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static MineshaftStructure.Builder mineshaft(StructureSettings settings) {
        return MineshaftStructure.builder(settings);
    }

    /**
     * Creates a new builder for a nether fossil.
     * @param settings the settings every structure carries
     * @return a new builder
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static NetherFossilStructure.Builder netherFossil(StructureSettings settings) {
        return NetherFossilStructure.builder(settings);
    }

    /**
     * Creates a new builder for an ocean ruin.
     * @param settings the settings every structure carries
     * @param biomeTemperature the template set the ruin is drawn from
     * @return a new builder
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static OceanRuinStructure.Builder oceanRuin(StructureSettings settings, OceanRuinStructure.BiomeTemperature biomeTemperature) {
        return OceanRuinStructure.builder(settings, biomeTemperature);
    }

    /**
     * Creates a new builder for a ruined portal. Add at least one setup before building.
     * @param settings the settings every structure carries
     * @return a new builder
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static RuinedPortalStructure.Builder ruinedPortal(StructureSettings settings) {
        return RuinedPortalStructure.builder(settings);
    }

    /**
     * Creates a new builder for a shipwreck on the ocean floor.
     * @param settings the settings every structure carries
     * @return a new builder
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static ShipwreckStructure.Builder shipwreck(StructureSettings settings) {
        return ShipwreckStructure.builder(settings);
    }

    /**
     * Reads a Minecraft structure, or a keyed structure holder, into a wrapper.
     * @param minecraftStructure the structure or structure holder to read
     * @return a reference to the structure, or the decoded structure when it carries no key
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static Structure decode(Object minecraftStructure) {
        record Holder() {
            static final Decoder<Structure> DECODER = Decoder.create("dev.wyck.decode.worldgen.structure.StructureDecoder");
        }
        return Holder.DECODER.decode(minecraftStructure);
    }
}
