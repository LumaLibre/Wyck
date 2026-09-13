package dev.wyck.worldgen.structure.templatesystem.processor;

import dev.wyck.tags.TagSet;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.block.Block;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record ProtectedBlocksProcessorImpl(
    @Override TagSet<Material> cannotReplace
) implements ProtectedBlocksProcessor {

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.structure.templatesystem.ProtectedBlockProcessor(this.cannotReplace.<HolderSet<Block>>asHolderSet());
    }
}
