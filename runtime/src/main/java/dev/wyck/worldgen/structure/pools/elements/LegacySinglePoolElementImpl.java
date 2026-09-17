package dev.wyck.worldgen.structure.pools.elements;

import dev.wyck.worldgen.structure.pools.Projection;
import dev.wyck.worldgen.structure.templatesystem.LiquidSettings;
import dev.wyck.worldgen.structure.templatesystem.ProcessorList;
import dev.wyck.worldgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record LegacySinglePoolElementImpl(
    @Override StructureTemplate template,
    @Override ProcessorList processors,
    @Override Projection projection,
    @Override Optional<LiquidSettings> overrideLiquidSettings
) implements LegacySinglePoolElement {

    @Override
    public Object toMinecraft() {
        return PoolElements.legacy(
            this.template.key().identifier(),
            this.processors.asHandle(),
            this.projection.toNms(net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection.class),
            this.overrideLiquidSettings.map(it -> it.toNms(net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings.class))
        );
    }
}
