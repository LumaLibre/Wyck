package dev.wyck.worldgen.structure.templatesystem.processor;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record BlockAgeProcessorImpl(
    @Override float mossiness
) implements BlockAgeProcessor {

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.structure.templatesystem.BlockAgeProcessor(this.mossiness);
    }
}
