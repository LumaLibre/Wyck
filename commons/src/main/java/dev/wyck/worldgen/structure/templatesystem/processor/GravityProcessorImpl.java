package dev.wyck.worldgen.structure.templatesystem.processor;

import dev.wyck.worldgen.HeightmapType;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record GravityProcessorImpl(
    @Override HeightmapType heightmap,
    @Override int offset
) implements GravityProcessor {

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.structure.templatesystem.GravityProcessor(this.heightmap.toNms(Heightmap.Types.class), this.offset);
    }
}
