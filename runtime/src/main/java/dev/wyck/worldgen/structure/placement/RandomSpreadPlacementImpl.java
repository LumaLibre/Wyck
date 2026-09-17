package dev.wyck.worldgen.structure.placement;

import dev.wyck.util.WorldgenConversions;
import dev.wyck.worldgen.structure.FrequencyReduction;
import dev.wyck.worldgen.structure.RandomSpreadType;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record RandomSpreadPlacementImpl(
    @Override int spacing,
    @Override int separation,
    @Override RandomSpreadType spreadType,
    @Override int salt,
    @Override BlockVector locateOffset,
    @Override FrequencyReduction frequencyReduction,
    @Override float frequency,
    @Override Optional<StructurePlacement.ExclusionZone> exclusionZone
) implements RandomSpreadPlacement {

    @Override
    public net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement toMinecraft() {
        return new net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement(
            WorldgenConversions.toVec3i(this.locateOffset),
            this.frequencyReduction.toNms(net.minecraft.world.level.levelgen.structure.placement.AbstractSpreadingStructurePlacement.FrequencyReductionMethod.class),
            this.frequency,
            this.salt,
            StructurePlacements.exclusionZone(this.exclusionZone),
            this.spacing,
            this.separation,
            this.spreadType.toNms(net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType.class)
        );
    }
}
