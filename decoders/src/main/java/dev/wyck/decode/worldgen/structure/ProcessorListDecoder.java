package dev.wyck.decode.worldgen.structure;

import dev.wyck.decode.Decoders;
import dev.wyck.worldgen.structure.templatesystem.ProcessorList;
import dev.wyck.worldgen.structure.templatesystem.processor.StructureProcessor;
import dev.wyck.worldgen.structure.templatesystem.types.ComposedProcessorList;
import dev.wyck.wrapper.decode.Decodable;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class ProcessorListDecoder implements Decodable<ProcessorList, Object> {

    @Override
    public ProcessorList decode(Object minecraftObject) {
        if (minecraftObject instanceof Holder<?> holder && holder.unwrapKey().isPresent()) {
            return ProcessorList.reference(Decoders.referenceKey(holder));
        }

        StructureProcessorList list = Decoders.value(minecraftObject);
        return ComposedProcessorList.of(null, list.list().stream().map(StructureProcessor::decode).toList());
    }
}
