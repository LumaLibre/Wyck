package dev.wyck.codegen;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
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
import net.minecraft.world.level.levelgen.NoiseRouterData;
import net.minecraft.world.level.levelgen.Noises;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.List;

public final class Generators {

    public static final List<GeneratorSpec> ALL = List.of(
        configuredFeatures(),
        placedFeatures(),
        densityFunctions(),
        noiseParameters(),
        soundEvents()
    );

    private static @Nullable Identifier keyLocation(Object entry) {
        if (entry instanceof ResourceKey<?> resourceKey) {
            return resourceKey.identifier();
        }
        return null;
    }


    private static GeneratorSpec configuredFeatures() {
        return new GeneratorSpec(
            "dev.wyck.wrapper.worldgen.feature",
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
            "Typed references that point to vanilla's configured features.",
            "2.3.0",
            "CONFIGURED_FEATURES"
        );
    }

    private static GeneratorSpec placedFeatures() {
        return new GeneratorSpec(
            "dev.wyck.wrapper.worldgen.placement",
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
            "Typed references that point to vanilla's placed features.",
            "2.3.0",
            "PLACED_FEATURES"
        );
    }

    private static GeneratorSpec densityFunctions() {
        return new GeneratorSpec(
            "dev.wyck.wrapper.level.noise.function",
            "DensityFunctions",
            "DensityFunction",
            "DensityFunction.reference",
            ResourceKey.class,
            Generators::keyLocation,
            List.of(
                NoiseRouterData.class
            ),
            "Typed references that point to vanilla's density functions.",
            "2.4.0",
            "DENSITY_FUNCTIONS"
        );
    }

    private static GeneratorSpec noiseParameters() {
        return new GeneratorSpec(
            "dev.wyck.wrapper.level.noise",
            "Noises",
            "ResourceKey",
            "",
            ResourceKey.class,
            Generators::keyLocation,
            List.of(
                Noises.class
            ),
            "Typed references that point to vanilla's noise parameters.",
            "2.4.0",
            "NOISE"
        );
    }

    private static GeneratorSpec soundEvents() {
        return new GeneratorSpec(
            "dev.wyck.wrapper.environment.sounds",
            "SoundEvents",
            "SoundEvent",
            "SoundEvent.variableRange",
            net.minecraft.sounds.SoundEvent.class,
            Generators::soundLocation,
            List.of(
                net.minecraft.sounds.SoundEvents.class
            ),
            "Typed references that point to vanilla's sound events.",
            "2.4.1",
            "SOUND_EVENTS",
            Generators::isSoundField
        );
    }

    private static boolean isSoundField(Field field) {
        Class<?> type = field.getType();
        return net.minecraft.sounds.SoundEvent.class.isAssignableFrom(type) || Holder.class.isAssignableFrom(type);
    }

    private static @Nullable Identifier soundLocation(Object entry) {
        net.minecraft.sounds.SoundEvent event = null;
        if (entry instanceof net.minecraft.sounds.SoundEvent se) {
            event = se;
        } else if (entry instanceof Holder<?> holder && holder.value() instanceof net.minecraft.sounds.SoundEvent se) {
            event = se;
        }
        if (event == null) {
            return null;
        }
        return BuiltInRegistries.SOUND_EVENT.getKey(event);
    }

}