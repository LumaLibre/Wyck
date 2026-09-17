package dev.wyck.worldgen.structure.pools.elements;

import dev.wyck.worldgen.structure.pools.PoolElement;
import dev.wyck.worldgen.structure.pools.Projection;
import dev.wyck.wrapper.Wrapper;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@ApiStatus.Internal
public record ListPoolElementImpl(
    @Override Projection projection,
    @Override List<PoolElement> elements
) implements ListPoolElement {

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.structure.pools.ListPoolElement(
            this.elements.stream().map(Wrapper::<StructurePoolElement>asHandle).toList(),
            this.projection.toNms(net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection.class)
        );
    }
}
