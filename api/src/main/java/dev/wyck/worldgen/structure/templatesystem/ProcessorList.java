package dev.wyck.worldgen.structure.templatesystem;

import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.worldgen.structure.templatesystem.processor.StructureProcessor;
import dev.wyck.worldgen.structure.templatesystem.types.ComposedProcessorList;
import dev.wyck.worldgen.structure.templatesystem.types.ReferencedProcessorList;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import net.kyori.adventure.key.Keyed;
import org.jspecify.annotations.NullMarked;

import java.util.List;

/**
 * Wraps Minecraft's StructureProcessorList: the {@link StructureProcessor processors} a structure
 * template is run through, in order, as it is placed. Either a reference to a list already in the
 * {@code PROCESSOR_LIST} registry, or a list authored or decoded here.
 *
 * @see <a href="https://minecraft.wiki/w/Processor_list">Processor list</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
public interface ProcessorList extends Wrapper, Keyed {

    /**
     * Vanilla's {@code minecraft:empty} list, which runs no processors.
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    ProcessorList EMPTY = reference(ResourceKey.minecraft("empty"));

    /**
     * References a processor list already registered under the given key.
     * @param key the registry key of the processor list
     * @return a reference to the registered processor list
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static ReferencedProcessorList reference(ResourceKey key) {
        return ReferencedProcessorList.of(key);
    }

    /**
     * Resolves this list's key in Minecraft's processor-list registry and decodes the registered
     * value, keeping the key on the result.
     * @return the decoded processor list
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    default ProcessorList wrap() {
        ResourceKey key = ResourceKey.of(key().namespace(), key().value());
        Object minecraft = WyckRegistry.of(RegistryId.PROCESSOR_LIST).retrieveOrThrow(key);
        ProcessorList decoded = decode(minecraft);
        if (decoded instanceof ComposedProcessorList composed && composed.resourceKey().isEmpty()) {
            return composed.toBuilder().resourceKey(key).build();
        }
        return decoded;
    }

    /**
     * Authors a processor list running the given processors in order.
     * @param processors the processors to run
     * @return an authored processor list
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static ComposedProcessorList of(StructureProcessor... processors) {
        return ComposedProcessorList.of(null, List.of(processors));
    }

    /**
     * Creates a new builder for an authored processor list.
     * @return a new builder
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static ComposedProcessorList.Builder builder() {
        return ComposedProcessorList.builder();
    }

    /**
     * Reads a Minecraft processor list, or a keyed processor-list holder, into a wrapper.
     * @param minecraftProcessorList the processor list or processor-list holder to read
     * @return a reference to the processor list, or the decoded list when it carries no key
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static ProcessorList decode(Object minecraftProcessorList) {
        record Holder() {
            static final Decoder<ProcessorList> DECODER = Decoder.create("dev.wyck.decode.worldgen.structure.ProcessorListDecoder");
        }
        return Holder.DECODER.decode(minecraftProcessorList);
    }
}
