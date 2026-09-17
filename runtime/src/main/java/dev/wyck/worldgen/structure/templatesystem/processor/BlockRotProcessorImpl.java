package dev.wyck.worldgen.structure.templatesystem.processor;

import dev.wyck.tags.TagSet;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.block.Block;
import org.bukkit.Material;

import java.util.Optional;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record BlockRotProcessorImpl(
    @Override Optional<TagSet<Material>> rottableBlocks,
    @Override float integrity
) implements BlockRotProcessor {

    @Override
    public Object toMinecraft() {
        return this.rottableBlocks
            .map(blocks -> new net.minecraft.world.level.levelgen.structure.templatesystem.BlockRotProcessor(blocks.<HolderSet<Block>>asHolderSet(), this.integrity))
            .orElseGet(() -> new net.minecraft.world.level.levelgen.structure.templatesystem.BlockRotProcessor(this.integrity));
    }
}
