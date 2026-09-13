package dev.wyck.decode.worldgen.structure;

import dev.wyck.decode.Decoders;
import dev.wyck.keys.ResourceKey;
import dev.wyck.tags.TagSet;
import dev.wyck.worldgen.structure.placement.ConcentricRingsPlacement;
import dev.wyck.worldgen.structure.FrequencyReduction;
import dev.wyck.worldgen.structure.placement.RandomSpreadPlacement;
import dev.wyck.worldgen.structure.RandomSpreadType;
import dev.wyck.worldgen.structure.placement.StructurePlacement;
import dev.wyck.worldgen.structure.StructureSet;
import dev.wyck.wrapper.decode.DecoderRegistry;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.placement.ConcentricRingsStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
@ApiStatus.Internal
public final class StructurePlacementDecoders extends DecoderRegistry<StructurePlacement, net.minecraft.world.level.levelgen.structure.placement.StructurePlacement> {

    public StructurePlacementDecoders() {
        register("random_spread", placement -> {
            RandomSpreadStructurePlacement spread = (RandomSpreadStructurePlacement) placement;
            return RandomSpreadPlacement.of(
                spread.spacing(),
                spread.separation(),
                RandomSpreadType.TRANSLATOR.fromNms(spread.spreadType()),
                spread.salt,
                offset(spread.locateOffset),
                FrequencyReduction.TRANSLATOR.fromNms(spread.frequencyReductionMethod),
                spread.frequency,
                exclusionZone(spread)
            );
        });
        register("concentric_rings", placement -> {
            ConcentricRingsStructurePlacement rings = (ConcentricRingsStructurePlacement) placement;
            return ConcentricRingsPlacement.of(
                rings.distance(),
                rings.spread(),
                rings.count(),
                TagSet.decodeBiomes(rings.preferredBiomes()),
                rings.salt,
                offset(rings.locateOffset),
                FrequencyReduction.TRANSLATOR.fromNms(rings.frequencyReductionMethod),
                rings.frequency,
                exclusionZone(rings)
            );
        });
    }

    @Override
    protected ResourceKey discriminate(net.minecraft.world.level.levelgen.structure.placement.StructurePlacement minecraftObject) {
        return Decoders.registryKey(BuiltInRegistries.STRUCTURE_PLACEMENT, minecraftObject.type());
    }

    private static BlockVector offset(Vec3i offset) {
        return new BlockVector(offset.getX(), offset.getY(), offset.getZ());
    }

    private static StructurePlacement.@Nullable ExclusionZone exclusionZone(net.minecraft.world.level.levelgen.structure.placement.StructurePlacement placement) {
        return placement.exclusionZone
            .map(zone -> StructurePlacement.ExclusionZone.of(StructureSet.decode(zone.otherSet()), zone.chunkCount()))
            .orElse(null);
    }
}
