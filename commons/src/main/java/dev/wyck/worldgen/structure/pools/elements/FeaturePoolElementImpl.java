package dev.wyck.worldgen.structure.pools.elements;

import dev.wyck.worldgen.placement.PlacedFeature;
import dev.wyck.worldgen.structure.pools.Projection;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record FeaturePoolElementImpl(
    @Override Projection projection,
    @Override PlacedFeature feature
) implements FeaturePoolElement {

    @Override
    public Object toMinecraft() {
        return StructurePoolElement.feature(this.feature.<Holder<net.minecraft.world.level.levelgen.placement.PlacedFeature>>asHandle())
            .apply(this.projection.toNms(net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection.class));
    }
}
