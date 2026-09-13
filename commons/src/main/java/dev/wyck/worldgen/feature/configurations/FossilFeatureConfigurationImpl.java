package dev.wyck.worldgen.feature.configurations;

import dev.wyck.worldgen.structure.templatesystem.ProcessorList;
import dev.wyck.worldgen.structure.templatesystem.StructureTemplate;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@ApiStatus.Internal
public record FossilFeatureConfigurationImpl(
    List<StructureTemplate> fossilStructures,
    List<StructureTemplate> overlayStructures,
    ProcessorList fossilProcessors,
    ProcessorList overlayProcessors,
    int maxEmptyCornersAllowed
) implements FossilFeatureConfiguration {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.FossilFeatureConfiguration(
            fossilStructures.stream().map(template -> template.<net.minecraft.resources.Identifier>asHandle()).toList(),
            overlayStructures.stream().map(template -> template.<net.minecraft.resources.Identifier>asHandle()).toList(),
            fossilProcessors.<Holder<StructureProcessorList>>asHandle(),
            overlayProcessors.<Holder<StructureProcessorList>>asHandle(),
            maxEmptyCornersAllowed
        );
    }
}
