package me.outspending.biomesapi.codegen;

import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.data.worldgen.features.AquaticFeatures;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.EndFeatures;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
import net.minecraft.data.worldgen.features.NetherFeatures;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.features.PileFeatures;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.data.worldgen.placement.EndPlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.NetherPlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.data.worldgen.placement.VillagePlacements;
import org.jspecify.annotations.Nullable;

import java.util.List;

// AsOf 2.3.0
public final class Generators {

    public static final List<GeneratorSpec> ALL = List.of(
        configuredFeatures(),
        placedFeatures()
    );

    private static @Nullable Identifier keyLocation(Object entry) {
        if (entry instanceof ResourceKey<?> resourceKey) {
            return resourceKey.identifier();
        }
        return null;
    }

    // TODO: replace these with builders

    private static GeneratorSpec configuredFeatures() {
        return new GeneratorSpec(
            "me.outspending.biomesapi.wrapper.worldgen.feature",
            "ConfiguredFeatures",
            "ConfiguredFeature",
            "ConfiguredFeature.reference",
            ResourceKey.class,
            Generators::keyLocation,
            List.of(
                AquaticFeatures.class,
                CaveFeatures.class,
                EndFeatures.class,
                MiscOverworldFeatures.class,
                NetherFeatures.class,
                OreFeatures.class,
                PileFeatures.class,
                TreeFeatures.class,
                VegetationFeatures.class
            ),
            "Typed references that point to vanilla's configured features."
        );
    }

    private static GeneratorSpec placedFeatures() {
        return new GeneratorSpec(
            "me.outspending.biomesapi.wrapper.worldgen.placement",
            "PlacedFeatures",
            "PlacedFeature",
            "PlacedFeature.reference",
            ResourceKey.class,
            Generators::keyLocation,
            List.of(
                AquaticPlacements.class,
                CavePlacements.class,
                EndPlacements.class,
                MiscOverworldPlacements.class,
                NetherPlacements.class,
                OrePlacements.class,
                TreePlacements.class,
                VegetationPlacements.class,
                VillagePlacements.class
            ),
            "Typed references that point to vanilla's placed features."
        );
    }

    private Generators() {
    }
}