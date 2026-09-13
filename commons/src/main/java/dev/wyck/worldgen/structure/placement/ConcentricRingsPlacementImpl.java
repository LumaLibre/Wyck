package dev.wyck.worldgen.structure.placement;

import dev.wyck.biome.Biome;
import dev.wyck.tags.TagSet;
import dev.wyck.util.WorldgenConversions;
import dev.wyck.worldgen.structure.FrequencyReduction;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record ConcentricRingsPlacementImpl(
    @Override int distance,
    @Override int spread,
    @Override int count,
    @Override TagSet<Biome> preferredBiomes,
    @Override int salt,
    @Override BlockVector locateOffset,
    @Override FrequencyReduction frequencyReduction,
    @Override float frequency,
    @Override Optional<StructurePlacement.ExclusionZone> exclusionZone
) implements ConcentricRingsPlacement {

    @Override
    public net.minecraft.world.level.levelgen.structure.placement.ConcentricRingsStructurePlacement toMinecraft() {
        return new net.minecraft.world.level.levelgen.structure.placement.ConcentricRingsStructurePlacement(
            WorldgenConversions.toVec3i(this.locateOffset),
            this.frequencyReduction.toNms(net.minecraft.world.level.levelgen.structure.placement.StructurePlacement.FrequencyReductionMethod.class),
            this.frequency,
            this.salt,
            StructurePlacements.exclusionZone(this.exclusionZone),
            this.distance,
            this.spread,
            this.count,
            this.preferredBiomes.asHolderSet()
        );
    }
}
