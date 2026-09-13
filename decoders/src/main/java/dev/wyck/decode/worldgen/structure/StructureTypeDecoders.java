package dev.wyck.decode.worldgen.structure;

import dev.wyck.decode.Decoders;
import dev.wyck.decode.FastReflection;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.HeightmapType;
import dev.wyck.worldgen.heightproviders.HeightProvider;
import dev.wyck.worldgen.structure.StructureSettings;
import dev.wyck.worldgen.structure.StructureType;
import dev.wyck.worldgen.structure.pools.DimensionPadding;
import dev.wyck.worldgen.structure.pools.TemplatePool;
import dev.wyck.worldgen.structure.pools.alias.PoolAliasBinding;
import dev.wyck.worldgen.structure.templatesystem.LiquidSettings;
import dev.wyck.worldgen.structure.types.DefinedStructure;
import dev.wyck.worldgen.structure.types.JigsawStructure;
import dev.wyck.worldgen.structure.types.MineshaftStructure;
import dev.wyck.worldgen.structure.types.NetherFossilStructure;
import dev.wyck.worldgen.structure.types.OceanRuinStructure;
import dev.wyck.worldgen.structure.types.RuinedPortalStructure;
import dev.wyck.worldgen.structure.types.ShipwreckStructure;
import dev.wyck.worldgen.structure.types.SimpleStructure;
import dev.wyck.wrapper.decode.DecoderRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

/**
 * Every structure type vanilla registers. {@code StructureDecoder} falls back to an opaque
 * {@code ComposedStructure} for types this registry does not claim, which is how structures from mods
 * and other plugins still read.
 */
@NullMarked
@ApiStatus.Internal
public final class StructureTypeDecoders extends DecoderRegistry<DefinedStructure, Structure> {

    public StructureTypeDecoders() {
        for (StructureType type : SimpleStructure.TYPES) {
            register(type.resourceKey(), structure -> SimpleStructure.of(type, settings(structure)));
        }
        register("jigsaw", structure -> jigsaw((net.minecraft.world.level.levelgen.structure.structures.JigsawStructure) structure));
        register("mineshaft", structure -> MineshaftStructure.of(
            null, settings(structure),
            MineshaftStructure.Type.TRANSLATOR.fromNms(FastReflection.<net.minecraft.world.level.levelgen.structure.structures.MineshaftStructure.Type>read(structure, "type"))
        ));
        register("shipwreck", structure -> ShipwreckStructure.of(
            null, settings(structure), ((net.minecraft.world.level.levelgen.structure.structures.ShipwreckStructure) structure).isBeached
        ));
        register("nether_fossil", structure -> NetherFossilStructure.of(
            null, settings(structure), HeightProvider.decode(((net.minecraft.world.level.levelgen.structure.structures.NetherFossilStructure) structure).height)
        ));
        register("ocean_ruin", structure -> {
            var ruin = (net.minecraft.world.level.levelgen.structure.structures.OceanRuinStructure) structure;
            return OceanRuinStructure.of(
                null, settings(structure), OceanRuinStructure.BiomeTemperature.TRANSLATOR.fromNms(ruin.biomeTemp),
                ruin.largeProbability, ruin.clusterProbability
            );
        });
        register("ruined_portal", structure -> {
            java.util.List<net.minecraft.world.level.levelgen.structure.structures.RuinedPortalStructure.Setup> setups = FastReflection.read(structure, "setups");
            return RuinedPortalStructure.of(null, settings(structure), setups.stream()
                .map(setup -> new RuinedPortalStructure.Setup(
                    RuinedPortalStructure.Placement.TRANSLATOR.fromNms(setup.placement()),
                    setup.airPocketProbability(), setup.mossiness(), setup.overgrown(), setup.vines(),
                    setup.canBeCold(), setup.replaceWithBlackstone(), setup.weight()
                ))
                .toList());
        });
    }

    @Override
    protected ResourceKey discriminate(Structure minecraftObject) {
        return Decoders.registryKey(BuiltInRegistries.STRUCTURE_TYPE, minecraftObject.type());
    }

    static StructureSettings settings(Structure structure) {
        return StructureSettings.decode(new Structure.StructureSettings(
            structure.biomes(),
            structure.spawnOverrides(),
            structure.step(),
            structure.terrainAdaptation()
        ));
    }

    private static JigsawStructure jigsaw(net.minecraft.world.level.levelgen.structure.structures.JigsawStructure jigsaw) {
        Optional<Identifier> startJigsawName = FastReflection.read(jigsaw, "startJigsawName");
        Optional<Heightmap.Types> heightmap = FastReflection.read(jigsaw, "projectStartToHeightmap");
        net.minecraft.world.level.levelgen.structure.structures.JigsawStructure.MaxDistance maxDistance =
            FastReflection.read(jigsaw, "maxDistanceFromCenter");
        net.minecraft.world.level.levelgen.structure.pools.DimensionPadding padding = FastReflection.read(jigsaw, "dimensionPadding");

        return JigsawStructure.of(
            null,
            settings(jigsaw),
            TemplatePool.decode(jigsaw.getStartPool()),
            startJigsawName.map(Decoders::key).orElse(null),
            FastReflection.<Integer>read(jigsaw, "maxDepth"),
            HeightProvider.decode(FastReflection.read(jigsaw, "startHeight")),
            FastReflection.<Boolean>read(jigsaw, "useExpansionHack"),
            heightmap.map(HeightmapType.TRANSLATOR::fromNms).orElse(null),
            JigsawStructure.MaxDistance.of(maxDistance.horizontal(), maxDistance.vertical()),
            jigsaw.getPoolAliases().stream().map(PoolAliasBinding::decode).toList(),
            DimensionPadding.of(padding.bottom(), padding.top()),
            LiquidSettings.TRANSLATOR.fromNms(FastReflection.<net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings>read(jigsaw, "liquidSettings"))
        );
    }
}
