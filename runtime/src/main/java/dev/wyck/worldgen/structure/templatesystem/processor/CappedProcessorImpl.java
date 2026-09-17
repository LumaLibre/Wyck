package dev.wyck.worldgen.structure.templatesystem.processor;

import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record CappedProcessorImpl(
    @Override StructureProcessor delegate,
    @Override IntProvider limit
) implements CappedProcessor {

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.structure.templatesystem.CappedProcessor(this.delegate.asHandle(), this.limit.asHandle());
    }
}
