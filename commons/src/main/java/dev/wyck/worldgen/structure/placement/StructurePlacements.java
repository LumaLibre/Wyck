package dev.wyck.worldgen.structure.placement;

import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
final class StructurePlacements {

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    static Optional<StructurePlacement.ExclusionZone> exclusionZone(Optional<dev.wyck.worldgen.structure.placement.StructurePlacement.ExclusionZone> zone) {
        return zone.map(it -> new StructurePlacement.ExclusionZone(
            it.otherSet().<Holder<StructureSet>>asHandle(),
            it.chunkCount()
        ));
    }
}
