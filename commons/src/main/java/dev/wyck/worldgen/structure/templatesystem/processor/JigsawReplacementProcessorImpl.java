package dev.wyck.worldgen.structure.templatesystem.processor;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class JigsawReplacementProcessorImpl implements JigsawReplacementProcessor {

    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.structure.templatesystem.JigsawReplacementProcessor.INSTANCE;
    }
}
