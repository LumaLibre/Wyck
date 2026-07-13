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
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.TriState;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.CardinalLighting;
import net.minecraft.world.level.MoonPhase;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
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
        soundEvents(),
        biomeKeys(),
        dimensionKeys(),
        generationSteps(),
        heightmapTypes(),
        caveSurfaces(),
        tristates(),
        skyboxes(),
        cardinalLightTypes(),
        moonPhases(),
        grassColorModifiers(),
        temperatureModifiers(),
        mobCategories(),
        fluidTypes(),
        featureTypes(),
        worldCarverTypes(),
        activities()
    );

    private static GeneratorSpec configuredFeatures() {
        return new ReferenceSpec(
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
        return new ReferenceSpec(
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
        return new ReferenceSpec(
            "dev.wyck.wrapper.worldgen.function",
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
        return new ReferenceSpec(
            "dev.wyck.wrapper.worldgen.synth",
            "Noises",
            "NoiseParameters",
            "NoiseParameters.reference",
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
        return new ReferenceSpec(
            "dev.wyck.wrapper.environment.sounds",
            "SoundEvents",
            "SoundEvent",
            "SoundEvent.variableRange",
            SoundEvent.class,
            Generators::soundLocation,
            List.of(
                SoundEvents.class
            ),
            "Typed references that point to vanilla's sound events.",
            "2.4.1",
            "SOUND_EVENTS",
            Generators::isSoundField
        );
    }

    private static GeneratorSpec biomeKeys() {
        return new ReferenceSpec(
            "dev.wyck.model.biome",
            "Biomes",
            "Biome",
            "Biome.reference",
            ResourceKey.class,
            Generators::keyLocation,
            List.of(
                Biomes.class
            ),
            "Typed references that point to vanilla's biomes.",
            "3.0.0",
            "BIOMES"
        );
    }

    private static GeneratorSpec dimensionKeys() {
        return new ReferenceSpec(
            "dev.wyck.model.level.dimension",
            "Dimensions",
            "Dimension",
            "Dimension.reference",
            ResourceKey.class,
            Generators::keyLocation,
            List.of(
                BuiltinDimensionTypes.class
            ),
            "Typed references that point to vanilla's dimensions.",
            "3.0.0",
            "DIMENSIONS"
        );
    }

    private static GeneratorSpec generationSteps() {
        return new EnumSpec(
            "dev.wyck.wrapper.worldgen",
            "GenerationStep",
            GenerationStep.Decoration.class,
            Generators::enumName,
            "Wraps Minecraft's GenerationStep.Decoration.",
            "2.3.0"
        );
    }

    private static GeneratorSpec heightmapTypes() {
        return new EnumSpec(
            "dev.wyck.wrapper.worldgen",
            "HeightmapType",
            Heightmap.Types.class,
            Generators::enumName,
            "Wraps Minecraft's Heightmap.Types.",
            "3.0.0"
        );
    }

    private static GeneratorSpec caveSurfaces() {
        return new EnumSpec(
            "dev.wyck.wrapper.worldgen.surface.condition",
            "CaveSurface",
            CaveSurface.class,
            List.of(
                "Which face of the surface a {@link StoneDepthConditionSource stone-depth} check measures from.",
                "{@link #FLOOR}, blocks are placed based on the distance to the surface above; if {@link #CEILING},",
                "the distance to the surface below is used instead.",
                "",
                "@see <a href=\"https://minecraft.wiki/w/Surface_rule#Surface_conditions\">Surface rule (surface conditions)</a>"
            ),
            "3.0.0"
        );
    }

    private static GeneratorSpec tristates() {
        return new EnumSpec(
            "dev.wyck.wrapper.environment",
            "TriState",
            TriState.class,
            "Represents a tri-state value, which can be true, false, or default.",
            "2.4.1"
        );
    }

    private static GeneratorSpec skyboxes() {
        return new EnumSpec(
            "dev.wyck.wrapper.level.dimension",
            "Skybox",
            DimensionType.Skybox.class,
            Generators::serializedName,
            "Skybox type, as it appears in Minecraft.",
            "2.4.0"
        );
    }

    private static GeneratorSpec cardinalLightTypes() {
        return new EnumSpec(
            "dev.wyck.wrapper.level.dimension",
            "CardinalLightType",
            CardinalLighting.Type.class,
            Generators::serializedName,
            "Cardinal light type, as it appears in Minecraft.",
            "2.4.0"
        );
    }

    private static GeneratorSpec moonPhases() {
        return new EnumSpec(
            "dev.wyck.wrapper.environment",
            "MoonPhase",
            MoonPhase.class,
            Generators::serializedName,
            List.of(
                "This enum represents the phases of the moon in Minecraft.",
                "Each enum value carries a vanilla key, retrievable via {@link #getKey()}, which the impl module translates to the underlying NMS MoonPhase value."
            ),
            "1.1.0"
        );
    }

    private static GeneratorSpec grassColorModifiers() {
        return new EnumSpec(
            "dev.wyck.wrapper.environment",
            "GrassColorModifier",
            BiomeSpecialEffects.GrassColorModifier.class,
            Generators::serializedName,
            List.of(
                "This enum represents the grass color modifier for a biome in Minecraft.",
                "It includes three values: NONE, SWAMP, and DARK_FOREST, which correspond to the GrassColorModifier values in the BiomeSpecialEffects class in the Minecraft code.",
                "Each enum value carries a vanilla key, retrievable via {@link #getKey()}, which the impl module translates to the underlying NMS value."
            ),
            "0.0.24"
        );
    }

    private static GeneratorSpec temperatureModifiers() {
        return new EnumSpec(
            "dev.wyck.wrapper.biome",
            "TemperatureModifier",
            Biome.TemperatureModifier.class,
            Generators::serializedName,
            List.of(
                "This enum represents the temperature modifier for a biome in Minecraft.",
                "It includes two values: NONE and FROZEN, which correspond to the TemperatureModifier values in the Biome class in the Minecraft code.",
                "Each enum value carries a vanilla key, retrievable via {@link #getKey()}, which the impl module translates to the underlying NMS value."
            ),
            "0.0.1"
        );
    }

    private static GeneratorSpec mobCategories() {
        return new EnumSpec(
            "dev.wyck.wrapper.biome.entity",
            "MobCategory",
            MobCategory.class,
            Generators::serializedName,
            "Represents the category of a mob in Minecraft.",
            "2.3.0"
        );
    }

    private static GeneratorSpec fluidTypes() {
        return new ConstantSpec(
            "dev.wyck.wrapper.worldgen.material",
            "FluidType",
            Fluid.class,
            Generators::fluidLocation,
            List.of(
                Fluids.class
            ),
            "FLUID",
            "The vanilla fluids a block predicate can match against.",
            "2.3.0"
        );
    }

    private static GeneratorSpec featureTypes() {
        return new ConstantSpec(
            "dev.wyck.wrapper.worldgen.feature",
            "FeatureType",
            Feature.class,
            Generators::featureLocation,
            List.of(
                Feature.class
            ),
            "FEATURE",
            "Typed references to the built-in feature types, the algorithms in the {@code FEATURE} registry.",
            "2.3.0"
        );
    }

    private static GeneratorSpec worldCarverTypes() {
        return new ConstantSpec(
            "dev.wyck.wrapper.worldgen.carver",
            "WorldCarverType",
            WorldCarver.class,
            Generators::worldCarverLocation,
            List.of(
                WorldCarver.class
            ),
            "CARVER",
            "The vanilla world-carver algorithms that a configured carver can be built on.",
            "2.3.0"
        );
    }

    private static GeneratorSpec activities() {
        return new ConstantSpec(
            "dev.wyck.wrapper.environment",
            "Activity",
            Activity.class,
            Generators::activityLocation,
            List.of(
                Activity.class
            ),
            "ACTIVITY",
            List.of(
                "This enum represents the various activities that entities in Minecraft can perform.",
                "Each enum value carries a vanilla key which the impl module translates to the underlying NMS Activity value."
            ),
            "1.1.0"
        );
    }

    private static @Nullable Identifier fluidLocation(Object entry) {
        if (entry instanceof Fluid fluid) {
            return BuiltInRegistries.FLUID.getKey(fluid);
        }
        return null;
    }

    private static @Nullable Identifier featureLocation(Object entry) {
        if (entry instanceof Feature<?> feature) {
            return BuiltInRegistries.FEATURE.getKey(feature);
        }
        return null;
    }

    private static @Nullable Identifier worldCarverLocation(Object entry) {
        if (entry instanceof WorldCarver<?> carver) {
            return BuiltInRegistries.CARVER.getKey(carver);
        }
        return null;
    }

    private static @Nullable Identifier activityLocation(Object entry) {
        if (entry instanceof Activity activity) {
            return BuiltInRegistries.ACTIVITY.getKey(activity);
        }
        return null;
    }

    private static @Nullable Identifier keyLocation(Object entry) {
        if (entry instanceof ResourceKey<?> resourceKey) {
            return resourceKey.identifier();
        }
        return null;
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

    /**
     * Keys the wrapped constant by the vanilla constant's name, e.g.
     * {@code RAW_GENERATION}. Use when the translator resolves via
     * {@code Enum.valueOf}.
     */
    static String enumName(Enum<?> constant) {
        return constant.name();
    }

    /**
     * Keys the wrapped constant by {@code getSerializedName()}, e.g.
     * {@code raw_generation}. Only valid for enums implementing
     * {@link StringRepresentable}.
     */
    static String serializedName(Enum<?> constant) {
        return ((StringRepresentable) constant).getSerializedName();
    }

}