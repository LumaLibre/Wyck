package dev.wyck.worldgen.structure.templatesystem.types;

import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.Lazy;
import dev.wyck.worldgen.structure.templatesystem.processor.StructureProcessor;
import dev.wyck.wrapper.Wrapper;
import net.kyori.adventure.key.Key;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record ComposedProcessorListImpl(
    @Override Optional<ResourceKey> resourceKey,
    @Override List<StructureProcessor> processors
) implements ComposedProcessorList {

    private static final Lazy<WyckRegistry> REGISTRY = WyckRegistry.lazy(RegistryId.PROCESSOR_LIST);

    @Override
    public Object toMinecraft() {
        return Holder.direct(new StructureProcessorList(
            this.processors.stream().map(Wrapper::<net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor>asHandle).toList()
        ));
    }

    @Override
    public Key key() {
        return this.resourceKey.orElseThrow();
    }

    @Override
    public ComposedProcessorList register() {
        ResourceKey key = this.resourceKey.orElseThrow(() -> new NoSuchElementException("Cannot register a composed processor list without a resource key."));
        REGISTRY.get().register(key, this);
        return this;
    }
}
