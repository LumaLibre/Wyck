package dev.wyck.worldgen.structure.templatesystem.processor;

import dev.wyck.util.WorldgenConversions;
import org.bukkit.Material;

import java.util.List;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record BlockIgnoreProcessorImpl(
    @Override List<Material> blocks
) implements BlockIgnoreProcessor {

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor(WorldgenConversions.toBlockList(this.blocks));
    }
}
